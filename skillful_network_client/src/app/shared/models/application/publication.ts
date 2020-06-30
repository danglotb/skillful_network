import { User } from '../user/user';

export class Publication {

    private _id: number;
    private _user: User;
    private _texte: String;
    private _fichier: String;
    private _votes: number;
    private _dateOfPost: Date;

 


    constructor(data: any) {
        this.id = data.id;
        this.texte = data.texte;
        this.user = data.user;
        this.fichier = data.fichier;
        this._votes = data.votes;
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
    public get texte(): String {
        return this._texte;
    }
    
    public set texte(value: String) {
        this._texte = value;
    }
    public get fichier(): String {
        return this._fichier;
    }
    
    public set fichier(value: String) {
        this._fichier = value;
    }

    public get dateOfPost(): Date {
        return this._dateOfPost;
    }
    
    public set dateOfPost(value: Date) {
        this._dateOfPost = value;
    }
}
