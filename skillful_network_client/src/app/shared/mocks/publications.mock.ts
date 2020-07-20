import { User } from 'src/app/shared/models/user/user';
import { PublicationComment } from 'src/app/shared/models/publication-comment';
import {  IComment } from './comments.mock';
export interface IPublication {
    id: number;
    user: string;
    text: string;
    file: string;
    numberOfComment: number;
    comments: IComment[],
    votes: number;
    dateOfPost: Date;
}

