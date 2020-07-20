package fr.uca.cdr.skillful_network.services.impl.user;

import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.entities.user.FollowStateTracker;
import fr.uca.cdr.skillful_network.entities.user.Followable;
import fr.uca.cdr.skillful_network.entities.user.Notification;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.repositories.user.FollowStateTrackerRepository;
import fr.uca.cdr.skillful_network.repositories.user.NotificationRepository;
import fr.uca.cdr.skillful_network.services.AuthenticationService;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import static fr.uca.cdr.skillful_network.entities.user.Follower.FollowerNotifiable;
import static fr.uca.cdr.skillful_network.entities.user.Follower.FollowerStatus;

@Service
public class FollowStateTrackerServiceImpl implements FollowStateTrackerService {

    private static final Logger logger = LoggerFactory.getLogger(FollowStateTrackerServiceImpl.class);

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
        logger.debug("getAllFollowersByFollowableID(followableID: {}})", followableID);
        return Optional.of(
                fstRepository.findAllByFollowed(userService.getById(followableID)).stream()
                        .map(FollowStateTracker::getFollower)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Long getFollowerCount(){
        return this.getFollowerCount(authenticationService.getCurrentUser().getId());
    }

    @Override
    public Long getFollowerCount(Long followableID){
        logger.debug("getFollowerCount(followableID: {}})", followableID);
        return (long) fstRepository.findAllByFollowed(userService.getById(followableID)).size();
    }

    @Override
    public boolean follow(Long followableID) {
        return this.follow(authenticationService.getCurrentUser().getId(), followableID);
    }

    @Override
    public boolean follow(Long followerID, Long followableID) {
        logger.debug("follow(followerID: {}, followableID: {})", followerID, followableID);
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
        logger.debug("unfollowByFollowedID(followedID: {})", followedID);
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
        logger.debug("unfollowByFSTId(fstId: {})", fstId);
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

        //update follower
        User follower = fst.getFollower();
        follower.getFollowerSet().remove(fst);

        // delete FST
        fstRepository.delete(fst);
    }

    @Override
    public void setFollowerStatus(FollowerStatus status) {
        logger.debug("setFollowerStatus(status: {})", status);
        this.setFollowerStatusByFollower(authenticationService.getCurrentUser(), status);
    }

    @Override
    public void setFollowerStatusByFollowerID(Long followerID, FollowerStatus status) {
        logger.debug("setFollowerStatusByFollowerID(followerID: {}, status: {})", followerID, status);
        this.setFollowerStatusByFollower(userService.getById(followerID), status);
    }

    public void setFollowerStatusByFollower(User follower, FollowerStatus status) {
        // get all FST and loop with modification
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.setFollowerStatus(status);
                    logger.debug("fstId: {} -> status: {})", fst.getId(), fst.getFollowerStatus());
                    fstRepository.save(fst);
                });
    }

    @Override
    public void setFollowerStatusByFollowedID(Long followedID, FollowerStatus status) {
        logger.debug("setFollowerStatusByFollowedID(followedID: {}, status: {})", followedID, status);
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                authenticationService.getCurrentUser(), userService.getById(followedID));
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        this.setFollowerStatusByFST(fst, status);
    }

    @Override
    public void setFollowerStatusByFSTID(Long fstId, FollowerStatus status) {
        logger.debug("setFollowerStatusByFSTID(fstId: {}, status: {})", fstId, status);
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );
        // process update
        this.setFollowerStatusByFST(fst, status);
    }

    public void setFollowerStatusByFST(FollowStateTracker fst, FollowerStatus status) {
        fst.setFollowerStatus(status);
        logger.debug("fstId: {} -> status: {})", fst.getId(), fst.getFollowerStatus());
        fstRepository.save(fst);
    }

    @Override
    public void setFollowerNotifiableStatus(FollowerNotifiable notifiable) {
        logger.debug("setFollowerNotifiableStatus(notifiable: {}", notifiable);
        this.setFollowerNotifiableStatusByFollower(authenticationService.getCurrentUser(), notifiable);
    }

    @Override
    public void setFollowerNotifiableStatusByFollowerID(Long followerID, FollowerNotifiable notifiable) {
        logger.debug("setFollowerNotifiableStatusByFollowerID(followerID: {}, notifiable {})", followerID, notifiable);
        this.setFollowerNotifiableStatusByFollower(userService.getById(followerID), notifiable);
    }

    public void setFollowerNotifiableStatusByFollower(User follower, FollowerNotifiable notifiable) {
        // get all FST and loop with modification
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.setFollowerNotifiable(notifiable);
                    logger.debug("fstId: {} -> notifiable: {})", fst.getId(), fst.getFollowerNotifiable());
                    fstRepository.save(fst);
                });
    }

    @Override
    public void setFollowerNotifiableStatusByFollowedID(Long followedID, FollowerNotifiable notifiable) {
        logger.debug("setFollowerNotifiableStatusByFollowedID(followedID: {}, notifiable: {})", followedID, notifiable);
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                authenticationService.getCurrentUser(), userService.getById(followedID));
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        this.setFollowerNotifiableByFST(fst, notifiable);
    }

    @Override
    public void setFollowerNotifiableStatusByFSTID(Long fstId, FollowerNotifiable notifiable) {
        logger.debug("setFollowerNotifiableStatusByFSTID(fstId: {}, notifiable: {})", fstId, notifiable);
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );
        this.setFollowerNotifiableByFST(fst, notifiable);
    }

    public void setFollowerNotifiableByFST( FollowStateTracker fst, FollowerNotifiable notifiable) {
        fst.setFollowerNotifiable(notifiable);
        logger.debug("fstId: {} -> notifiable: {})", fst.getId(), fst.getFollowerNotifiable());
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
    public Long getFollowedCount(){
        return this.getFollowedCount(authenticationService.getCurrentUser().getId());
    }

    @Override
    public Long getFollowedCount(Long followerID){
        logger.debug("getFollowedCount(followerID: {})", followerID);
        return (long) fstRepository.findAllByFollower(userService.getById(followerID)).size();
    }

    @Override
    public void banFollower(Long followerID) {
        this.banFollower(authenticationService.getCurrentUser().getId(), followerID);
    }

    @Override
    public void banFollower(Long followedID, Long followerID) {
        logger.debug("banFollower(followedID: {}, followerID: {})", followedID, followerID);
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
        fst.setFollowerStatus(FollowerStatus.banned);
        fstRepository.save(fst);
    }

    @Override
    public void setFollowableStatus(Followable.FollowableStatus status) {
        // set for all FST by current user as followed
        logger.debug("setFollowableStatus(status: {})", status);
        // process update
        this.setFollowableStatusByFollowable(authenticationService.getCurrentUser(), status);
    }

    @Override
    public void setFollowableStatusByFollowedID(Long followedID, Followable.FollowableStatus status) {
        // set for all FST by provided user as followed
        logger.debug("setFollowableStatusByFollowedID(followedID: {}, status: {})", followedID, status);
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
                    logger.debug("-> fstId: {} -> status: {})", fst.getId(), fst.getFollowedStatus());
                    fstRepository.save(fst);
                });
    }

    @Override
    public void setFollowableStatusByFollowerID(Long followerID, Followable.FollowableStatus status) {
        // set for a specific FST by current user as followed and provided user as follower
        logger.debug("setFollowableStatusByFollowerID(followerID: {}, status: {})", followerID, status);
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
        logger.debug("setFollowableStatusByFSTID(fstId: {}, status: {})", fstId, status);
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );
        // process update
        this.setFollowableStatusByFST(fst, status);
    }

    public void setFollowableStatusByFST(FollowStateTracker fst, Followable.FollowableStatus status) {
        // set for all FST by provided user as followed
        fst.setFollowedStatus(status);
        logger.debug("fstId: {} -> status: {})", fst.getId(), fst.getFollowedStatus());
        fstRepository.save(fst);
    }

    @Override
    public void setFollowableNotifiableStatus(Followable.FollowableNotifiable notifiable) {
        // set for all FST by current user as followed
        logger.debug("setFollowableNotifiableStatus(notifiable: {})", notifiable);
        // process update
        this.setFollowableNotifiableStatusByFollowable(authenticationService.getCurrentUser(), notifiable);
    }

    @Override
    public void setFollowableNotifiableStatusByFollowedID(Long followedID, Followable.FollowableNotifiable notifiable) {
        // set for all FST by provided user as followed
        logger.debug("setFollowableNotifiableStatusByFollowedID(followedID: {}, notifiable: {})", followedID, notifiable);
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
                    logger.debug("-> fstId: {} -> notifiable: {})", fst.getId(), fst.getFollowedNotifiable());
                    fstRepository.save(fst);
                });
    }

    @Override
    public void setFollowableNotifiableStatusByFollowerID(Long followerID, Followable.FollowableNotifiable notifiable) {
        // set for a specific FST by current user as followed and provided user as follower
        logger.debug("setFollowableNotifiableStatusByFollowerID(followerID: {}, notifiable: {})", followerID, notifiable);
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
        logger.debug("setFollowableNotifiableStatusByFSTID(fstId: {}, notifiable: {})", fstId, notifiable);
        // get FST
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );
        // process update
        this.setFollowableNotifiableStatusByFST(fst, notifiable);
    }

    public void setFollowableNotifiableStatusByFST(FollowStateTracker fst, Followable.FollowableNotifiable notifiable) {
        // set for all FST by provided user as followed
        fst.setFollowedNotifiable(notifiable);
        logger.debug("fstId: {} -> notifiable: {})", fst.getId(), fst.getFollowedNotifiable());
        fstRepository.save(fst);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // notification methods
    ////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public void pushNotifications(Set<Post>  posts) {
        this.pushNotifications(authenticationService.getCurrentUser(), posts);
    }

    @Override
    public void pushNotifications(Long followedID, Set<Post>  posts) {
        this.pushNotifications(userService.getById(followedID), posts);
    }

    public void pushNotifications(User followed, Set<Post>  posts) {
        logger.debug("pushNotifications(followed: {}, posts: {})", followed.getId(), posts);

        // process update
        fstRepository.findAllByFollowed(followed)
                .forEach( fst -> {
                    logger.debug("-> fst: {}", fst.getId());
                    // filtering out
                    if (fst.getFollowedStatus() != Followable.FollowableStatus.on) {
                        logger.debug("-> push prohibited (FollowedStatus: {})", fst.getFollowedStatus());
                    } else {
                        switch (fst.getFollowedNotifiable()) {
                            case all:
                                // create new Notiffication Set
                                Set<Notification> notifications = posts.stream()
                                        .map( post -> {
                                            Notification newNotification = new Notification(post);
                                            notificationRepository.save(newNotification);
                                            return newNotification;
                                        })
                                        .collect(Collectors.toSet());
                                logger.debug("notificationSet: {}", notifications);

                                fst.pushNotifications(notifications);
                                fstRepository.save(fst);
                                break;
                            default:
                                logger.debug("-> notifications filtered out (FollowableNotifiable: {})", fst.getFollowedNotifiable());
                        }
                    }
                });
    }

    @Override
    public void pushNotificationLabels(Set<String> labels) {
        this.pushNotificationLabels(authenticationService.getCurrentUser(), labels);
    }

    @Override
    public void pushNotificationLabels(Long followedID, Set<String> labels) {
        this.pushNotificationLabels(userService.getById(followedID), labels);
    }

    public void pushNotificationLabels(User followed, Set<String> labels) {
        logger.debug("pushNotifications(followed: {}, labels: {})", followed.getId(), labels);

        // persist notifications
//        notifications.stream()
//                .filter( n -> n.followerSetSize() == 0 )
//                .forEach( n -> notificationRepository.save(n) );

        // process update
        fstRepository.findAllByFollowed(followed)
                .forEach( fst -> {
                    logger.debug("-> fst: {}", fst.getId());
                    // filtering out
                    if (fst.getFollowedStatus() != Followable.FollowableStatus.on) {
                        logger.debug("-> push prohibited (FollowedStatus: {})", fst.getFollowedStatus());
                    } else {
                        switch (fst.getFollowedNotifiable()) {
                            case all:
                                // create new Notiffication Set
                                Set<Notification> notifications = labels.stream()
                                        .map( label -> {
                                            Notification newNotification = new Notification(label);
                                            notificationRepository.save(newNotification);
                                            return newNotification;
                                        })
                                        .collect(Collectors.toSet());
                                logger.debug("notificationSet: {}", notifications);

                                fst.pushNotifications(notifications);
                                fstRepository.save(fst);
                                break;
                            default:
                                logger.debug("-> notifications filtered out (FollowableNotifiable: {})", fst.getFollowedNotifiable());
                        }
                    }
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
        logger.debug("getAllNotificationsByFollower(follower: {})", follower.getId());
        // get all FST and loop with modification
        return Optional.of(fstRepository.findAllByFollower(follower).stream()
                .flatMap(fst -> fst.getNotifications().stream())
                .collect(Collectors.toSet()));
    }

    @Override
    public Optional<Set<Notification>> getAllNotificationsByFollowedId(Long followedID) {
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                authenticationService.getCurrentUser(), userService.getById(followedID));
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        return Optional.of(this.getAllNotificationsByFST(fst));
    }

    @Override
    public Optional<Set<Notification>> getAllNotificationsByFollowerIdAndByFollowedId(Long followerID, Long followedID) {
        FollowStateTracker fst =  fstRepository.findByFollowerAndFollowed(
                userService.getById(followerID), userService.getById(followedID));
        if ( fst == null)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.");
        }
        return Optional.of(this.getAllNotificationsByFST(fst));
    }

    public Set<Notification> getAllNotificationsByFST(FollowStateTracker fst) {
        logger.debug("getAllNotificationsByFST(fst: {})", fst.getId());
        Set<Notification> notifications = new HashSet<>();
        // filtering out
        if (fst.getFollowerStatus() != FollowerStatus.on) {
            logger.debug("-> get() prohibited (FollowerStatus: {})", fst.getFollowerStatus());
        } else {
            switch (fst.getFollowerNotifiable()) {
                case all :
                notifications = fst.getNotifications();
                    break;
                default:
                    logger.debug("-> notifications filtered out (FollowerNotifiable: {})", fst.getFollowerNotifiable());
            }
        }
        return notifications;
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
        logger.debug("getAllLabelsByFollower(follower: {})", follower.getId());
        // get all FST and loop with modification
        return Optional.of(fstRepository.findAllByFollower(follower).stream()
                .flatMap(fst -> fst.getNotifications().stream())
                .collect(Collectors.toMap(Notification::getId, Notification::getLabel)));
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
        logger.debug("isNotificationsEmpty(User: {})", follower.getId());
        return fstRepository.findAllByFollower(follower).stream()
                .allMatch( fst -> fst. getNotifications().isEmpty() );
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
        logger.debug("notificationsSize(User: {})", follower.getId());
//        AtomicReference<Long> notificationsSize = new AtomicReference<>(0L);
//        fstRepository.findAllByFollower(follower).forEach( fst -> notificationsSize.updateAndGet( v -> v + fst.getNotifications().size() ) );
//        return notificationsSize.get();
        return (long) fstRepository.findAllByFollower(follower).stream()
                .reduce(0, (subtotal, fst) -> subtotal + fst.getNotifications().size(), Integer::sum);
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
        logger.debug("unreadNotificationsCount(User: {})", follower.getId());
        long unreadCount = 0L;
        for (FollowStateTracker fst : fstRepository.findAllByFollower(follower)) {
            long count = fst.getNotifications().stream()
                    .filter( n -> ! n.getRead())
                    .count();
            unreadCount+=count;
        }
        return unreadCount;
    }

    @Override
    public Set<Notification> unreadNotifications() {
        return this.unreadNotifications(authenticationService.getCurrentUser());
    }

    @Override
    public Set<Notification> unreadNotifications(Long followerID) {
         return this.unreadNotifications(userService.getById(followerID));
    }

    public Set<Notification> unreadNotifications(User follower) {
        logger.debug("unreadNotificationsCount(User: {})", follower.getId());
        Set<Notification> unread = new HashSet<>();
        for (FollowStateTracker fst : fstRepository.findAllByFollower(follower)) {
            unread.addAll(
                    fst.getNotifications().stream()
                    .filter( n -> ! n.getRead())
                    .collect(Collectors.toSet())
            );
        }
        logger.debug("notifications unread found : {}", unread.toString());
        logger.debug("notifications List is empty : {}", unread.isEmpty());
        return unread;
    }

    @Override
    public void setNotificationReadStatus(Long notificationId, Boolean isRead) {
        this.setNotificationReadStatus(authenticationService.getCurrentUser(), notificationId, isRead);
    }

    @Override
    public void setNotificationReadStatus(Long followerID, Long notificationId, Boolean isRead) {
        this.setNotificationReadStatus(userService.getById(followerID), notificationId, isRead);
    }

    public void setNotificationReadStatus(User follower, Long notificationId, Boolean isRead) {
        logger.debug("setNotificationReadStatus(User: {}, Long: {}, Boolean: {})", follower.getId(), notificationId, isRead);
        // get notificationSet from id
        Set<Notification> notifications = notificationRepository.findById(notificationId).map(Collections::singleton).orElse(Collections.emptySet());
        // process update
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.setNotificationReadStatus(notifications, isRead);
                    fstRepository.save(fst);
                });
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
        logger.debug("setNotificationsReadStatus(User: {}, Boolean: {})", follower.getId(), isRead);
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.setNotificationReadStatus(notifications, isRead);
                    fstRepository.save(fst);
                });
    }

    @Override
    public void updateNotifications(Long followedID, Set<Post>  posts) {
        logger.debug("updateNotifications(followedID: {}, posts: {})", followedID, posts);

        // get and update notifications from post id
        posts.stream()
                .flatMap(post -> notificationRepository.findByPostId(post.getId()).stream())
                .map(notification -> {
                    posts.stream()
                            .filter( post -> ( post.getId() == notification.getPostId()))
                            .forEach( post -> {
                                notification.updateNotification(post);
                                logger.debug("-> notification: {}", notification);
                            });
                    return notification;
                })
                .forEach(notification -> {
                    // update FST
                    notification.followerSet().stream()
                        .filter( fst -> fst.getFollowed().getId() == followedID)
                        .forEach( fst -> {
                            logger.debug("-> fst: {}", fst.getId());
                            // filtering out
                            if (fst.getFollowedStatus() != Followable.FollowableStatus.on) {
                                logger.debug("-> update prohibited (FollowedStatus: {})", fst.getFollowedStatus());
                            } else {
                                switch (fst.getFollowedNotifiable()) {
                                    case all:
                                        fstRepository.save(fst);
                                        break;
                                    default:
                                        logger.debug("-> notifications filtered out (FollowableNotifiable: {})", fst.getFollowedNotifiable());
                                }
                            }});
                });
    }

    @Override
    public void popNotification(Long notificationId) {
        this.popNotification(authenticationService.getCurrentUser(), notificationId);
    }

    @Override
    public void popNotification(Long followerID, Long notificationId) {
        this.popNotification(userService.getById(followerID), notificationId);
    }

    public void popNotification(User follower, Long notificationId) {
        logger.debug("popNotification(User: {}, Long: {})", follower.getId(), notificationId);

        // get notificationSet from id
        Set<Notification> notifications = notificationRepository.findById(notificationId).map(Collections::singleton).orElse(Collections.emptySet());
        logger.debug("-> notifications: {})", notifications);

        // process update
        this.popNotifications(follower, notifications);
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
        logger.debug("popNotifications(User: {}, Set<Long>: {})", follower.getId(), notifications);

        // process update
        fstRepository.findAllByFollower(follower)
                .forEach( fst -> {
                    fst.popNotifications(notifications);
                    fstRepository.save(fst);
                });

        // clean orphan notifications
        cleanOrphanNotifications(notifications);
    }


    @Override
    public void popNotificationsByPostIds(Long followedID, Set<Long>  postIds) {
        logger.debug("popNotificationsByPosts(followedID: {}, postIdsd: {})", followedID, postIds);

        // get notificationSet from post id
        Set<Notification> notifications = postIds.stream()
                .flatMap(postId -> notificationRepository.findByPostId(postId).stream())
                .collect(Collectors.toSet());
        logger.debug("-> notifications: {})", notifications);

        // process update
        fstRepository.findAllByFollowed(userService.getById(followedID))
                .forEach( fst -> {
                    logger.debug("-> fst: {}", fst.getId());
                    fst.popNotifications(notifications);
                    fstRepository.save(fst);
                });

        // clean orphan notifications
        cleanOrphanNotifications(notifications);
    }

    // clean orphan notifications
    public void cleanOrphanNotifications(Set<Notification> notifications) {
        notifications.stream()
                .filter( n -> n.followerSetSize() == 0 )
            .forEach( n -> notificationRepository.delete(n) );
}
}
