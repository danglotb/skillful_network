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
  public chipQualificationValues: Perk[] = [];
  public chipSkillValues: Perk[] = [];
  public chipSubscriptionValues: Perk[] = [];
  private lastUserLogged: User;

  @ViewChild('userConfiguration') userConfiguration: UserConfComponent;
  @ViewChild('chipQualifications') chipQualifications: ChipComponent;
  @ViewChild('chipSkills') chipSkills: ChipComponent;
  @ViewChild('chipSubscriptions') chipSubscriptions: ChipComponent;

  constructor(
    private userService: UserService,
  ) { }

  ngOnInit() {
    this.userService.getCurrentUser().then(data => {
      this.userLogged = data;
      this.lastUserLogged = JSON.parse(JSON.stringify(this.userLogged));
      this.chipQualificationValues = this.userLogged.qualificationSet;
      this.chipSkillValues = this.userLogged.skillSet;
      this.chipSubscriptionValues = this.userLogged.subscriptionSet;
    });
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
    return this.userLogged != undefined && (
      this.userLogged.lastName !== this.lastUserLogged.lastName ||
      JSON.stringify(this.userLogged.qualificationSet) !== JSON.stringify(this.lastUserLogged.qualificationSet)
    );
  }

}
