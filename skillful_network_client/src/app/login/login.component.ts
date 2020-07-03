import { Component, OnInit, Input } from '@angular/core';
import { ApiHelperService } from '../shared/services/api-helper.service';
import { AuthService } from '../shared/services/auth.service';
import { TokenStorageService } from '../shared/services/token-storage.service';
import { Router } from '@angular/router';
import { FormControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JwtResponse } from '../shared/models/jwt-response';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  @Input() title: string = "Connexion";
  @Input() icon: string = "perm_identity";


  loginForm: FormGroup;
  public username: string;
  public email: string;
  public error: boolean;
  public password: string;
  role: string[];
  isLoginFailed = false;
  public rememberMe: FormControl = new FormControl(false);
  isChecked: boolean;
  checkExistingMail: boolean = false;

  // tslint:disable-next-line: max-line-length
  private _emailRegex = '^(([^<>()\\[\\]\\\\.,;:\\s@"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@"]+)*)|(".+"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$';

  // Définitions des FormControl et FormGroup pour les 2 formulaires : Inscription et Login
  emailControlInscription: FormControl;
  emailControlLogin: FormControl;
  passwordControlLogin: FormControl;
  inscriptionFormGroup: FormGroup;
  loginFormGroup: FormGroup;
  codeForm: FormGroup;

  // variable qui servira à afficher le formulaire approprié en fonction du context
  public doDisplayCodeVerif = false;

  dialogResult = "";

  constructor(private api: ApiHelperService,
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private _snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.buildFormInscription();
    this.buildFormLogin();
    this.codeFormBuild();
    this.doDisplayCodeVerif = false;
  }

  login() {
    localStorage.clear();
    sessionStorage.clear();
    let response: Promise<JwtResponse>;
    if (this.doDisplayCodeVerif) {
      response = this.authService.login(this.inscriptionFormGroup.value.emailInscription, this.codeForm.value.code);
    } else {
      response = this.authService.login(this.loginFormGroup.value.emailLogin, this.loginFormGroup.value.password);
    }
    response.then((data) => {
      this.tokenStorage.saveTokenAndCurrentUsername(data.token, data.authorities, this.isChecked ? 'local' : '');
      this.authService.user = data.user;
      if (this.doDisplayCodeVerif) {
        this.router.navigate(['/registration']);
      } else {
        this.router.navigate(['/home']);
      }
    }).catch((error) => {
      this.isLoginFailed = true;
    });
  }

  register() {
    this.authService.register(this.inscriptionFormGroup.value.emailInscription, ['ROLE_USER'])
      .then((response) => {
        this.checkExistingMail = true;
        this.reloadPage();
      }).catch((error) => {
        if (error.status == 401) {
          this.doDisplayCodeVerif = true;
        } else {
          console.log(error);
        }
      });
  }

  // Création du formulaire inscription avec un seul champ email
  buildFormInscription() {
    this.emailControlInscription = new FormControl(null, Validators.compose([Validators.pattern(this._emailRegex), Validators.required]));
    this.inscriptionFormGroup = new FormGroup({
      emailInscription: this.emailControlInscription
    });
  }

  // Création du formulaire Login avec un champ email et password
  buildFormLogin() {
    this.emailControlLogin = new FormControl(null, Validators.compose([Validators.pattern(this._emailRegex), Validators.required]));
    this.passwordControlLogin = new FormControl(null, Validators.compose([Validators.required, Validators.minLength(8)]));
    this.loginFormGroup = new FormGroup({
      emailLogin: this.emailControlLogin,
      password: this.passwordControlLogin
    });
  }
  // Création du formulaire code avec un champ code
  codeFormBuild() {
    this.codeForm = this.formBuilder.group({
      code: ['', [Validators.required, Validators.minLength(10)]],
    });
  }
  onChange(event) {
    this.isChecked = event;
    // can't event.preventDefault();
    console.log('onChange event.checked ' + event.checked);
  }

  snackBarNewCodeSend() {
    this._snackBar.open("Vous avez reçu un nouveau code", "X", {
      duration: 1500,
      verticalPosition: 'bottom'
    });
  }

  reloadPage() {
    setTimeout(function () { window.location.reload(); }, 3000);
  }
}
