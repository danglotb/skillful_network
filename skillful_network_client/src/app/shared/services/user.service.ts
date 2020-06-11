
import { Injectable } from '@angular/core';
import { User } from '../models/user/user';
import { ApiHelperService } from './api-helper.service';
import { SearchService } from './abstract-search.service'

import { Subject, from } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService extends SearchService<User> {

  constructor(protected api: ApiHelperService) {
    super(api);
  }

  public userLogged;
  userLoggedSubject = new Subject<User>();

  emitUsers() {
    this.userLoggedSubject.next(this.userLogged);
  }

  updateUser(user: User, sendToBack: boolean = true) {
    this.userLogged.firstName = user.firstName;
    this.userLogged.lastName = user.lastName;
    this.userLogged.birthDate = user.birthDate;
    this.userLogged.email = user.email;
    this.userLogged.mobileNumber = user.mobileNumber;
    this.userLogged.careerGoal = user.careerGoal;
    this.userLogged.skillSet = user.skillSet;
    this.userLogged.qualificationSet = user.qualificationSet;
    this.userLogged.subscriptionSet = user.subscriptionSet;

    // envoie vers le back
    if (sendToBack) {
      this.api.put({ endpoint: '/users', data: this.userLogged });
      this.emitUsers();
    }
  }

  public findAll(page: number, size: number, sortOrder: string, field: string): Promise<any> {
    const promise = new Promise((resolve, reject) => {
      this.api.get({ endpoint: `/users/`, queryParams: { page, size, sortOrder, field } })
        .then(
          res => {
            resolve(res);
          },
          msg => {
            reject(msg);
          }
        ).catch((error) => {
        });
    });
    return promise;
  }

  public findByContain(option: string, contain: string): Promise<any> {
    return this.api.get({ endpoint: `/${option}/candidates`, queryParams: { contain } });
  }

  public getBySearch(keyword: string, page: number, size: number, sortOrder: string, field: string): Promise<User> {
    return super._getBySearch("users", keyword, page, size, sortOrder, field);
  }

  public disconnect() {

  }

  public findAllByPage(page: number, size: number, sortOrder: String, fieldToSort: String): Promise<any> {
    let promise = new Promise((resolve, reject) => {
      this.api.get({
        endpoint: '/users',
        queryParams: { "page": page, "size": size, "sortOrder": sortOrder, "field": fieldToSort }
      })
        .then(
          res => {
            resolve(res);
          },
          msg => {
            reject(msg);
          }
        ).catch((error) => {
        });
    });
    return promise;
  }

  public updateUserPassword(passwordToUpdate: string): Promise<any> {
    return this.api.put({ endpoint: '/users/password', data: { password: passwordToUpdate } });
  }


  // NEW API

  public getCurrentProfilePicture(): Promise<any> {
    return this.api.get({ endpoint: '/users/profilePicture' });
  }

  public initUserLogged(): void {
    this.api.get({ endpoint: '/user' }).then(data => {
      this.userLogged = data;
    });
  }

  public initUserLoggedWithObject(data: any): void {
    this.userLogged = new User(data);
  }

}
