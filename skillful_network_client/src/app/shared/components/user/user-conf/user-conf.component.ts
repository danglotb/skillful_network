import { Component, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { User } from 'src/app/shared/models/user/user';

@Component({
  selector: 'app-user-conf',
  templateUrl: './user-conf.component.html',
  styleUrls: ['./user-conf.component.scss']
})

export class UserConfComponent {

  public title: string = 'Informations'
  @Input() userLogged: User;
  @Input() readonly: boolean;
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
    this.formGroup.valueChanges.subscribe(data => {
      this.userLogged.lastName = data.lastName;
      this.userLogged.firstName = data.firstName;
      this.userLogged.birthDate = data.birthDate;
      this.userLogged.email = data.email;
      this.userLogged.mobileNumber = data.mobileNumber;
      this.userLogged.careerGoal = data.careerGoal;
    });
    if (this.readonly) {
      this.formGroup.disable();
    }
  }

  public initValue(user: User): void {
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

}
