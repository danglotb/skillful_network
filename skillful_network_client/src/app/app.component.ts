import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './shared/services/token-storage.service';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';
import { pipe } from 'rxjs';
import { ApiHelperService } from './shared/services/api-helper.service';
import { UserService } from './shared/services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  isLogging : boolean;

  private urlActual: String;

  constructor(private tokenStorageService: TokenStorageService, private snackBar: MatSnackBar, private router: Router, private api: ApiHelperService, private userService: UserService) {

  }
  ngOnInit(): void {
    if (!this.urlIsAboutLogin()) {
      this.isLogging = true;
      this.api.post({
        endpoint: "/whoami",
        data: this.tokenStorageService.getToken()
      }).then(data => {
        this.userService.initUserLoggedWithObject(data)
        this.isLogging = true;
      }).catch(err => {
        console.log(err);
        localStorage.clear()
        this.router.navigate(['/login']);
        this.snackBar.open("Votre token a été expiré, veuillez vous connectez de nouveau !", "", {
          duration: 5000,
        });
        this.isLogging = true;
      })
    } else if (this.tokenStorageService.getToken() != null) {
      this.api.post({ endpoint: "/whoami", data: this.tokenStorageService.getToken() }).then(data => {
        this.userService.updateUser(data, false);
        this.router.navigate(['/home']);
        this.snackBar.open("Vous êtes déja connecté, veuillez vous déconnecter pour vous connecter à un autre compte !", "", {
          duration: 5000,
        });
        this.isLogging = true;
      }
      ).catch(err => {
        localStorage.clear()
        this.router.navigate(['/login']);
        this.isLogging = true;
      })
    }
  }

  private urlIsAboutLogin(): boolean {
    let actualUrl = location.pathname.toString();
    return actualUrl == "/" ||
      actualUrl.includes("login") ||
      actualUrl.includes("password") ||
      actualUrl.includes("passwordForgotten");
  }
}

