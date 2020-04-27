import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/models/user';
import { MatDialog } from '@angular/material/dialog';
import { ProfilePictureUploader } from '../home/profile-picture-uploader/profile-picture-uploader';
import { UserService } from '../shared/services/user.service';
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { DomSanitizer } from "@angular/platform-browser";

@Component({
  selector: 'app-menuprofile',
  templateUrl: './menuprofile.component.html',
  styleUrls: ['./menuprofile.component.scss']
})
export class MenuprofileComponent implements OnInit {
  public photoProfile: any;

  user: User = new User({
    id: 1,
    firstName: 'Steeve',
    lastName: 'Jobs',
    email: 'SteveJobs@gmail.com',
    mobileNumber: '0123456789',
    status: 'Etudiant',
    validated: true,
    photo: true,
  });


  constructor(public dialog: MatDialog, public userService: UserService, private http: HttpClient, private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    console.log(this.user.photo);
    console.log(this.user.id)
    if (this.user.photo) {
      this.http.get(environment.base_url + `/users/image/${this.user.id}`, { responseType: 'blob' })
        .subscribe(dataBlob => {
          const objectURL = URL.createObjectURL(dataBlob);

          this.userService.userLogged.photoProfile = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        });
    } else {
      this.userService.userLogged.photoProfile = '../../../../assets/pictures/profile_defaut.jpg';
    }
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
