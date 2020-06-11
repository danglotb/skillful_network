import { Component, OnInit } from '@angular/core';
import { ListElement } from 'src/app/shared/components/list/list-element';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})

export class UsersListComponent implements OnInit {

  displayedColumns: string[] = ['lastName', 'firstName', 'birthdate'];
  listElements: ListElement[] = [
    new ListElement("Nom", "lastName"),
    new ListElement("Prénom", "firstName"),
    new ListElement("Date de naissance", "birthdate")
  ]

  constructor(public service: UserService) {

  }

  ngOnInit() {

  }
}