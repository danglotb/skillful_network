import { User } from '../user/user';

export class Application {

    private _id: number;
    private _user: User;
    private _submitDate: Date;
    private _status: String;

    constructor(data: any) {
        this.id = data.id;
        this.status = data.status;
        this.user = data.user;
        this.submitDate = data.submitDate;
    }

    public get submitDate(): Date {
        return this._submitDate;
    }

    public set submitDate(submitDate: Date) {
        this._submitDate = submitDate;
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

    public get status(): String {
        return this._status;
    }
    
    public set status(value: String) {
        this._status = value;
    }

}
