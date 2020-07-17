import { User } from './user';

export class FollowStateTracker{
    public id: number;
    public creationDate: Date;
    public follower: User;
    public followed: User;
    public notifications: Notification[];
    public followerStatus:string[];
    public followerNotifiable:string[];
    public followableStatus: string[];
    public followableNotifiable: string[];


    constructor(
        data: any
    ) {
        this.id = data.id;
        this.creationDate = data.creationDate;
        this.followed = data.followed;
        this.notifications = data.notifications;
        this.followableStatus = data.followableStatus;
        this.followableNotifiable = data.followableNotifiable;
        this.followerStatus = data.followerStatus;
        this.followerNotifiable = data.followerNotifiable;
    }
    public getId(): number {
        return this.id;
    }

    public getCreationDate(): Date {
        return this.creationDate;
    }

    public getFollowed(): User {
        return this.followed;
    }

    public setFollowed(followed: User): void {
        this.followed = followed;
    }

    public getNotifications(): Notification[] {
        return this.notifications;
    }

    public setNotifications(notifications: Notification[]): void {
        this.notifications = notifications;
    }

    public getFollowableStatus(): string[] {
        return this.followableStatus;
    }

    public setFollowableStatus(followableStatus: string[]): void {
        this.followableStatus = followableStatus;
    }

    public getFollowableNotifiable(): string[] {
        return this.followableNotifiable;
    }

    public setFollowableNotifiable(followableNotifiable: string[]): void {
        this.followableNotifiable = followableNotifiable;
    }

    public getFollowerStatus(): string[] {
        return this.followerStatus;
    }

    public setFollowerStatus(followerStatus: string[]): void {
        this.followerStatus = followerStatus;
    }

    public getFollowerNotifiable(): string[] {
        return this.followerNotifiable;
    }

    public setFollowerNotifiable(followerNotifiable: string[]): void {
        this.followerNotifiable = followerNotifiable;
    }

}
