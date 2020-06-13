import { Injectable } from '@angular/core';
import { ApiHelperService } from '../services/api-helper.service';
import { JwtResponse } from '../models/jwt-response';
import { User } from '../models/user/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private api: ApiHelperService) { }

  private _userLogged: User;

  public login(email: string, password: string): Promise<JwtResponse> {
    return this.api.post({ endpoint: '/login', data: { email: email, password: password } });
  }

  public register(email: string, roles: string[]): Promise<User> {
    return this.api.post({ endpoint: '/register', data: { email: email, role: roles } });
  }

  public getCurrentUser(): Promise<User> {
    return this.api.get({ endpoint: '/user' });
  }

  public initUserLogged(): void {
    this.getCurrentUser().then( data => {
      this._userLogged = data;
    }).catch(error => console.log(console.error()));
  }

  public get userLogged() : User {
    if (this._userLogged == null) {
      this.initUserLogged();
    }
    return this._userLogged;
  }

  public initUserLoggedWithObject(data: any): void {
    this._userLogged = new User(data);
  }

}
