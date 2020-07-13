import { User } from './../models/user/user';
import { MOCK_PUBLICATIONS } from '../mocks/publications.mock';
import { Injectable } from '@angular/core';
import { Publication } from '../models/application/publication';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {
listPublication : Publication[];

  constructor() { 
      this.listPublication = [];
      MOCK_PUBLICATIONS.forEach(publication => {
        this.listPublication.push(new Publication(publication));
      });
  }
  public onUpVote(index: number, value: number) {
    this.listPublication[index].votes += value;
  }
  public onDownVote(index: number, value: number) {
    this.listPublication[index].votes -= value;
  }
  public getPublications(): Publication[] {
    return this.listPublication;
  }
  public onDelete(index: number) {
    this.listPublication.splice(index, 1);
  }
  public addpublication(text: String, file:String,  votes: number, user: User, dateOfPost: Date) {
    let data = {
      text : text,
      file : file,
      user: user,
      votes: votes,
      dateOfPost : dateOfPost
    }
     let publication = new Publication(data);
    this.listPublication.unshift(publication);
  }
}
