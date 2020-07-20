import { Injectable } from '@angular/core';
import { ApiHelperService } from './api-helper.service';
import { User } from '../models/user/user';

const ROOT_ENDPOINT = '/posts';

@Injectable({
  providedIn: 'root'
})
export class PostService {


  constructor(private api: ApiHelperService) { }

  public getUserByPostId(postId: number): Promise<User> {
    return this.api.get({endpoint : ROOT_ENDPOINT + '/' + postId + '/user'});
  }
}
