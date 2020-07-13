export class Notification {

    public id: number;
    public followerSet: string[];
    public label: string;
    public isRead: boolean;

    constructor(data: any) {
        this.id = data.id;
        this.followerSet = data.followerSet;
        this.label = data.label;
        this.isRead = data.isRead;
    }

    public get getId() {
        return this.id;
    }

    public get getFollowerSet() {
        return this.followerSet;
    }

    public set setFollowerSet(followerSet: string[]) {
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
}
