import { Component, ViewChild } from '@angular/core';
import { UserService } from "../../shared/services/user.service"
import { User } from 'src/app/shared/models/user/user';
import { UserPageComponent } from 'src/app/shared/components/user/user-page.component';

@Component({
  selector: 'app-profile-conf',
  templateUrl: './profile-conf.component.html',
  styleUrls: ['./profile-conf.component.scss']
})
export class ProfileConfComponent {

  @ViewChild(UserPageComponent) userPage: UserPageComponent;

  constructor(
    private userService: UserService,
  ) { }

  ngAfterViewInit() {
    this.userPage.init(this.userService.getCurrentUser());
  }

}
