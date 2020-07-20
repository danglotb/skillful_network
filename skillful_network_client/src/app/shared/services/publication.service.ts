import { ApiHelperService } from './api-helper.service';
import { User } from './../models/user/user';
import { Injectable, EventEmitter } from '@angular/core';
import { Publication } from '../models/application/publication';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {
listPublication : Publication[];
publicationAdded = new EventEmitter();

  constructor( private api: ApiHelperService) { 
    
  }
  public onUpVote(index: number, value: number) {
    this.listPublication[index].votes += value;
  }
  public onDownVote(index: number, value: number) {
    this.listPublication[index].votes -= value;
  }
  public getPublications(): Promise<any> {
    return this.api.get({endpoint: '/posts'});
  }
  public deletePublication(id) {
    return this.api.delete({endpoint: '/posts/'+ id});
  }
  public addpublication(postBodyText: String) {

    let endPoint = "/posts";
    let that = this;
    this.api.post({endpoint : endPoint, data : postBodyText}).then(function(response){
      that.publicationAdded.emit(response);
    }) 
  }
}
