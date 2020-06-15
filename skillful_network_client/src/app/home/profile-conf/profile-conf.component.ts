import { Component } from '@angular/core';
import { UserService } from "../../shared/services/user.service"
import { User } from 'src/app/shared/models/user/user';

@Component({
  selector: 'app-profile-conf',
  templateUrl: './profile-conf.component.html',
  styleUrls: ['./profile-conf.component.scss']
})
export class ProfileConfComponent {

  public userLogged: User;

  constructor(
    private userService: UserService,
  ) { }

  ngOnInit() {
    this.userLogged = this.userService.getCurrentUser();
  }

}
