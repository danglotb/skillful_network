import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../../../../shared/models/user/user';
import { UserService } from '../../../../shared/services/user.service';
import { UserPageComponent } from 'src/app/shared/components/user/user-page.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  @ViewChild(UserPageComponent) userPage: UserPageComponent;

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
  }
  
  ngAfterViewInit() {
    let splittedUrl = this.router.url.split('/');
    this.userPage.init(Promise.resolve(this.userService.getById(+ splittedUrl[splittedUrl.length - 1])));
  }

}