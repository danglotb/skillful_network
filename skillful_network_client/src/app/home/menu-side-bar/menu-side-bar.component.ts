import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/user/user';
import { MatDialog } from '@angular/material/dialog';
import { ProfilePictureUploader } from '../profile-picture-uploader/profile-picture-uploader';
import { UserService } from '../../shared/services/user.service';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { DomSanitizer } from "@angular/platform-browser";

@Component({
  selector: 'app-menu-side-bar',
  templateUrl: './menu-side-bar.component.html',
  styleUrls: ['./menu-side-bar.component.scss']
})
export class MenuSideBarComponent implements OnInit {
  
  public photoProfile: any;

  constructor(
      public dialog: MatDialog, 
      public userService: UserService,
      private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.userService.getCurrentProfilePicture().then( data => {
      const objectURL = URL.createObjectURL(data);
      this.userService.userLogged.photoProfile = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    }).catch( (error => {
        if (error.error.status == 404) {
          this.userService.userLogged.photoProfile = this.sanitizer.bypassSecurityTrustUrl(
            'https://www.gravatar.com/avatar/' + this.userService.userLogged.id + '?s=128&d=identicon&r=PG'
          );
        }
      })
    )
  }

  onSelectFile(e) {
    if (e.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (event: any) => {
        console.log(event.target)
        this.userService.userLogged.photoProfile = event.target.result;
      }
    }
  }

  openModalProfile() {
    const dialogRef = this.dialog.open(ProfilePictureUploader, {
      data: { user: this.userService.userLogged.photoProfile }
    });
  }


}
