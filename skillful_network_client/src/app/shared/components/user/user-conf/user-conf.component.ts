import { Component, Input, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from 'src/app/shared/models/user/user';

import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';

import * as _moment from 'moment';
import { FollowStateTrackerService } from 'src/app/shared/services/FollowStateTracker.service';
const moment = _moment;

export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'LL',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-user-conf',
  templateUrl: './user-conf.component.html',
  styleUrls: ['./user-conf.component.scss'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})

export class UserConfComponent {

  public title: string = 'Informations'
  private userLogged: User;
  totalFollowers: number;
  totalFollowed: number;
  @Input() readonly: boolean;
  formGroup: FormGroup;
  keys = [
    'lastName',
    'firstName',
    'birthDate',
    'email',
    'mobileNumber',
    'careerGoal'
  ];
  labels = {
    lastName: 'Votre nom',
    firstName: 'Votre prénom',
    birthDate: 'Votre date de naissance',
    email: 'Votre email',
    mobileNumber: 'Votre numéro de téléphone',
    careerGoal: 'Votre objectif professionel'
  };
  placeholders = {
    lastName: 'Nom',
    firstName: 'Prénom',
    birthDate: 'Date de naissance',
    email: 'email',
    mobileNumber: 'Téléphone',
    careerGoal: 'Objectif professionel'
  };
  hints = {
    lastName: 'Nom',
    firstName: 'Prénom',
    birthDate: 'Date de naissance',
    email: 'email',
    mobileNumber: 'Téléphone',
    careerGoal: 'Objectif professionel'
  };
  icons = {
    lastName: 'account_box',
    firstName: 'account_circle',
    birthDate: '',
    email: 'email',
    mobileNumber: 'local_phone',
    careerGoal: 'business_center'
  };
  types = {
    lastName: 'input',
    firstName: 'input',
    birthDate: 'datepicker',
    email: 'input',
    mobileNumber: 'input',
    careerGoal: 'input'
  }

  constructor(
    private _adapter: DateAdapter<any>,
    private followService: FollowStateTrackerService
  ) {
    this._adapter.setLocale('fr');
  }

  ngOnInit() {
    this.formGroup = new FormGroup({
      lastName: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
      firstName: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
      birthDate: new FormControl(moment(), Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      mobileNumber: new FormControl('', [Validators.required, Validators.minLength(10)]),
      careerGoal: new FormControl('', [Validators.required, Validators.minLength(3)])
    });
    this.userLogged = new User({});
    this.formGroup.valueChanges.subscribe(data => {
      this.userLogged.lastName = data.lastName;
      this.userLogged.firstName = data.firstName;
      this.userLogged.birthDate = data.birthDate;
      this.userLogged.email = data.email;
      this.userLogged.mobileNumber = data.mobileNumber;
      this.userLogged.careerGoal = data.careerGoal;
    });

    let onChangeFunction = data => {
      this.userLogged.lastName = data.lastName;
      this.userLogged.firstName = data.firstName;
      this.userLogged.birthDate = data.birthDate;
      this.userLogged.email = data.email;
      this.userLogged.mobileNumber = data.mobileNumber;
      this.userLogged.careerGoal = data.careerGoal;
    }

    let init = user => {
      console.log("INIT");
      this.formGroup.setValue({
        lastName: user.lastName,
        firstName: user.firstName,
        birthDate: user.birthDate,
        email: user.email,
        mobileNumber: user.mobileNumber,
        careerGoal: user.careerGoal
      });
      this.userLogged = user;
    }

    if (this.readonly) {
      this.formGroup.disable();
    }

    this.countFollowers();
    this.countFollowed();
  }

  public init(user: User): void {
    console.log("INIT");
    this.formGroup.setValue({
      lastName: user.lastName,
      firstName: user.firstName,
      birthDate: user.birthDate,
      email: user.email,
      mobileNumber: user.mobileNumber,
      careerGoal: user.careerGoal
    });
    this.userLogged = user;
  }

  //::en cours
  async setFollowerNotifiableStatus(status: string) {
    await this.followService.setFollowerNotifiableStatus(status).then((data) => {
      console.log("data: " + data);
    }).catch((error) => {
      console.log(error);
    });
  }
  async trigger(value: string) {
    console.log("value: " + value);
    console.log("before: " + this.userLogged.followableNotifiable);
    await this.setFollowerNotifiableStatus(value);
    console.log("after: " + this.userLogged.followableNotifiable);
  }
  //::
  async countFollowers() {
    await this.followService.getFollowersCount().then((data) => {
      this.totalFollowers = data;
    }).catch((error) => {
      console.log(error);
    });
  }

  async countFollowed() {
    await this.followService.getFollowedCount().then((data) => {
      this.totalFollowed = data;
    }).catch((error) => {
      console.log(error);
    });
  }


}
