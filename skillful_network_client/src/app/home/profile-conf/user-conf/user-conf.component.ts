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
  formGroup: FormGroup = new FormGroup({});

  constructor(
    private _adapter: DateAdapter<any>,
    private userService: UserService
  ) {
    this._adapter.setLocale('fr');
  }

  ngOnInit() {
    this.userService.getCurrentUser().then(data => {
      this.formGroup = new FormGroup({
        lastName: new FormControl(data.lastName, [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
        firstName: new FormControl(data.firstName, [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
        birthDate: new FormControl(data.birthDate, Validators.required),
        email: new FormControl(data.email, [Validators.required, Validators.email]),
        mobileNumber: new FormControl(data.mobileNumber, [Validators.required, Validators.minLength(10)]),
        careerGoal: new FormControl(data.careerGoal, [Validators.required, Validators.minLength(3)])
      });
    });
  }


}
