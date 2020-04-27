import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { User } from '../../shared/models/user';
import { UserService } from '../../shared/services/user.service';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

export interface DialogData {
  user: User;
}

@Component({
  selector: 'profile-picture-uploader',
  templateUrl: 'profile-picture-uploader.html',
  styleUrls: ['profile-picture-uploader.css'],
})
export class ProfilePictureUploader {

  public profilPicture;

  constructor(
    public dialogRef: MatDialogRef<ProfilePictureUploader>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData, public userService: UserService, private http: HttpClient) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onValidate(): void {
    let body = new FormData();
    body.append("image", this.dataURItoBlob(this.profilPicture), 'image.png');
    this.http.post(environment.base_url + '/users/uploadProfilePicture', body).toPromise()
    .then((res) => {
      console.log(res);
      this.userService.userLogged.photoProfile = this.profilPicture;
    })
    this.dialogRef.close();
  }

  dataURItoBlob(dataURI) {
    var byteString = atob(dataURI.split(',')[1]);
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
    var blob = new Blob([ab], { type: mimeString });
    return blob;

  }

  onSelectFile(event) {
    if (event.target.files) {
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (event: any) => {
        console.log(event);
        this.profilPicture = event.target.result;
      }
    }
  }


}
