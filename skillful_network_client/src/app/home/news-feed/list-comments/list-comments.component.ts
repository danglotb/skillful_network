import { PublicationService } from 'src/app/shared/services/publication.service';
import { Component, OnInit, Input } from '@angular/core';
import { IComment } from './../../../shared/mocks/comments.mock';


@Component({
  selector: 'app-list-comments',
  templateUrl: './list-comments.component.html',
  styleUrls: ['./list-comments.component.scss']
})
export class ListCommentsComponent implements OnInit {
  @Input() comments: IComment[];
  
  constructor(private publicationService : PublicationService) {
  }

  ngOnInit(): void {
  }

}
