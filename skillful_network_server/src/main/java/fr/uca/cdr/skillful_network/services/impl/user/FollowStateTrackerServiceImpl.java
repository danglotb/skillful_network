package fr.uca.cdr.skillful_network.services.impl.user;

import fr.uca.cdr.skillful_network.entities.user.*;
import fr.uca.cdr.skillful_network.repositories.user.FollowStateTrackerRepository;
import fr.uca.cdr.skillful_network.repositories.user.NotificationRepository;
import fr.uca.cdr.skillful_network.repositories.user.UserRepository;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowStateTrackerServiceImpl implements FollowStateTrackerService {

    @Autowired
    private FollowStateTrackerRepository fstRepository;

    @Autowired
    private NotificationRepository notificationRepository;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
    public Optional<List<FollowStateTracker>> getFSTByFollowerID(Long followerID) {
        return Optional.of( fstRepository.findAllByFollower(userService.getById(followerID)) );
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
    public boolean follow(Long followerID, Long followableID) {
        System.out.println("FollowStateTrackerServiceImpl.follow("+followerID+","+followableID+")");
        if ( followableID == followerID ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de se suivre soi même!");
        }

        User follower = userService.getById(followerID);
        User followable = userService.getById(followableID);
        if ( ! fstRepository.findAllByFollowerAndFollowed(follower, followable).isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi est déjà activé.");
        }

        return ( fstRepository.save(new FollowStateTracker(followable, follower)) != null) ;
    }

    @Override
    public void unfollowByFSTId(Long fstId) {
        System.out.println("FollowStateTrackerServiceImpl.unfollowById("+fstId+")");
        FollowStateTracker fst = fstRepository.findById(fstId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le suivi n'existe pas.") );

        //update followed
        User followed = fst.getFollowed();
        followed.getFollowableSet().remove(fst);
        userService.createOrUpdate(followed);

        //update follower
        User follower = fst.getFollower();
        follower.getFollowerSet().remove(fst);
        userService.createOrUpdate(follower);

        fstRepository.delete(fst);
    }

    @Override
    public void setFollowerStatusByFollowableID(Long followerID, Long followableID, Follower.FollowerStatus status) {

    }

    @Override
    public void setFollowerNotifiableStatusByFollowableID(Long followerID, Long followableID, Follower.FollowerNotifiable notifiable) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Followable methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Optional<List<FollowStateTracker>> getAllFSTByFollowableID(Long followableID) {
        return Optional.of( fstRepository.findAllByFollowed(userService.getById(followableID)) );
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
    public void banFollower(Long followableID, Long followerID) {

    }

    @Override
    public void setFollowableStatus(Long followableID, Followable.FollowableStatus status) {

    }

    @Override
    public void setFollowableNotifiableStatus(Long followableID, Followable.FollowableNotifiable notifiable) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // notification methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Optional<List<Notification>> getAllNotifications() {
        return Optional.empty();
    }

    @Override
    public Optional<List<Notification>> getAllNotificationsByFollower(Long followerID) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Notification>> getAllNotificationsByFollowable(Long followableID) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Notification>> getAllNotificationsByFollowerAndByFollowable(Long followerID, Long followableID) {
        return Optional.empty();
    }

    @Override
    public void setNotifcationsReadStatus(Long followerID, List<Notification> notifications, Boolean isRead) {

    }

    @Override
    public void popNotifications(Long followerID, List<Notification> notifications) {

    }
}
