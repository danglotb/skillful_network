import { Component, OnInit, Input, ViewChild, Output, TemplateRef, SimpleChanges } from '@angular/core';
import { User } from 'src/app/shared/models/user/user';
import { UserPageComponent } from '../user-page.component';
import { ChipComponent } from '../../chip/chip.component';
import { FollowStateTrackerService } from 'src/app/shared/services/FollowStateTracker.service';
import { AuthService } from 'src/app/shared/services/auth.service';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { UserService } from 'src/app/shared/services/user.service';

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

  totalFollowed: number = 0;
  totalFollowers: number = 0;
  toggleFollow: boolean;
  idUserPick: number;
  userPick: User;

  constructor(private followService: FollowStateTrackerService, private authService: AuthService, private route: ActivatedRoute, private userService: UserService) {
  }

  async ngOnInit(){
    console.log("ngOninit");
    this.userLogged = new User({});
    this.userPick = new User({});
    this.idUserPick = this.route.snapshot.params.id;

    await this.getUser(this.idUserPick);

    this.toggleFollow = this.isFollowed(); 
  }

  ngAfterViewInit(){
    console.log("afterInit");

    this.countFollowers(this.idUserPick);
    this.countFollowed(this.idUserPick);
  }

  async countFollowers(FollowableId: number) {
    await this.followService.getFollowersCountByFollowableId(FollowableId).then((data) => {
      this.totalFollowers = data;
    }).catch((error) => {
      console.log(error);
    });
  }

  async countFollowed(idFollower: number) {
    await this.followService.getFollowedCountByFollowerId(idFollower).then((data) => {
      this.totalFollowed = data;
    }).catch((error) => {
      console.log(error);
    });
  }

  async toggleFollowUnfollow(event: Event) {
    let response;
    if (!this.toggleFollow) {
      response = this.followService.followByFollowableId(this.userPick.id);
    } else {
      response = this.followService.unfollowByFollowedId(this.userPick.id);
    }
    await response.catch((error) => {
      console.log(error);
    });
    this.ngAfterViewInit();
    this.toggleFollow = !this.toggleFollow;
  }

    isFollowed() : boolean{
    let currentUser: User = this.authService.user;
    let response = false;
    this.userPick.followableSet.map((item) => {
      if (item.follower.id === currentUser.id) {
        response = true; 
      }
    });
    return response;
  }

  cantBeFollow() : boolean{
    let currentUser: User = this.authService.user;
    let response = false;
    if(currentUser.id == this.userPick.id){
      response =  true;
    }
    return response;
  }

  async getUser(id: number) {
    await this.userService.getById(id).then((data) => {
      this.userPick = data;
      return this.userPick;
    }).catch((error) => {
      console.log(error);
    });
  }




}


