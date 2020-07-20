import { FollowStateTracker } from '../user/FollowStateTracker';

export class Notification {

    public id: number;
    public followerSet: Set<FollowStateTracker> = new Set();
    public label: string;
    public isRead: boolean;
    public postId: number;

    constructor(data: any) {
        this.id = data.id;
        this.followerSet = data.followerSet;
        this.label = data.label;
        this.isRead = data.read;
        this.postId = data.postId;
    }

    public get getId() {
        return this.id;
    }

    public get getFollowerSet() {
        return this.followerSet;
    }

    public set setFollowerSet(followerSet: Set<FollowStateTracker>) {
        this.followerSet = followerSet;
    }

    public get getLabel() {
        return this.label;
    }

    public set setLabel(label: string) {
        this.label = label;
    }

    public get getIsRead() {
        return this.isRead;
    }

    public set setIsRead(isRead: boolean) {
        this.isRead = isRead;
    }

    public get getPostId() {
        return this.postId;
    }

    public set setPostId(postId: number) {
        this.postId = postId;
    }
}
