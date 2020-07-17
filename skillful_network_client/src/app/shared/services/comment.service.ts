import { IComment } from './../mocks/comments.mock';
import { Publication } from './../models/application/publication';
import { Injectable } from '@angular/core';
import { User } from './../models/user/user';
import { PublicationComment } from '../models/publication-comment';


@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor() { }

  public addComment(publication, data : any){
    let comment = new PublicationComment(data);
    if(publication.comments == undefined){
      publication.comments = [];
    }
    publication.comments.unshift(comment);
  }

  public onUpVote(comment,value: number) {
    comment.votes += value;
  }
  public onDownVote(comment,value: number) {
    comment.votes -= value;
  }
}
