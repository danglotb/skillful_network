import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/user/user';
import { MatDialog } from '@angular/material/dialog';
import { ProfilePictureUploader } from '../profile-picture-uploader/profile-picture-uploader';
import { UserService } from '../../shared/services/user.service';
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
    let currentUser : User = this.userService.getCurrentUser();
    this.userService.getCurrentProfilePicture().then( data => {
      const objectURL = URL.createObjectURL(data);
      currentUser.photoProfile = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    }).catch( (error => {
        if (error.error.status == 404) {
          currentUser.photoProfile = this.sanitizer.bypassSecurityTrustUrl(
            'https://www.gravatar.com/avatar/' + currentUser.id + '?s=128&d=identicon&r=PG'
          );
        }
      })
    ).finally(() => this.photoProfile = currentUser.photoProfile)
  }

  onSelectFile(e) {
    let currentUser : User = this.userService.getCurrentUser();
    if (e.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (event: any) => {
        console.log(event.target)
        currentUser.photoProfile = event.target.result;
      }
    }
  }

  openModalProfile() {
    let currentUser : User = this.userService.getCurrentUser();
    const dialogRef = this.dialog.open(ProfilePictureUploader, {
      data: { user: currentUser.photoProfile }
    });
  }


}
