package fr.uca.cdr.skillful_network.controller.user;

import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.entities.user.*;
import fr.uca.cdr.skillful_network.services.user.FollowStateTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/fst")
public class FollowStateTrackerController {

    private static final Logger logger = LoggerFactory.getLogger(FollowStateTrackerController.class);

    @Autowired
    private FollowStateTrackerService fstService;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Global methods :
    //
    // /all                 getAllFST()
    // /{Id}                getFSTById(Id)
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<FollowStateTracker>> getAllFST() {
        List<FollowStateTracker> fstList = fstService.getAllFST()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>(fstList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<FollowStateTracker> getFSTById(@PathVariable(value = "id") Long id) {
        FollowStateTracker fst = fstService.getFSTById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance trouvée avec l'id: " + id));
        return new ResponseEntity<>( fst, HttpStatus.OK);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Follower methods :
    //
    // /all/follower                                    getAllFSTByFollower()                   (currentUser -> follower)
    // /all/follower/{followerId}                       getAllFSTByFollowerID(followerId)
    //
    // /followers                                       getAllFollowersByFollowable()           (currentUser -> followable)
    // /followers/{followableId}                        getAllFollowersByFollowableID(followableId)
    //
    // /followers/count                                 getFollowerCount()                      (currentUser -> followable)
    // /followers/count/{followableId}                  getFollowerCount(Long followableID)
    //
    // /follow/{followableId}                           follow(followableId)                    (currentUser -> follower)
    // /follow/{followerId}/{followableId}              follow(followerId, followableId)
    // /unfollow/{followedId}                           unfollowByFollowedID(followedId)        (currentUser -> follower)
    // /unfollow/fst/{fstId}                            unfollowByFSTId(fstId)
    //
    // /follower/status/{status}                        setFollowerStatus(status)               (currentUser -> follower)
    // /follower/status/{status}?followerId=            setFollowerStatusByFollowerID(followerId, status)
    // /follower/status/{status}?followedId=            setFollowerStatusByFollowedID(followedId, status) (currentUser -> follower)
    // /follower/status/{status}?fstId=                 setFollowerStatusByFSTID(fstId, status)
    //
    // /follower/notifiable/{notifiable}                setFollowerNotifiableStatus(notifiable) (currentUser -> follower)
    // /follower/notifiable/{notifiable}?followerId=    setFollowerNotifiableStatusByFollowerID (followerId, notifiable)
    // /follower/notifiable/{notifiable}?followerId=    setFollowerNotifiableStatusByFollowedID (followedId, notifiable) (currentUser -> follower)
    // /follower/notifiable/{notifiable}?fstId=         setFollowerNotifiableStatusByFSTID(fstId, notifiable)
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all/follower")
    public ResponseEntity<List<FollowStateTracker>> getAllFSTByFollower() {

        List<FollowStateTracker> fstList = fstService.getAllFSTByFollower()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>(fstList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all/follower/{followerId}")
    public ResponseEntity<List<FollowStateTracker>> getAllFSTByFollowerID(@PathVariable(value = "followerId") Long followerId) {

        List<FollowStateTracker> fstList = fstService.getFSTByFollowerID(followerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>(fstList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers")
    public ResponseEntity<List<User>> getAllFollowersByFollowable() {
        List<User> followedList = fstService.getAllFollowersByFollowable()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>( followedList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers/{followableId}")
    public ResponseEntity<List<User>> getAllFollowersByFollowableID(
            @PathVariable(value = "followableId") Long followableId) {
        List<User> followedList = fstService.getAllFollowersByFollowableID(followableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>( followedList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers/count")
    public ResponseEntity<Long> getFollowerCount(){
        return new ResponseEntity<>(fstService.getFollowerCount(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followers/count/{followableId}")
    public ResponseEntity<Long> getFollowerCount(
            @PathVariable(value = "followableId") Long followableID) {
        return new ResponseEntity<>(fstService.getFollowerCount(followableID), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follow/{followableId}")
    public ResponseEntity<Boolean> follow(
            @PathVariable(value = "followableId") Long followableId) {

        if ( fstService.follow(followableId)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>( false, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follow/{followerId}/{followableId}")
    public ResponseEntity<Boolean> follow(
            @PathVariable(value = "followerId") Long followerId,
            @PathVariable(value = "followableId") Long followableId) {

        if ( fstService.follow(followerId, followableId)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>( false, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @DeleteMapping(value = "/unfollow/{followedId}")
    public ResponseEntity<Boolean> unfollowByFollowedID(
            @PathVariable(value = "followedId") Long followedId) {

        fstService.unfollowByFollowedID(followedId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @DeleteMapping(value = "/unfollow/fst/{fstId}")
    public ResponseEntity<Boolean> unfollowByFSTId(
            @PathVariable(value = "fstId") Long fstId) {

        fstService.unfollowByFSTId(fstId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follower/status/{status}")
    public ResponseEntity<Boolean>  setFollowerStatus(
        @RequestParam Map<String,String> paramsMap,
        @Valid @PathVariable(value = "status") Follower.FollowerStatus status) {
        if ( ! paramsMap.isEmpty() )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametre(s) invalide(s) : " +  paramsMap.entrySet());
        fstService.setFollowerStatus(status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follower/status/{status}", params = "followerId")
    public ResponseEntity<Boolean>  setFollowerStatusByFollowerID(
            @RequestParam(name = "followerId") Long followerId,
            @Valid @PathVariable(value = "status") Follower.FollowerStatus status) {

        fstService.setFollowerStatusByFollowerID(followerId, status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follower/status/{status}", params = "followedId")
    public ResponseEntity<Boolean>  setFollowerStatusByFollowedID(
            @RequestParam(name = "followedId") Long followedId,
            @Valid @PathVariable(value = "status") Follower.FollowerStatus status) {

        fstService.setFollowerStatusByFollowedID(followedId, status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follower/status/{status}", params = "fstId")
    public ResponseEntity<Boolean>  setFollowerStatusByFSTID(
            @RequestParam(name = "fstId") Long fstId,
            @Valid @PathVariable(value = "status") Follower.FollowerStatus status) {

        fstService.setFollowerStatusByFSTID(fstId, status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follower/notifiable/{notifiable}")
    public ResponseEntity<Boolean>  setFollowerNotifiableStatus(
        @RequestParam Map<String,String> paramsMap,
        @Valid @PathVariable(value = "notifiable") Follower.FollowerNotifiable notifiable) {
        if ( ! paramsMap.isEmpty() )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametre(s) invalide(s) : " +  paramsMap.entrySet());
        fstService.setFollowerNotifiableStatus(notifiable);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follower/notifiable/{notifiable}", params = "followerId")
    public ResponseEntity<Boolean>  setFollowerNotifiableStatusByFollowerID(
            @RequestParam(name = "followerId") Long followerId,
            @Valid @PathVariable(value = "notifiable") Follower.FollowerNotifiable notifiable) {

        fstService.setFollowerNotifiableStatusByFollowerID(followerId, notifiable);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follower/notifiable/{notifiable}", params = "followedId")
    public ResponseEntity<Boolean>  setFollowerNotifiableStatusByFollowedID(
            @RequestParam(name = "followedId") Long followedId,
            @Valid @PathVariable(value = "notifiable") Follower.FollowerNotifiable notifiable) {

        fstService.setFollowerNotifiableStatusByFollowedID(followedId, notifiable);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/follower/notifiable/{notifiable}", params = "fstId")
    public ResponseEntity<Boolean>  setFollowerNotifiableStatusByFSTID(
            @RequestParam(name = "fstId") Long fstId,
            @Valid @PathVariable(value = "notifiable") Follower.FollowerNotifiable notifiable) {

        fstService.setFollowerNotifiableStatusByFSTID(fstId, notifiable);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Followable methods :
    //
    // /all/followed                                    getAllFSTByFollowable()                     (currentUser -> followable)
    // /all/followed/{followableId}                     getAllFSTByFollowableID(followableId)
    //
    // /followed                                        getAllFollowedByFollower()                  (currentUser -> follower)
    // /followed/{followerId}                           getAllFollowersByFollowableID(followerId)
    //
    // /followed/count                                  getFollowedCount()                      (currentUser -> follower)
    // /followed/count/{followerId}                     getFollowedCount(Long followerId)
    //
    // /ban/{followerId}                                follow(followerId)                          (currentUser -> followed)
    // /ban/{followedId}/{followerId}                   follow(followedId, followerId)
    //
    // /followed/status/{status}                        setFollowableStatus(status)                         (currentUser -> followed)
    // /followed/status/{status}?followedId=            setFollowableStatusByFollowedID(followedId, status)
    // /followed/status/{status}?followerId=            setFollowableStatusByFollowerID(followerId, status) (currentUser -> followed)
    // /followed/status/{status}?fstId=                 setFollowableStatusByFSTID(fstId, status)
    //
    // /followed/notifiable/{notifiable}                setFollowableNotifiableStatus(notifiable)           (currentUser -> followed)
    // /followed/notifiable/{notifiable}?followedId=    setFollowableNotifiableStatusByFollowedID(followedId, notifiable)
    // /followed/notifiable/{notifiable}?followerId=    setFollowableNotifiableStatusByFollowerID(followerId, notifiable) (currentUser -> followed)
    // /followed/notifiable/{notifiable}?fstId=         setFollowableNotifiableStatusByFSTID(fstId, notifiable)
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all/followed")
    public ResponseEntity<List<FollowStateTracker>> getAllFSTByFollowable() {
        List<FollowStateTracker> fstList = fstService.getAllFSTByFollowable()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>(fstList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/all/followed/{followableId}")
    public ResponseEntity<List<FollowStateTracker>> getAllFSTByFollowableID(
            @PathVariable(value = "followableId") Long followableId) {
        List<FollowStateTracker> fstList = fstService.getAllFSTByFollowableID(followableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>(fstList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followed")
    public ResponseEntity<List<User>> getAllFollowedByFollower() {

        List<User> followedList = fstService.getAllFollowedByFollower()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>(followedList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followed/{followerId}")
    public ResponseEntity<List<User>> getAllFollowedByFollowerID(
            @PathVariable(value = "followerId") Long followerId) {

        List<User> followedList = fstService.getAllFollowedByFollowerID(followerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune instance n'est suivie."));
        return new ResponseEntity<>(followedList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followed/count")
    public ResponseEntity<Long> getFollowedCount(){
        return new ResponseEntity<>(fstService.getFollowedCount(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/followed/count/{followerId}")
    public ResponseEntity<Long> getFollowedCount(
            @PathVariable(value = "followerId") Long followerId) {
        return new ResponseEntity<>(fstService.getFollowedCount(followerId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/ban/{followerId}")
    public ResponseEntity<Boolean>  banFollower(
            @PathVariable(value = "followerId") Long followerId) {

        fstService.banFollower(followerId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/ban/{followedId}/{followerId}")
    public ResponseEntity<Boolean>  banFollower(
            @PathVariable(value = "followedId") Long followedId,
            @PathVariable(value = "followerId") Long followerId) {

        fstService.banFollower(followedId, followerId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/followed/status/{status}")
    public ResponseEntity<Boolean>  setFollowableStatus(
            @RequestParam Map<String,String> paramsMap,
            @Valid @PathVariable(value = "status") Followable.FollowableStatus status) {
        if ( ! paramsMap.isEmpty() )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametre(s) invalide(s) : " +  paramsMap.entrySet());
        fstService.setFollowableStatus(status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/followed/status/{status}", params = "followedId")
    public ResponseEntity<Boolean>  setFollowableStatusByFollowedID(
            @RequestParam(name = "followedId") Long followedId,
            @Valid @PathVariable(value = "status") Followable.FollowableStatus status) {

        fstService.setFollowableStatusByFollowedID(followedId, status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/followed/status/{status}", params = "followerId")
    public ResponseEntity<Boolean>  setFollowableStatusByFollowerID(
            @RequestParam(name = "followerId") Long followedId,
            @Valid @PathVariable(value = "status") Followable.FollowableStatus status) {

        fstService.setFollowableStatusByFollowerID(followedId, status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/followed/status/{status}", params = "fstId")
    public ResponseEntity<Boolean>  setFollowedStatusByFSTID(
            @RequestParam(name = "fstId") Long fstId,
            @Valid @PathVariable(value = "status") Followable.FollowableStatus status) {

        fstService.setFollowableStatusByFSTID(fstId, status);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/followed/notifiable/{notifiable}")
    public ResponseEntity<Boolean>  setFollowableNotifiableStatus(
        @RequestParam Map<String,String> paramsMap,
        @Valid @PathVariable(value = "notifiable") Followable.FollowableNotifiable notifiable) {
        if ( ! paramsMap.isEmpty() )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametre(s) invalide(s) : " +  paramsMap.entrySet());
        fstService.setFollowableNotifiableStatus(notifiable);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/followed/notifiable/{notifiable}", params = "followedId")
    public ResponseEntity<Boolean>  setFollowableNotifiableStatusByFollowedID(
            @RequestParam(name = "followedId") Long followedId,
            @Valid @PathVariable(value = "notifiable") Followable.FollowableNotifiable notifiable) {

        fstService.setFollowableNotifiableStatusByFollowedID(followedId, notifiable);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/followed/notifiable/{notifiable}", params = "followerId")
    public ResponseEntity<Boolean>  setFollowableNotifiableStatusByFollowerID(
            @RequestParam(name = "followerId") Long followerId,
            @Valid @PathVariable(value = "notifiable") Followable.FollowableNotifiable notifiable) {

        fstService.setFollowableNotifiableStatusByFollowerID(followerId, notifiable);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/followed/notifiable/{notifiable}", params = "fstId")
    public ResponseEntity<Boolean>  setFollowableNotifiableStatusByFSTID(
            @RequestParam(name = "fstId") Long fstId,
            @Valid @PathVariable(value = "notifiable") Followable.FollowableNotifiable notifiable) {

        fstService.setFollowableNotifiableStatusByFSTID(fstId, notifiable);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // notification methods :
    //
    // /notification/push                           pushNotifications(notifications)    (currentUser -> followed)
    // /notification/push/{followedId}              pushNotifications(followedID, notifications)
    //
    // /notification/push/labels                    pushNotificationLabels(notifications)    (currentUser -> followed)
    // /notification/push/labels/{followedId}       pushNotificationLabels(followedID, notifications)
    //
    // /notification                                getAllNotificationsByFollowerId()   (currentUser -> follower)
    // /notification/{followerId}                   getAllNotificationsByFollowerId(followerId)
    // /notification/{followerId}/{followableId}    getAllNotificationsByFollowerIdAndByFollowedId(followerId, followableId)
    //
    // /notification/labels                         getAllLabelsByFollowerId()          (currentUser -> follower)
    // /notification/labels/{followerId}")          getAllLabelsByFollowerId(followerId)
    //
    // /notification/isEmpty                        isNotificationsEmpty()              (currentUser -> follower)
    // /notification/isEmpty/{followerId}           isNotificationsEmpty(Long followerID)
    //
    // /notification/size                           notificationsSize()                 (currentUser -> follower)
    // /notification/size/{followerId}              notificationsSize(Long followerID)
    //
    // /notification/unread/                        unreadNotificationsCount()          (currentUser -> follower)
    // /notification/unread/{followerId}            unreadNotificationsCount(followerId)
    //
    // /notification/read/                          setNotificationsReadStatus(notifications, isRead)   (currentUser -> follower)
    // /notification/read/{followerId}              setNotificationsReadStatus(followerId, notifications, isRead)
    //
    // /notification/pop                            popNotifications(notifications)     (currentUser -> follower)
    // /notification/pop/{followerId}               popNotifications(followerId, notifications)
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

//    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
//    @PostMapping(value = "/notification/push")
//    void pushNotifications(@Valid @RequestBody Set<Notification> notifications) {
//        fstService.pushNotifications(notifications);
//    }
//
//    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
//    @PostMapping(value = "/notification/push/{followedId}")
//    void pushNotifications(
//            @PathVariable(value = "followedId") Long followedID,
//            @Valid @RequestBody Set<Notification> notifications) {
//        System.out.println("FollowStateTrackerController.pushNotifications(Long: "+followedID + ", Set: "+ notifications + ")");
//        fstService.pushNotifications(followedID, notifications);
//    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/notification/push")
    void pushNotifications(@Valid @RequestBody Set<Post>  posts) {
        fstService.pushNotifications(posts);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/notification/push/{followedId}")
    void pushNotifications(
            @PathVariable(value = "followedId") Long followedID,
            @Valid @RequestBody Set<Post>  posts) {
        logger.debug("pushNotifications(followedID: {}, labels: {})", followedID, posts);
        fstService.pushNotifications(followedID, posts);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/notification/push/labels")
    void pushNotificationLabels(@Valid @RequestBody Set<String> labels) {
        fstService.pushNotificationLabels(labels);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/notification/push/labels/{followedId}")
    void pushNotificationLabels(
            @PathVariable(value = "followedId") Long followedID,
            @Valid @RequestBody Set<String> labels) {
        logger.debug("pushNotifications(followedID: {}, labels: {})", followedID, labels);
        fstService.pushNotificationLabels(followedID, labels);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification")
    public ResponseEntity<Set<Notification>> getAllNotifications()  {
        Set<Notification> notificationList = fstService.getAllNotifications()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune notification trouvée."));
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/{followerId}")
    public ResponseEntity<Set<Notification>> getAllNotificationsByFollowerId(
            @PathVariable(value = "followerId") Long followerId)  {
        Set<Notification> notificationList = fstService.getAllNotificationsByFollowerId(followerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune notification trouvée."));
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    //Optional<List<Notification>> getAllNotificationsByFollowableId(Long followableID) {};

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/{followerId}/{followableId}")
    public ResponseEntity<Set<Notification>> getAllNotificationsByFollowerIdAndByFollowedId(
            @PathVariable(value = "followerId") Long followerId,
            @PathVariable(value = "followableId") Long followableId)  {

        Set<Notification> notificationList = fstService.getAllNotificationsByFollowerIdAndByFollowedId(followerId, followableId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune notification trouvée."));
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/labels")
    public ResponseEntity<Map<Long, String>> getAllLabels() {
        Map<Long, String> labelMap = fstService.getAllLabels()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun label trouvé."));
        return new ResponseEntity<>(labelMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/labels/{followerId}")
    public ResponseEntity<Map<Long, String>> getAllLabelsByFollowerId(
            @PathVariable(value = "followerId") Long followerId) {
        Map<Long, String> labelMap = fstService.getAllLabelsByFollowerId(followerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun label trouvé."));
        return new ResponseEntity<>(labelMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/isEmpty")
    public ResponseEntity<Boolean> isNotificationsEmpty() {
        return new ResponseEntity<>(fstService.isNotificationsEmpty(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/isEmpty/{followerId}")
    public ResponseEntity<Boolean> isNotificationsEmpty(@PathVariable(value = "followerId") Long followerID) {
        return new ResponseEntity<>(fstService.isNotificationsEmpty(followerID), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/size")
    public ResponseEntity<Long> notificationsSize() {
        return new ResponseEntity<>(fstService.notificationsSize(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/size/{followerId}")
    public ResponseEntity<Long> notificationsSize(@PathVariable(value = "followerId") Long followerID) {
        return new ResponseEntity<>(fstService.notificationsSize(followerID), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/unread")
    public ResponseEntity<Long> unreadNotificationsCount() {
        return new ResponseEntity<>(fstService.unreadNotificationsCount(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @GetMapping(value = "/notification/unread/{followerId}")
    public ResponseEntity<Long> unreadNotificationsCount(@PathVariable(value = "followerId") Long followerID) {
        return new ResponseEntity<>(fstService.unreadNotificationsCount(followerID), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/notification/read/id/{id}")
    public void setNotificationReadStatus(
            @PathVariable(value = "id") Long notificationId,
            @RequestParam(name = "read", defaultValue = "true") Boolean isRead)  {
        fstService.setNotificationReadStatus(notificationId, isRead);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/notification/read/{followerId}/id/{id}")
    public void setNotificationReadStatus(
            @PathVariable(value = "followerId") Long followerId,
            @PathVariable(value = "id") Long notificationId,
            @RequestParam(name = "read", defaultValue = "true") Boolean isRead)  {
        fstService.setNotificationReadStatus(followerId, notificationId, isRead);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/notification/read")
    public void setNotificationsReadStatus(
            @Valid @RequestBody Set<Notification> notifications,
            @RequestParam(name = "read", defaultValue = "true") Boolean isRead)  {
        fstService.setNotificationsReadStatus(notifications, isRead);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @PostMapping(value = "/notification/read/{followerId}")
    public void setNotificationsReadStatus(
            @PathVariable(value = "followerId") Long followerId,
            @Valid @RequestBody Set<Notification> notifications,
            @RequestParam(name = "read", defaultValue = "true") Boolean isRead)  {
        fstService.setNotificationsReadStatus(followerId, notifications, isRead);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @DeleteMapping(value = "/notification/pop/id/{id}")
    public void popNotification(
            @PathVariable(value = "id") Long notificationId) {
        fstService.popNotification(notificationId);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @DeleteMapping(value = "/notification/pop/{followerId}/id/{id}")
    public void popNotification(
            @PathVariable(value = "followerId") Long followerId,
            @PathVariable(value = "id") Long notificationId) {
        logger.debug("popNotification(followerId: {}, notificationId: {})", followerId, notificationId);
        fstService.popNotification(followerId, notificationId);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @DeleteMapping(value = "/notification/pop")
    public void popNotifications(
            @Valid @RequestBody Set<Notification> notifications) {
        fstService.popNotifications(notifications);
    }

    @PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
    @DeleteMapping(value = "/notification/pop/{followerId}")
    public void popNotifications(
            @PathVariable(value = "followerId") Long followerId,
            @Valid @RequestBody Set<Notification> notifications) {
        fstService.popNotifications(followerId, notifications);
    }
}
