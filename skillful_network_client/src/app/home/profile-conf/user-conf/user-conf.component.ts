import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { User } from 'src/app/shared/models/user/user';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-user-conf',
  templateUrl: './user-conf.component.html',
  styleUrls: ['./user-conf.component.scss']
})

export class UserConfComponent {

  public title: string = 'Informations'
  @Input() userLogged: User;
  formGroup: FormGroup;

  constructor(
    private _adapter: DateAdapter<any>
  ) {
    this._adapter.setLocale('fr');
  }

  ngOnInit() {
    console.log(this.userLogged);
    this.formGroup = new FormGroup({
      lastName: new FormControl(this.userLogged.lastName, [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
      firstName: new FormControl(this.userLogged.firstName, [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
      birthDate: new FormControl(this.userLogged.birthDate, Validators.required),
      email: new FormControl(this.userLogged.email, [Validators.required, Validators.email]),
      mobileNumber: new FormControl(this.userLogged.mobileNumber, [Validators.required, Validators.minLength(10)]),
      careerGoal: new FormControl(this.userLogged.careerGoal, [Validators.required, Validators.minLength(3)])
    });
  }


}
