import { Injectable } from '@angular/core';
import { ApiHelperService } from './api-helper.service';

const ROOT_ENDPOINT: string = '/comments/';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor( private api: ApiHelperService) { }

  public addComment(commentBodyText: String, idPost: number){
    return this.api.post({
      endpoint: ROOT_ENDPOINT, 
      data : { body: commentBodyText, id: idPost } 
    });
  }

  public onUpVote(comment,value: number) {
    comment.votes += value;
  }
  
  public onDownVote(comment,value: number) {
    comment.votes -= value;
  }
  
  public deleteComment(id) {
    return this.api.delete({endpoint: ROOT_ENDPOINT + id});
  }
}
