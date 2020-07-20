import { FollowStateTrackerService } from './../../../shared/services/FollowStateTracker.service';
import { AuthService } from './../../../shared/services/auth.service';
import { Notification } from './../../../shared/models/application/notification';
import { Component, OnInit, Input } from '@angular/core';
import { User } from 'src/app/shared/models/user/user';
import { PostService } from 'src/app/shared/services/post.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {
  @Input() public notification: Notification;
  public owner: User = new User({});
  public label: string;

  constructor(private postService: PostService, private fst: FollowStateTrackerService, private authService: AuthService) { }

  async ngOnInit() {
    await this.getOwner();
    console.log('NgOnInit : ');
    console.log(this.owner);
  }

   public async getOwner() {
    await this.postService.getUserByPostId(this.notification.getPostId).then(data => {
      console.log('GetOwner : ');
      this.owner = new User(data);
      console.log(this.owner);
    });
  }

  public async setIsRead() {
    await this.fst.setNotificationsReadStatus(this.authService.user.id, this.notification.getId, true);
  }

}
