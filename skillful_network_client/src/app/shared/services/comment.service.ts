import { IComment } from './../mocks/comments.mock';
import { Publication } from './../models/application/publication';
import { Injectable } from '@angular/core';
import { User } from './../models/user/user';
import { PublicationComment } from '../models/publication-comment';
import { ApiHelperService } from './api-helper.service';


@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor( private api: ApiHelperService) { }

  public addComment(commentBodyText: String){
    return this.api.post({endpoint: '/comments', data : commentBodyText});
  }

  public onUpVote(comment,value: number) {
    comment.votes += value;
  }
  public onDownVote(comment,value: number) {
    comment.votes -= value;
  }
}
