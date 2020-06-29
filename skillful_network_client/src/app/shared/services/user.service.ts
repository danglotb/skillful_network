
import { Injectable } from '@angular/core';

import { User } from '../models/user/user';
import { ApiHelperService } from './api-helper.service';
import { SearchService } from './abstract-search.service'
import { AuthService } from './auth.service';

const ROOT_ENDPOINT: string = '/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService extends SearchService<User> {

  constructor(
    protected api: ApiHelperService,
    private authService: AuthService) {
    super(api);
  }

  public update(user: User): Promise<User> {
    return this.api.put({ endpoint: ROOT_ENDPOINT, data: user });
  }

  public getBySearch(keyword: string, page: number, size: number, sortOrder: string, field: string): Promise<User> {
    return super._getBySearch("users", keyword, page, size, sortOrder, field);
  }

  public getCurrentProfilePicture(): Promise<any> {
    return this.api.get({ endpoint: ROOT_ENDPOINT + 'profilePicture' });
  }

  public getProfilePictureById(id: number) : Promise<any> {
    return this.api.get({ endpoint: ROOT_ENDPOINT + 'profilePicture/' + id });
  }

  public getImage(id: number) : Promise<any> {
    return this.getProfilePictureById(id);
  }

  public getCurrentUser(): Promise<User> {
    return this.authService.getCurrentUser();
  }

  public getById(id: number): Promise<User> {
    return this.api.get({ endpoint: ROOT_ENDPOINT + id })
  }

  // DEPRECATED
  public updateUserPassword(passwordToUpdate: string): Promise<any> {
    return this.api.put({ endpoint: '/users/password', data: { password: passwordToUpdate } });
  }

  public updateConfirmationRegister(firstName: string, lastName: string, roleSet: string [] ): Promise<any> {
    return this.api.put({ endpoint: ROOT_ENDPOINT+'confirmation', data: { firstName: firstName, lastName : lastName, roleSet : roleSet } });
  }

}
