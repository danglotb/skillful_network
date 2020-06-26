import { Publication } from 'src/app/shared/models/application/publication';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-list-publications',
  templateUrl: './list-publications.component.html',
  styleUrls: ['./list-publications.component.scss']
})

export class ListPublicationsComponent implements OnInit {
  listPublication : Publication[];

  constructor() { }

  ngOnInit(): void {
  }

}
