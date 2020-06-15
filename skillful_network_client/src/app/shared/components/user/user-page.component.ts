import { Component, ViewChild, Input } from '@angular/core';
import { User } from 'src/app/shared/models/user/user';
import { ChipComponent } from 'src/app/shared/components/chip/chip.component';
import { UserConfComponent } from './user-conf/user-conf.component';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.scss']
})
export class UserPageComponent {

  @Input() userLogged: User; 
  @Input() readonly: boolean;

  private lastUserLogged: User;

  @ViewChild('userConfiguration') userConfiguration: UserConfComponent;
  @ViewChild('chipQualifications') chipQualifications: ChipComponent;
  @ViewChild('chipSkills') chipSkills: ChipComponent;
  @ViewChild('chipSubscriptions') chipSubscriptions: ChipComponent;

  constructor(
    private userService: UserService,
  ) { }

  ngOnInit() {
    // this.userLogged = this.userService.getCurrentUser();
    this.lastUserLogged = JSON.parse(JSON.stringify(this.userLogged));
  }

  onUpdateForm() {
    if (this.changed()) {
      this.userService.update(this.userLogged);
      this.lastUserLogged = new User(this.userLogged);
    }
  }

  onResetForm() {
    this.userLogged = this.lastUserLogged;
    this.userConfiguration.initValue(this.userLogged);
  }

  changed(): boolean {
    return this.userLogged != undefined && (
      this.userLogged.lastName !== this.lastUserLogged.lastName ||
      this.userLogged.firstName !== this.lastUserLogged.firstName ||
      this.userLogged.email !== this.lastUserLogged.email ||
      this.userLogged.birthDate !== this.lastUserLogged.birthDate ||
      this.userLogged.mobileNumber !== this.lastUserLogged.mobileNumber ||
      this.userLogged.careerGoal !== this.lastUserLogged.careerGoal ||
      JSON.stringify(this.userLogged.qualificationSet) !== JSON.stringify(this.lastUserLogged.qualificationSet) ||
      JSON.stringify(this.userLogged.subscriptionSet) !== JSON.stringify(this.lastUserLogged.subscriptionSet) ||
      JSON.stringify(this.userLogged.skillSet) !== JSON.stringify(this.lastUserLogged.skillSet)
    );
  }

}
