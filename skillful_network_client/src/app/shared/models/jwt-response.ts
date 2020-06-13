import { User } from './user/user';

export class JwtResponse {
    user: User;
    authorities: string[];
    token: string;
}