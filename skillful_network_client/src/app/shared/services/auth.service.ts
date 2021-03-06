import { Injectable } from '@angular/core';
import { ApiHelperService } from '../services/api-helper.service';
import { JwtResponse } from '../models/jwt-response';
import { User } from '../models/user/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _user: User = new User({ id: -1 });

  constructor(private api: ApiHelperService) { }

  public whoami(token: string) : Promise<User> {
    return this.api.post({ endpoint: "/whoami", data: token });
  }

  public login(email: string, password: string): Promise<JwtResponse> {
    return this.api.post({ endpoint: '/login', data: { email: email, password: password } });
  }

  public register(email: string, roles: string[]): Promise<User> {
    return this.api.post({ endpoint: '/register', data: { email: email, role: roles } });
  }

  public async getCurrentUser(): Promise<User> {
    if (this._user.id == -1) {
      await this.api.get({ endpoint: '/user' }).then(data => {
        this._user = data;
      });
    }
    return this.user;
  }

  public set user(user: User) { 
    this._user = user;
  }

  public get user() : User {
    return this._user;
  }

}
