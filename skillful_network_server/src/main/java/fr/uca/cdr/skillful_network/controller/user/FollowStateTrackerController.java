package fr.uca.cdr.skillful_network.controller.user;

import fr.uca.cdr.skillful_network.entities.user.Notification;
import fr.uca.cdr.skillful_network.entities.user.User;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/fst")
public class FollowStateTrackerController {

    @Autowired
    private FollowStateTrackerService fstService;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Follower methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followed/{followerId}")
    public ResponseEntity<List<User>> getAllFollowedByFollower(@PathVariable(value = "followerId") Long followerId) {
        List<User> followedList = new ArrayList<>();
        return new ResponseEntity<List<User>>( followedList, HttpStatus.OK);
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follow/{followerId}/{followableId}")
    public ResponseEntity<Boolean> follow(@PathVariable(value = "followerId") Long followerId, @PathVariable(value = "followableId") Long followableId) {
        return new ResponseEntity<Boolean>( false, HttpStatus.OK);
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/unfollow/{followerId}/{followableId}")
    public ResponseEntity<Boolean> unfollow(@PathVariable(value = "followerId") Long followerId, @PathVariable(value = "followableId") Long followableId) {
        return new ResponseEntity<Boolean>( false, HttpStatus.OK);
    };

    //public void setFollowerStatusByFollowable(User follower, User followable, Follower.FollowerStatus status);
    //public void setFollowerNotifiableStatusByFollowable(User follower, User followable, Follower.FollowerNotification notifiable);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Followable methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //public Optional<List<User>>  getAllFollowersBbyFollowable(User followable);
    //public void banFollower(User followed, User follower);
    //public void setFollowableStatus(User followable, Followable.FollowableStatus status);
    //public void setFollowableNotifiableStatus(User followable, Followable.FollowableNotification notifiable);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // notification methods
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification")
    public ResponseEntity<List<Notification>> getAllNotifications()  {
        List<Notification> notificationList = new ArrayList<>();
        return new ResponseEntity<List<Notification>>( notificationList, HttpStatus.OK);
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/{followerId}")
    public ResponseEntity<List<Notification>> getAllNotificationsByFollower(
            @PathVariable(value = "followerId") Long followerId)  {

        List<Notification> notificationList = new ArrayList<>();
        return new ResponseEntity<List<Notification>>( notificationList, HttpStatus.OK);
    };

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/{followerId}/{followableId}")
    public ResponseEntity<List<Notification>> getAllNotificationsByFollowerAndByFollowable(
            @PathVariable(value = "followerId") Long followerId,
            @PathVariable(value = "followableId") Long followableId)  {

        List<Notification> notificationList = new ArrayList<>();
        return new ResponseEntity<List<Notification>>( notificationList, HttpStatus.OK);
    };

    //Optional<List<Notification>> getAllNotificationsByFollowable(User followable) {};

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/read/{followerId}")
    public void setNotifcationsReadStatus(@PathVariable(value = "followerId") Long followerId, @Valid @RequestBody List<Notification> notifications, @RequestParam(name = "read", defaultValue = "true") Boolean isRead)  {};

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/pop/{followerId}")
    public void popNotifications(@PathVariable(value = "followerId") Long followerId, @Valid @RequestBody List<Notification> notifications) {};;
}
