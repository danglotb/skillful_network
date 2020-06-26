import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Role } from '../shared/models/user/role';
import { AuthService } from '../shared/services/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../shared/services/user.service';

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
  selectedValue: string = '';

  // FormGroup pour le formulaire Registration
  registrationForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private userService: UserService, private router: Router) {
    this.buildForm();
  }

  ngOnInit(): void {
  }
  
  //intégre les formControl au sein du formGroup
  buildForm() {
    this.registrationForm = this.formBuilder.group({
      selectRole: new FormControl('', Validators.required),
      name: new FormControl('', Validators.required),
      firstName: new FormControl('')
    });
  }

  //Récupère via l'évenement de sélection du select la valeur du role
  getSelected(event: Event): string {
    this.selectedValue = this.registrationForm.value.selectRole.value
    return this.selectedValue;
  }

  //Change le validator selon le rôle
  setValidator(event: Event) {
    if (this.getSelected(this.event) == 'ROLE_USER') {
      this.registrationForm.get('firstName').setValidators(Validators.required);
    } else {
      this.registrationForm.get('firstName').setValidators([]);
    }
    this.registrationForm.get('firstName').updateValueAndValidity();
  }

  async registration() {
    await this.userService.updateConfirmationRegister(this.registrationForm.value.firstName, this.registrationForm.value.name, [this.selectedValue])
      .then((data) => {
        this.authService.user = data;
        console.log(data);
        this.router.navigate(['/home']);
      }).catch((error) => {
        console.log(error);
      });
  }
}

