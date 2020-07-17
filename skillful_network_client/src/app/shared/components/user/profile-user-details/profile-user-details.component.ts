import { Component, OnInit, Input, ViewChild, Output, TemplateRef } from '@angular/core';
import { User } from 'src/app/shared/models/user/user';
import { UserPageComponent } from '../user-page.component';
import { ChipComponent } from '../../chip/chip.component';
import { FollowStateTrackerService } from 'src/app/shared/services/FollowStateTracker.service';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-profile-user-details',
  templateUrl: './profile-user-details.component.html',
  styleUrls: ['./profile-user-details.component.scss']
})
export class ProfileUserDetailsComponent implements OnInit {
  @Input() type: string;
  @Input() readOnly: boolean;
  @Input() userLogged: User;
  @Input() public chips: TemplateRef<any>;
  public cardRef: TemplateRef<any>;
  public title: string = "Informations";

  @ViewChild('chipQualifications') chipQualifications: ChipComponent;
  @ViewChild('chipSkills') chipSkills: ChipComponent;
  @ViewChild('chipSubscriptions') chipSubscriptions: ChipComponent;
  @ViewChild('userPage') userPage: UserPageComponent;

  totalFollowed: number = 0;
  totalFollowers: number = 0;
  toggleFollow: boolean;
  constructor(private followService: FollowStateTrackerService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.userLogged = new User({});
    console.log(this.userLogged);
    this.countFollowed();
    this.countFollowers();
   
  }

  ngAfterViewInit(){
    this.toggleFollow = this.isFollowed(this.toggleFollow);
    console.log("toggleFollow: " + this.toggleFollow);
  }

  async countFollowed() {
    this.followService.getFollowedCount().then((data) => {
      this.totalFollowed = data;
    }).catch((error) => {
      console.log(error);
    });
  }

  async countFollowers() {
    this.followService.getFollowerCount().then((data) => {
      this.totalFollowers = data;
    }).catch((error) => {
      console.log(error);
    });
  }

  async toggleFollowUnfollow() {
    let response;
    if (this.isFollowed(this.toggleFollow)) {
      response = this.followService.unfollowByFollowedId(this.userLogged.id);
    } else {
      response = this.followService.followByFollowableId(this.userLogged.id)
    }
    await response.catch((error) => {
      console.log(error);
    });
  }

  isFollowed(toggle: boolean) {
    let currentUser: User = this.authService.user;
    toggle = true;
    this.userLogged.followableSet.map((item) => {
      if (item.follower.id == currentUser.id) { toggle = false; }
    })
    return toggle;
  }
}


