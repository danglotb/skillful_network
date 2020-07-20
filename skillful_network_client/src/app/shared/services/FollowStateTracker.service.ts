import { Injectable } from '@angular/core';
import { ApiHelperService } from './api-helper.service';
import { User } from '../models/user/user';
import { FollowStateTracker } from '../models/user/FollowStateTracker';

const ROOT_ENDPOINT: string = '/fst/';
const ROOT_ENDPOINT_FOLLOW: string = ROOT_ENDPOINT + 'follow/';
const ROOT_ENDPOINT_UNFOLLOW: string = ROOT_ENDPOINT + 'unfollow/';
const ROOT_ENDPOINT_FOLLOWED: string = ROOT_ENDPOINT + 'followed/';
const ROOT_ENDPOINT_FOLLOWER: string = ROOT_ENDPOINT + 'follower/';
const ROOT_ENDPOINT_FOLLOWERS: string = ROOT_ENDPOINT + 'followers/';
const ROOT_ENDPOINT_NOTIFICATION: string = ROOT_ENDPOINT + 'notification/';

@Injectable({
  providedIn: 'root'
})
export class FollowStateTrackerService {

  constructor(private api: ApiHelperService) { }

  /*==================== FOLLOWER method ==============================================================*/

  // /all/follower                                    getAllFSTByFollower()                   (currentUser -> follower)
  public getAllFSTByFollower(): Promise<FollowStateTracker[]> {
    return this.api.get({ endpoint: ROOT_ENDPOINT + 'all/follower' })
  }
  // /followers                                       getAllFollowersByFollowable()           (currentUser -> followable)
  public getAllFollowersByFollowable(): Promise<User[]> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_FOLLOWERS })
  }

  // /follow/{followableId}                           *follow(followableId)                    (currentUser -> follower)
  public followByFollowableId(followableId: number): Promise<Boolean> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_FOLLOW + followableId });
  }
  // /unfollow/{followedId}                          * unfollowByFollowedID(followedId)        (currentUser -> follower)
  public unfollowByFollowedId(followerId: number): Promise<Boolean> {
    return this.api.delete({ endpoint: ROOT_ENDPOINT_UNFOLLOW + followerId });
  }
  // /follower/status/{status}                        setFollowerStatus(status)               (currentUser -> follower)
  public setFollowerStatus(status: string): Promise<Boolean> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_FOLLOWER + 'status/' + status });
  }
  // /follower/status/{status}?followedId=            setFollowerStatusByFollowedID(followedId, status) (currentUser -> follower)
  public setFollowerStatusByFollowedID(followedId: number, status: string): Promise<Boolean> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_FOLLOWER + 'status/' + status, queryParams: followedId });
  }
  // /follower/notifiable/{notifiable}                setFollowerNotifiableStatus(notifiable) (currentUser -> follower)
  public setFollowerNotifiableStatus(notifiable: string): Promise<Boolean> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_FOLLOWER + 'notifiable/' + notifiable });
  }
  // /follower/notifiable/{notifiable}?followerId=    setFollowerNotifiableStatusByFollowedID (followedId, notifiable) (currentUser -> follower)
  public setFollowerNotifiableStatusByFollowedID(followedId: number, notifiable: string): Promise<Boolean> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_FOLLOWER + 'notifiable/' + notifiable, queryParams: followedId });
  }
  // /followers/count                                 getFollowerCount()                      (currentUser -> followable)
  public getFollowersCount(): Promise<number> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_FOLLOWERS + 'count' });
  }
  ///followers/count/{followableId}
  public getFollowersCountByFollowableId(FollowableId: number): Promise<number> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_FOLLOWERS + 'count/' + FollowableId });
  }
  /*==================== FOLLOWABLE method ==============================================================*/

  // /all/followed                                    getAllFSTByFollowable()                     (currentUser -> followable)
  public getAllFSTByFollowable(): Promise<FollowStateTracker[]> {
    return this.api.get({ endpoint: ROOT_ENDPOINT + 'all/' + 'followed' });
  }
  // /followed                                        getAllFollowedByFollower()                  (currentUser -> follower)
  public getAllFollowedByFollower(): Promise<User[]> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_FOLLOWED });
  }
  // /ban/{followerId}                                banFollow(followerId)                          (currentUser -> followed)
  public banFollow(followerId: number): Promise<boolean> {
    return this.api.get({ endpoint: ROOT_ENDPOINT + "ban/" + followerId });
  }
  // /followed/status/{status}                        setFollowableStatus(status)                         (currentUser -> followed)
  public setFollowableStatus(status: string) {
    return this.api.post({ endpoint: ROOT_ENDPOINT_FOLLOWED + 'status/' + status });
  }
  // /followed/status/{status}?followerId=            setFollowableStatusByFollowerID(followerId, status) (currentUser -> followed)
  public setFollowableStatusByFollowerID(followerId: number, status: string): Promise<boolean> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_FOLLOWED + 'status/' + status, queryParams: followerId });
  }
  // /followed/notifiable/{notifiable}                setFollowableNotifiableStatus(notifiable)           (currentUser -> followed)
  public setFollowableNotifiableStatus(notifiable: string): Promise<boolean> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_FOLLOWED + 'notifiable/' + notifiable });
  }

  // /followed/count                                  getFollowedCount()                      (currentUser -> follower)
  public getFollowedCount(): Promise<number> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_FOLLOWED + 'count' });
  }
  // /followed/count/{followerId}   
  public getFollowedCountByFollowerId(FollowerId: number): Promise<number> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_FOLLOWED + 'count/' + FollowerId });
  }
  /*==================== NOTIFICATIONS method==============================================================*/

  //push
  public pushNotification(notifications: string[]): Promise<any> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_NOTIFICATION + 'push' });
  }
  // /notification                                getAllNotificationsByFollowerId()   (currentUser -> follower)
  public getAllNotificationsByFollowerId(followerId: number): Promise<Notification[]> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_NOTIFICATION + followerId });
  }
  // /notification/labels                         getAllLabelsByFollowerId()          (currentUser -> follower)
  public getAllLabelsByFollowerId(followerId: number): Promise<Map<number, string>> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_NOTIFICATION + 'labels/' + followerId });
  }
  // /notification/unread/                        unreadNotificationsCount()          (currentUser -> follower)
  public unreadNotificationsCount(): Promise<number> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_NOTIFICATION + 'unread' });
  }

  // /notification/unreadlist/                        unreadNotificationsCount()          (currentUser -> follower)
  public unreadNotifications(): Promise<Set<Notification>> {
    return this.api.get({ endpoint: ROOT_ENDPOINT_NOTIFICATION + 'unreadlist' });
  }
  // //notification/read/{followerId}/id/{id}                         setNotificationsReadStatus(notifications, isRead)   (currentUser -> follower)
  public setNotificationsReadStatus(followerId: number, notificationId: number, isRead: boolean): Promise<void> {
    return this.api.post({ endpoint: ROOT_ENDPOINT_NOTIFICATION + 'read/' + followerId + '/id/' + notificationId });
  }
  // /notification/pop                            popNotifications(notifications)     (currentUser -> follower)
  public popNotifications(notifications: Set<Notification>): Promise<any> {
    return this.api.delete({ endpoint: ROOT_ENDPOINT_NOTIFICATION + 'pop' });
  }



}
