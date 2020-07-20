import { AuthService } from './../../shared/services/auth.service';
import { FollowStateTrackerService } from 'src/app/shared/services/FollowStateTracker.service';
import { Notification } from './../../shared/models/application/notification';
import { Component, OnInit } from '@angular/core';
import { empty } from 'rxjs';

@Component({
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit {
  public listNotification = ['n1', 'n2'];
  public notifications: Set<Notification> = new Set<Notification>();
  public newsIsEmpty: boolean = false;
  public oldNotifications: Set<Notification> = new Set<Notification>();
  public titleNew: string = 'Récemment';
  public titleOld: string = 'Some time ago';

  constructor(private fstService: FollowStateTrackerService, private authService: AuthService) { }

  async ngOnInit() {
    await this.getUnreadNotifications();
    console.log(this.notifications);
    console.log('notifications is empty from ngOnInit : ' + this.newsIsEmpty);
  }

  public async getUnreadNotifications() {
    await this.fstService.unreadNotifications().then(data => {
      console.log(data);
      if (data !== null) {
        data.forEach(item => {
          const notif = new Notification(item);
          // Don't know if it's best practice but do the job
          const notification = Object.setPrototypeOf(item, Notification);
          console.log(notif);
          console.log(notification);
          this.notifications.add(notif);
        });
      }
    }).catch(error => {
      console.log(error);
    });
    console.log('notifications count from getUnreadNotifications : ' + this.notifications.size);
    if (this.notifications.size === 0) {
      this.newsIsEmpty = true;
    }
  }


}
