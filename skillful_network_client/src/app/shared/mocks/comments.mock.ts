import { User } from '../models/user/user';

export interface IComment {
    id: number;
    user: User;
    commentBodyText: string;
    votes: number;
    dateOfComment: Date;
}

