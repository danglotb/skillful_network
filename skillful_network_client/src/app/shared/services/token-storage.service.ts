import { Injectable } from '@angular/core';
import { deprecate } from 'util';
// Ce service permettra de manipuler les données relatives au token/utilisateur de la session

const TOKEN_KEY = 'token';
const USERNAME_KEY = 'username';
const LOCAL_STORAGE = 'local';
const AUTHORITIES_KEY = 'authorities';
const IS_LOGGED_IN = 'isLoggedIn';
const IS_LOGGED = 'true';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
 
  constructor() { }

  signOut() {
    localStorage.clear();
    sessionStorage.clear();
  }

  public saveTokenAndCurrentUsername(token: string, authorities: string[], storage: string) {
    localStorage.removeItem(TOKEN_KEY);
    sessionStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USERNAME_KEY );
    sessionStorage.removeItem(USERNAME_KEY );
    localStorage.removeItem(AUTHORITIES_KEY);
    sessionStorage.removeItem(AUTHORITIES_KEY);
    localStorage.removeItem(IS_LOGGED_IN);
    sessionStorage.removeItem(IS_LOGGED_IN);
    if (storage === LOCAL_STORAGE) {
      localStorage.setItem(TOKEN_KEY, token);
      localStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
      localStorage.setItem(IS_LOGGED_IN, IS_LOGGED);
    } else {
      sessionStorage.setItem(TOKEN_KEY, token);
      sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
      sessionStorage.setItem(IS_LOGGED_IN, IS_LOGGED);
    }
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY) || sessionStorage.getItem(TOKEN_KEY);
  }

  public getAuthorities(): string[] {
    let roles: string [];
    let authorities: any;
    roles = [];
    authorities = sessionStorage.getItem(AUTHORITIES_KEY) || localStorage.getItem(AUTHORITIES_KEY);
    JSON.parse(authorities).forEach(authority => {
        roles.push(authority.authority);
      });
    return roles;
  }

  public isLogged(): boolean {
    return (Boolean)(localStorage.getItem(IS_LOGGED_IN)  || sessionStorage.getItem(IS_LOGGED_IN)) ;
  }

}
