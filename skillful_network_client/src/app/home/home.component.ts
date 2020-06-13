import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { TokenStorageService } from '../shared/services/token-storage.service';
import { AuthService } from '../shared/services/auth.service';
import { User } from '../shared/models/user/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    private router: Router,
    private service: TokenStorageService,
    public dialog: MatDialog) {
  }

  ngOnInit() {

  }

  logOut() {
    this.service.signOut();
    this.router.navigate(['/login']);
  }
}
