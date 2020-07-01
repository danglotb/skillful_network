import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { AuthService } from '../shared/services/auth.service';
import { Router } from '@angular/router';
import { UserService } from '../shared/services/user.service';
import { RoleService } from '../shared/services/role.service';
import { async } from '@angular/core/testing';
import { ValueConverter } from '@angular/compiler/src/render3/view/template';
import { newArray } from '@angular/compiler/src/util';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { keyValuesToMap } from '@angular/flex-layout/extended/typings/style/style-transforms';

@Component({
  selector: 'app-registration-confirmation',
  templateUrl: './registration-confirmation.component.html',
  styleUrls: ['./registration-confirmation.component.scss']
})
export class RegistrationConfirmationComponent implements OnInit {
  //template
  @Input() title:string = "Finalisation inscription";
  @Input() icon:string = "person_outline";


  roles = new Map();

  //variable pour récupérer la valeur du <mat-selection> du formControl selectRole
  event: Event;
  selectedValue: string = '';

  // FormGroup pour le formulaire Registration
  registrationForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private userService: UserService, private roleService: RoleService, private router: Router) {
    this.buildForm();
    this.getRoles();
  }

  ngOnInit(): void {
  }
  

  //intégre les formControl au sein du formGroup
  buildForm() {
    this.registrationForm = this.formBuilder.group({
      selectRole: new FormControl('', Validators.required),
      name: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
      firstName: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)])
    });
  }

  //Récupère via l'évenement de sélection du select la valeur du role
  getSelected(event: Event): string {
    this.selectedValue = this.registrationForm.value.selectRole;
    console.log(this.selectedValue);
    return this.selectedValue;
  }

  //Change le type de validation selon le rôle
  setValidator(event: Event) {
    if (this.getSelected(this.event) == 'ROLE_USER') {
      this.registrationForm.get('firstName').setValidators([Validators.required, Validators.minLength(3), Validators.maxLength(20)]);
    } else {
      this.registrationForm.get('firstName').setValidators([]);
    }
    this.registrationForm.get('firstName').updateValueAndValidity();
  }

  async registration() {
    let response;
    if (this.selectedValue == 'ROLE_USER') {
      response = this.userService.updateConfirmationRegister(this.registrationForm.value.firstName, this.registrationForm.value.name, [this.selectedValue]);
    } else {
      response = this.userService.updateConfirmationRegister(" ", this.registrationForm.value.name, [this.selectedValue]);
    }
    await response.then((data) => {
      this.authService.user = data;
      console.log(data);
      this.router.navigate(['/home']);
    }).catch((error) => {
      console.log(error);
    });
  }

  async getRoles() {
    
    await this.roleService.getRoles().then((data) => {
     Object.entries(data).forEach(
        ([key, value]) => this.roles.set(key,value)
        );
     console.log(this.roles);
    }).catch((error) => {
      console.log(error);
    });
  }
  
}

