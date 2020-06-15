import {Component, OnInit} from '@angular/core';
import {User} from '../../../../shared/models/user/user';
import {UserService} from '../../../../shared/services/user.service';

@Component({
    selector: 'app-user',
    templateUrl: './user.component.html',
    styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

    public userLogged: User;

    constructor(
      private userService: UserService,
    ) { }
  
    ngOnInit() {
      this.userLogged = this.userService.getCurrentUser();
    }
  
}