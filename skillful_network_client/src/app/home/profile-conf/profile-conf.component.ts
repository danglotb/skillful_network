import { Component, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { UserService } from "../../shared/services/user.service"
import { User } from 'src/app/shared/models/user/user';
import { ChipComponent } from 'src/app/shared/components/chip/chip.component';
import { UserConfComponent } from './user-conf/user-conf.component';

@Component({
  selector: 'app-profile-conf',
  templateUrl: './profile-conf.component.html',
  styleUrls: ['./profile-conf.component.scss']
})
export class ProfileConfComponent {

  public userLogged: User;

  @ViewChild('userConfiguration') userConfiguration: UserConfComponent;
  @ViewChild('chipQualifications') chipQualifications: ChipComponent;
  @ViewChild('chipSkills') chipSkills: ChipComponent;
  @ViewChild('chipSubscriptions') chipSubscriptions: ChipComponent;

  constructor(
    private userService: UserService,
  ) { }

  ngOnInit() {
    this.userLogged = this.userService.getCurrentUser();
  }

  onUpdateForm() {
    this.userLogged = this.userConfiguration.userLogged;
    this.userLogged.qualificationSet = this.chipQualifications.chipValues;
    this.userLogged.skillSet = this.chipSkills.chipValues;
    this.userLogged.subscriptionSet = this.chipSubscriptions.chipValues;
    this.userService.update(this.userLogged);
  }

  onResetForm() {
    window.location.reload();
  }

  changed(): boolean {
    return true;
  }

}
