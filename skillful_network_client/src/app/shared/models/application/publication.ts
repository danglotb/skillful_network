import { User } from '../user/user';

export class Publication {

    private _id: number;
    private _user: User;
    private _postBodyText: String;
    private _file: String;
    private _votes: number;
    private _numberOfComment: number;
    private _comments: String [];
    private _dateOfPost: Date;


    constructor(data: any) {
        this.id = data.id;
        this._postBodyText = data.postBodyText;
        this.user = data.user;
        this.file = data.file;
        this._votes = data.votes;
        this._numberOfComment = data.numberOfComment;
        this._comments = data.comments;
        this.dateOfPost = data.dateOfPost;
      
    }

    public get user(): User {
        return this._user;
    }

    public set user(user: User) {
        this._user = user;
    }

    public get id(): number {
        return this._id;
    }

    public set id(value: number) {
        this._id = value;
    }

    public get votes(): number {
        return this._votes;
    }

    public set votes(value: number) {
        this._votes = value;
    }
    public get postBodyText(): String {
        return this.postBodyText;
    }
    
    public set postBodyText(value: String) {
        this.postBodyText = value;
    }
    public get file(): String {
        return this._file;
    }
    
    public set file(value: String) {
        this._file = value;
    }

    public get numberOfComment (): number {
        return this._numberOfComment;
    }

    public set numberOfComment(value: number) {
        this._numberOfComment = value;
    }
    public get comments(): String[] {
        return this._comments;
    }
    
    public set comments(value: String []) {
        this._comments = value;
    }

    public get dateOfPost(): Date {
        return this._dateOfPost;
    }
    
    public set dateOfPost(value: Date) {
        this._dateOfPost = value;
    }
}
