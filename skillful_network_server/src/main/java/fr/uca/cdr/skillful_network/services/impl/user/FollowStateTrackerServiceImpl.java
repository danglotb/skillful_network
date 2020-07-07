package fr.uca.cdr.skillful_network.services.impl.user;

import fr.uca.cdr.skillful_network.entities.user.*;
import fr.uca.cdr.skillful_network.repositories.user.FollowStateTrackerRepository;
import fr.uca.cdr.skillful_network.repositories.user.NotificationRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class FollowStateTrackerServiceImpl implements FollowStateTrackerService {

    @Autowired
    private FollowStateTrackerRepository fstRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Global
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Optional<List<FollowStateTracker>> getAllFST() { return Optional.of(fstRepository.findAll()); }
    @Override
    public Optional<FollowStateTracker> getFSTById(Long ID)  { return fstRepository.findById(ID); }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Follower methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Optional<List<FollowStateTracker>> getAllFSTByFollower() {
        return this.getFSTByFollowerID(authenticationService.getCurrentUser().getId());
    }

    @Override
    public Optional<List<FollowStateTracker>> getFSTByFollowerID(Long followerID) {
        return Optional.of( fstRepository.findAllByFollower(userService.getById(followerID)) );
    }

    @Override
    public Optional<List<User>> getAllFollowersByFollowable() {
        return this.getAllFollowersByFollowableID(authenticationService.getCurrentUser().getId());
    }

    @Override
    public Optional<List<User>> getAllFollowersByFollowableID(Long followableID) {
        return Optional.of(
                fstRepository.findAllByFollowed(userService.getById(followableID)).stream()
                        .map(FollowStateTracker::getFollower)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public boolean follow(Long followableID) {
        return this.follow(authenticationService.getCurrentUser().getId(), followableID);
    }

    @Override
    public boolean follow(Long followerID, Long followableID) {
        //  System.out.println("FollowStateTrackerServiceImpl.follow("+followerID+","+followableID+")");
        if ( followableID == followerID ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de se suivre soi même!");
        }

        // check if FST link already exists
        User follower = userService.getById(followerID);
        User followable = userService.getById(followableID);
        if ( fstRepository.findByFollowerAndFollowed(follower, followable) != null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi est déjà activé.");
        }

        // persist
        return ( fstRepository.save(new FollowStateTracker(followable, follower)) != null) ;
    }

    @Override
    public void unfollowByFollowedID(Long followedID) {
        //  System.out.println("FollowStateTrackerServiceImpl.unfollowByFollowedID("+followedID+")");
        // get followed user
        User followed = userService.getById(followedID);
        if ( followed == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucun User avec l'id: " + followedID);
        }

        // get FST
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                authenticationService.getCurrentUser(), followed);
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }

        // delete process
        this.unfollow(fst);
    }

    @Override
    public void unfollowByFSTId(Long fstId) {
        //  System.out.println("FollowStateTrackerServiceImpl.unfollowByFSTId("+fstId+")");
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );

        // delete process
        this.unfollow(fst);
    }

    public void unfollow(FollowStateTracker fst) {

        //update followed
        User followed = fst.getFollowed();
        followed.getFollowableSet().remove(fst);
        userService.createOrUpdate(followed);

        //update follower
        User follower = fst.getFollower();
        follower.getFollowerSet().remove(fst);
        userService.createOrUpdate(follower);

        // delete FST
        fstRepository.delete(fst);
    }

    @Override
    public void setFollowerStatus(Follower.FollowerStatus status) {
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowerStatus(" + status + ")");
        this.setFollowerStatusByFollower(authenticationService.getCurrentUser(), status);
    }

    @Override
    public void setFollowerStatusByFollowerID(Long followerID, Follower.FollowerStatus status) {
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowerStatusByFollowerID("+followerID+"," + status + ")");
        this.setFollowerStatusByFollower(userService.getById(followerID), status);
    }

    public void setFollowerStatusByFollower(User follower, Follower.FollowerStatus status) {
        // get all FST and loop with modification
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.setFollowerStatus(status);
                    //  System.out.println("fstId: "+fst.getId()+" -> " + fst.getFollowerStatus());
                    fstRepository.save(fst);
                });
    }

    @Override
    public void setFollowerStatusByFollowedID(Long followedID, Follower.FollowerStatus status) {
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowerStatusByFollowedID("+followedID+"," + status + ")");
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                authenticationService.getCurrentUser(), userService.getById(followedID));
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        this.setFollowerStatusByFST(fst, status);
    }

    @Override
    public void setFollowerStatusByFSTID(Long fstId, Follower.FollowerStatus status) {
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowerStatusByFSTID("+fstId+"," + status + ")");
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );
        // process update
        this.setFollowerStatusByFST(fst, status);
    }

    public void setFollowerStatusByFST(FollowStateTracker fst, Follower.FollowerStatus status) {
        fst.setFollowerStatus(status);
        //  System.out.println("fstId: "+fst.getId()+" -> " + fst.getFollowerStatus());
        fstRepository.save(fst);
    }

    @Override
    public void setFollowerNotifiableStatus(Follower.FollowerNotifiable notifiable) {
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowerNotifiableStatus(" + notifiable + ")");
        this.setFollowerNotifiableStatusByFollower(authenticationService.getCurrentUser(), notifiable);
    }

    @Override
    public void setFollowerNotifiableStatusByFollowerID(Long followerID, Follower.FollowerNotifiable notifiable) {
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowerNotifiableStatusByFollowerID("+followerID+"," + notifiable + ")");
        this.setFollowerNotifiableStatusByFollower(userService.getById(followerID), notifiable);
    }

    public void setFollowerNotifiableStatusByFollower(User follower, Follower.FollowerNotifiable notifiable) {
        // get all FST and loop with modification
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.setFollowerNotifiable(notifiable);
                    //  System.out.println("fstId: "+fst.getId()+" -> " + fst.getFollowerNotifiable());
                    fstRepository.save(fst);
                });
    }

    @Override
    public void setFollowerNotifiableStatusByFollowedID(Long followedID, Follower.FollowerNotifiable notifiable) {
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowerNotifiableStatusByFollowedID("+followedID+"," + notifiable + ")");
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                authenticationService.getCurrentUser(), userService.getById(followedID));
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        this.setFollowerNotifiableByFST(fst, notifiable);
    }

    @Override
    public void setFollowerNotifiableStatusByFSTID(Long fstId, Follower.FollowerNotifiable notifiable) {
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowerNotifiableStatusByFSTID("+fstId+"," + notifiable + ")");
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );
        this.setFollowerNotifiableByFST(fst, notifiable);
    }

    public void setFollowerNotifiableByFST( FollowStateTracker fst, Follower.FollowerNotifiable notifiable) {
        fst.setFollowerNotifiable(notifiable);
        //  System.out.println("fstId: "+fst.getId()+" -> " + fst.getFollowerNotifiable());
        fstRepository.save(fst);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Followable methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Optional<List<FollowStateTracker>> getAllFSTByFollowable() {
        return this.getAllFSTByFollowableID(authenticationService.getCurrentUser().getId());
    }

    @Override
    public Optional<List<FollowStateTracker>> getAllFSTByFollowableID(Long followableID) {
        return Optional.of( fstRepository.findAllByFollowed(userService.getById(followableID)) );
    }

    @Override
    public Optional<List<User>> getAllFollowedByFollower() {
        return this.getAllFollowedByFollowerID(authenticationService.getCurrentUser().getId());
    }

    @Override
    public Optional<List<User>> getAllFollowedByFollowerID(Long followerID) {
        return Optional.of(
                fstRepository.findAllByFollower(userService.getById(followerID)).stream()
                        .map(FollowStateTracker::getFollowed)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void banFollower(Long followerID) {
        this.banFollower(authenticationService.getCurrentUser().getId(), followerID);
    }

    @Override
    public void banFollower(Long followedID, Long followerID) {
        //  System.out.println("FollowStateTrackerServiceImpl.banFollower("+followedID+","+followerID+")");
        if ( followedID == followerID ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de se suivre soi même!");
        }

        // check if FST link already exists
        User follower = userService.getById(followerID);
        User followed = userService.getById(followedID);

        // get FST
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(follower, followed);
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        fst.setFollowerStatus(Follower.FollowerStatus.banned);
        fstRepository.save(fst);
    }

    @Override
    public void setFollowableStatus(Followable.FollowableStatus status) {
        // set for all FST by current user as followed
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowableStatus(" + status + ")");
        // process update
        this.setFollowableStatusByFollowable(authenticationService.getCurrentUser(), status);
    }

    @Override
    public void setFollowableStatusByFollowedID(Long followedID, Followable.FollowableStatus status) {
        // set for all FST by provided user as followed
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowableStatusByFollowableID("+followedID+"," + status + ")");
        // process update
        this.setFollowableStatusByFollowable(userService.getById(followedID), status);
    }

    public void setFollowableStatusByFollowable(User followed, Followable.FollowableStatus status) {
        // update followable User status
        followed.setFollowableStatus(status);
        userService.createOrUpdate(followed);

        // get all FST and loop with modification
        fstRepository.findAllByFollowed(followed)
                .forEach( fst -> {
                    fst.setFollowedStatus(status);
                    //  System.out.println("fstId: "+fst.getId()+" -> " + fst.getFollowedStatus());
                    fstRepository.save(fst);
                });
    }

    @Override
    public void setFollowableStatusByFollowerID(Long followerID, Followable.FollowableStatus status) {
        // set for a specific FST by current user as followed and provided user as follower
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowableStatusByFollowerID("+followerID+"," + status + ")");
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                userService.getById(followerID), authenticationService.getCurrentUser());
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        // process update
        this.setFollowableStatusByFST(fst, status);
    }

    @Override
    public void setFollowableStatusByFSTID(Long fstId, Followable.FollowableStatus status) {
        // set for a specific FST
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowableStatusByFSTID("+fstId+"," + status + ")");
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );
        // process update
        this.setFollowableStatusByFST(fst, status);
    }

    public void setFollowableStatusByFST(FollowStateTracker fst, Followable.FollowableStatus status) {
        // set for all FST by provided user as followed
        fst.setFollowedStatus(status);
        //  System.out.println("fstId: "+fst.getId()+" -> " + fst.getFollowedStatus());
        fstRepository.save(fst);
    }

    @Override
    public void setFollowableNotifiableStatus(Followable.FollowableNotifiable notifiable) {
        // set for all FST by current user as followed
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowableNotifiableStatus(" + notifiable + ")");
        // process update
        this.setFollowableNotifiableStatusByFollowable(authenticationService.getCurrentUser(), notifiable);
    }

    @Override
    public void setFollowableNotifiableStatusByFollowedID(Long followedID, Followable.FollowableNotifiable notifiable) {
        // set for all FST by provided user as followed
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowableNotifiableStatusByFollowableID("+followedID+"," + notifiable + ")");
        // process update
        this.setFollowableNotifiableStatusByFollowable(userService.getById(followedID), notifiable);
    }

    public void setFollowableNotifiableStatusByFollowable(User followed, Followable.FollowableNotifiable notifiable) {
        // update followable User status
        followed.setFollowableNotifiable(notifiable);
        userService.createOrUpdate(followed);

        // get all FST and loop with modification
        fstRepository.findAllByFollowed(followed)
                .forEach( fst -> {
                    fst.setFollowedNotifiable(notifiable);
                    //  System.out.println("fstId: "+fst.getId()+" -> " + fst.getFollowedNotifiable());
                    fstRepository.save(fst);
                });
    }

    @Override
    public void setFollowableNotifiableStatusByFollowerID(Long followerID, Followable.FollowableNotifiable notifiable) {
        // set for a specific FST by current user as followed and provided user as follower
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowableStatusBsetFollowableNotifiableStatusByFollowerIDyFollowerID("+followerID+"," + notifiable + ")");
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                userService.getById(followerID), authenticationService.getCurrentUser());
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        // process update
        this.setFollowableNotifiableStatusByFST(fst, notifiable);
    }

    @Override
    public void setFollowableNotifiableStatusByFSTID(Long fstId, Followable.FollowableNotifiable notifiable) {
        // set for a specific FST
        //  System.out.println("FollowStateTrackerServiceImpl.setFollowableNotifiableStatusByFSTID("+fstId+"," + notifiable + ")");
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );
        // process update
        this.setFollowableNotifiableStatusByFST(fst, notifiable);
    }

    public void setFollowableNotifiableStatusByFST(FollowStateTracker fst, Followable.FollowableNotifiable notifiable) {
        // set for all FST by provided user as followed
        fst.setFollowedNotifiable(notifiable);
        //  System.out.println("fstId: "+fst.getId()+" -> " + fst.getFollowedNotifiable());
        fstRepository.save(fst);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // notification methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void pushNotifications(Set<Notification> notifications) {
        this.pushNotifications(authenticationService.getCurrentUser(), notifications);
    }

    @Override
    public void pushNotifications(Long followedID, Set<Notification> notifications) {
        this.pushNotifications(userService.getById(followedID), notifications);
    }

    public void pushNotifications(User followed, Set<Notification> notifications) {
        fstRepository.findAllByFollower(followed)
                .forEach( fst -> {
                    fst.pushNotifications(notifications);
                    fstRepository.save(fst);
                });
    }

    @Override
    public Optional<Set<Notification>> getAllNotifications() {
        return this.getAllNotificationsByFollower(authenticationService.getCurrentUser());
    }

    @Override
    public Optional<Set<Notification>> getAllNotificationsByFollowerId(Long followerID) {
        return this.getAllNotificationsByFollower(userService.getById(followerID));
    }

    public Optional<Set<Notification>> getAllNotificationsByFollower(User follower) {
        Set<Notification> notifications = new HashSet<>();
        // get all FST and loop with modification
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> notifications.addAll(fst.getNotifications()) );
        return Optional.of(notifications);
    }

    @Override
    public Optional<Set<Notification>> getAllNotificationsByFollowedId(Long followedID) {
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                authenticationService.getCurrentUser(), userService.getById(followedID));
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        return this.getAllNotificationsByFST(fst);
    }

    @Override
    public Optional<Set<Notification>> getAllNotificationsByFollowerIdAndByFollowedId(Long followerID, Long followedID) {
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                userService.getById(followerID), userService.getById(followedID));
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        return this.getAllNotificationsByFST(fst);
    }

    public Optional<Set<Notification>> getAllNotificationsByFST(FollowStateTracker fst) {
        Set<Notification> notifications = fst.getNotifications().stream().collect(Collectors.toSet());
        return Optional.of(notifications);
    }

    @Override
    public Optional<Map<Long, String>> getAllLabels() {
        return this.getAllLabelsByFollower(authenticationService.getCurrentUser());
    }

    @Override
    public Optional<Map<Long, String>> getAllLabelsByFollowerId(Long followerID) {
        return this.getAllLabelsByFollower(userService.getById(followerID));
    }

    public Optional<Map<Long, String>> getAllLabelsByFollower(User follower) {
        Map<Long, String> labelMap = new HashMap<>();
        // get all FST and loop with modification
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> fst.getNotifications()
                        .forEach( item -> labelMap.put(item.getId(), item.getLabel()) ));
        return Optional.of(labelMap);
    }

    @Override
    public Boolean isNotificationsEmpty() {
        return this.isNotificationsEmpty(authenticationService.getCurrentUser());
    }

    @Override
    public Boolean isNotificationsEmpty(Long followerID) {
        return this.isNotificationsEmpty(userService.getById(followerID));
    }

    public Boolean isNotificationsEmpty(User follower){
//        Boolean isEmpty = true;
        AtomicReference<Boolean> isEmpty = new AtomicReference<>(true);
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    isEmpty.updateAndGet( v -> v && fst.getNotifications().isEmpty() );
                });
        return isEmpty.get();
    }

    @Override
    public Long notificationsSize() {
        return this.notificationsSize(authenticationService.getCurrentUser());
    }

    @Override
    public Long notificationsSize(Long followerID) {
        return this.notificationsSize(userService.getById(followerID));
    }

    public Long notificationsSize(User follower){
        AtomicReference<Long> notificationsSize = new AtomicReference<>(0L);
        fstRepository.findAllByFollower(follower)
        .forEach( fst -> {
            notificationsSize.updateAndGet( v -> v + fst.getNotifications().size() );
        });
        return notificationsSize.get();
    }

    @Override
    public Long unreadNotificationsCount() {
        return this.unreadNotificationsCount(authenticationService.getCurrentUser());
    }

    @Override
    public Long unreadNotificationsCount(Long followerID){
        return this.unreadNotificationsCount(userService.getById(followerID));
    }

    public Long unreadNotificationsCount(User follower){
        Long unreadCount =
                fstRepository.findAllByFollower(follower).stream()
                .map( fst -> fst.getNotifications().stream()
                        .filter(Notification::getRead)
                        .count()
                ).count();
        return unreadCount;
    }

    @Override
    public void setNotificationsReadStatus(Set<Notification> notifications, Boolean isRead) {
        this.setNotificationsReadStatus(authenticationService.getCurrentUser(), notifications, isRead);
    }

    @Override
    public void setNotificationsReadStatus(Long followerID, Set<Notification> notifications, Boolean isRead) {
        this.setNotificationsReadStatus(userService.getById(followerID), notifications, isRead);
    }

    public void setNotificationsReadStatus(User follower, Set<Notification> notifications, Boolean isRead) {
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.setNotificationReadStatus(notifications, isRead);
                    fstRepository.save(fst);
                });
    }

    @Override
    public void popNotifications(Set<Notification> notifications) {
        this.popNotifications(authenticationService.getCurrentUser(), notifications);
    }

    @Override
    public void popNotifications(Long followerID, Set<Notification> notifications) {
        this.popNotifications(userService.getById(followerID), notifications);
    }

    public void popNotifications(User follower, Set<Notification> notifications) {
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.popNotifications(notifications);
                    fstRepository.save(fst);
                });
    }

}
