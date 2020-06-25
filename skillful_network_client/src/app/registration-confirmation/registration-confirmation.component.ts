import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Role } from '../shared/models/user/role';

@Component({
  selector: 'app-registration-confirmation',
  templateUrl: './registration-confirmation.component.html',
  styleUrls: ['./registration-confirmation.component.scss']
})
export class RegistrationConfirmationComponent implements OnInit {

  //Selection des rôles utilisateurs
  value: string;
  viewValue: string;
  roles: Role[] = [
    { value: 'ROLE_USER', valueView: 'utilisateur' },
    { value: 'ROLE_COMPANY', valueView: 'entreprise' },
    { value: 'ROLE_TRAINING_ORGANIZATION', valueView: 'organisme de formation' }
  ];
  event: Event;

  // FormGroup pour le formulaire Registration
  registrationForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.buildForm();
  }

  ngOnInit(): void {
    this.registration();
  }
  //intégre les formControl au sein du formGroup
  buildForm() {
    this.registrationForm = this.formBuilder.group({
      selectRole: new FormControl('', Validators.required),
      name: new FormControl('', Validators.required),
      firstName: new FormControl('')
    });
  }

  //Seul l'utilisateur a un prénom: enlève le required selon la valeur "role"
  setValidator(event: Event) {
    if (this.registrationForm.value.selectRole.value == 'ROLE_USER') {
      this.registrationForm.get('firstName').setValidators(Validators.required);
    } else {
      this.registrationForm.get('firstName').setValidators([]);
    }
    this.registrationForm.get('firstName').updateValueAndValidity();
  }

  registration() {
    console.log(this.registrationForm.value);
  }
}
