import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './shared/services/token-storage.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from './shared/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  isLogging: boolean;

  constructor(
    private tokenStorageService: TokenStorageService,
    private snackBar: MatSnackBar,
    private router: Router,
    private authService: AuthService) {
  }

  ngOnInit(): void {
    if (!this.urlIsAboutLogin()) {
      this.isLogging = true;
      this.authService.whoami(this.tokenStorageService.getToken()).then(data => {
        this.authService.user = data;
        this.isLogging = false;
      }).catch(err => {
        console.log(err);
        localStorage.clear()
        this.router.navigate(['/login']);
        this.snackBar.open("Votre token a été expiré, veuillez vous connectez de nouveau !", "", {
          duration: 5000,
        });
        this.isLogging = false;
      })
    } else if (this.tokenStorageService.getToken() != null) {
      this.authService.whoami(this.tokenStorageService.getToken()).then(data => {
        this.authService.user = data;
        this.router.navigate(['/home']);
        this.snackBar.open("Vous êtes déja connecté, veuillez vous déconnecter pour vous connecter à un autre compte !", "", {
          duration: 5000,
        });
        this.isLogging = false;
      }
      ).catch(err => {
        localStorage.clear()
        this.router.navigate(['/login']);
        this.isLogging = false;
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

