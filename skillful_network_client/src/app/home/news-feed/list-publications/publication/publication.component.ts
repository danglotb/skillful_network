import { User } from './../../../../shared/models/user/user';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.scss']
})
export class PublicationComponent implements OnInit {
  @Input() public user: User;
  @Input() public texte: string;
  @Input() public votes: number;
  @Input() public fichier: string;
  @Input() public dateOfPost: Date;

  constructor() { }

  ngOnInit(): void {
  }

}
