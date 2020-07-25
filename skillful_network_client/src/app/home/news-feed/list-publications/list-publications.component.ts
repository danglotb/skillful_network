import { Publication } from 'src/app/shared/models/application/publication';
import { Component, OnInit } from '@angular/core';
import { PublicationService } from 'src/app/shared/services/publication.service';


@Component({
  selector: 'app-list-publications',
  templateUrl: './list-publications.component.html',
  styleUrls: ['./list-publications.component.scss']
})

export class ListPublicationsComponent implements OnInit {
  listPublication : Publication[];

  constructor(public pub: PublicationService) { 

  }

  ngOnInit(): void {
    let that = this;
    this.pub.getPublications().then(function(response){
      console.log(response);
      
      that.listPublication = response;
      that.listPublication.sort((a, b) => {
        return <any>new Date(b.dateOfPost) - <any>new Date(a.dateOfPost);
      });
    })
    this.pub.publicationAdded.subscribe(function(publication){
      that.listPublication.push(publication);
      that.listPublication.sort((a, b) => {
        return <any>new Date(b.dateOfPost) - <any>new Date(a.dateOfPost);
      });
    })
  }
  async onPublicationDeleted(){
    this.listPublication = await this.pub.getPublications();
  }
}
