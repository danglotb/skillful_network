import { Component, OnInit, Input } from '@angular/core';
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
  public name: string;

  constructor(
    public dialog: MatDialog,
    public userService: UserService) {
  }

  ngOnInit(): void {
    let currentUser: User;
    this.userService.getCurrentUser().then(
      data => {
        currentUser = new User(data);
        // if (currentUser.profilePicture == null) { // if so, the user did not yet upload its own profile picture
        //   currentUser.profilePicture = this.sanitizer.bypassSecurityTrustUrl(
        //     'https://www.gravatar.com/avatar/' + currentUser.id + '?s=128&d=identicon&r=PG'
        //   );
        // }
        // Might need the following code when the backend provides a profile picture
        // const objectURL = URL.createObjectURL(data);
        // currentUser.profilePicture = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      }
    ).finally(() => {
      this.name = currentUser.firstName + ' ' + currentUser.lastName;
      console.log(currentUser.profilePicture)
      this.photoProfile = currentUser.profilePicture;
    })

  }

  onSelectFile(e) {
    if (e.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (event: any) => {
        console.log(event.target)
        this.photoProfile = event.target.result;
      }
    }
  }

  openModalProfile() {
    const dialogRef = this.dialog.open(ProfilePictureUploader, {
      data: { user: this.photoProfile }
    });
  }


}
