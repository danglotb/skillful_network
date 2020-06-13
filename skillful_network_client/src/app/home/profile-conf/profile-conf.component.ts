import { Component, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { UserService } from "../../shared/services/user.service"
import { User } from 'src/app/shared/models/user/user';
import { ChipComponent } from 'src/app/shared/components/chip/chip.component';
import { UserConfComponent } from './user-conf/user-conf.component';
import { Perk } from 'src/app/shared/models/user/perk';
import { ITS_JUST_ANGULAR } from '@angular/core/src/r3_symbols';

@Component({
  selector: 'app-profile-conf',
  templateUrl: './profile-conf.component.html',
  styleUrls: ['./profile-conf.component.scss']
})
export class ProfileConfComponent {

  public userLogged: User;
  private lastUserLogged: User;

  @ViewChild('userConfiguration') userConfiguration: UserConfComponent;
  @ViewChild('chipQualifications') chipQualifications: ChipComponent;
  @ViewChild('chipSkills') chipSkills: ChipComponent;
  @ViewChild('chipSubscriptions') chipSubscriptions: ChipComponent;

  constructor(
    private userService: UserService,
  ) { }

  ngOnInit() {
    this.userLogged = this.userService.getCurrentUser();
    this.lastUserLogged = JSON.parse(JSON.stringify(this.userLogged));
    console.log(this.lastUserLogged);
    console.log(this.userLogged);
  }

  onUpdateForm() {
    if (this.changed()) {
      this.userService.update(this.userLogged);
      this.lastUserLogged = new User(this.userLogged);
    }
  }

  onResetForm() {
    window.location.reload();
  }

  changed(): boolean {
    return this.userLogged.lastName !== this.lastUserLogged.lastName ||
          JSON.stringify(this.userLogged.qualificationSet) !== JSON.stringify(this.lastUserLogged.qualificationSet);
  }

}
