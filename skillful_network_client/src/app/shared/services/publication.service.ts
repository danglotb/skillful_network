import { ApiHelperService } from './api-helper.service';
import { User } from './../models/user/user';
import { MOCK_PUBLICATIONS } from '../mocks/publications.mock';
import { Injectable } from '@angular/core';
import { Publication } from '../models/application/publication';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {
listPublication : Publication[];

  constructor( private api: ApiHelperService) { 
      // this.listPublication = [];
      // MOCK_PUBLICATIONS.forEach(publication => {
      //   this.listPublication.push(new Publication(publication));
      // });
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
    return this.api.delete({endpoint: '/posts'});
  }
  public upNumberComment(index: number, value: number){
    this.listPublication[index].numberOfComment  += value;
  }
  public addpublication(text: String) {

    let endPoint = "/posts";
    return this.api.post({endpoint : endPoint, data : text});
    // this.listPublication.unshift(publication);
    
  }
}
