import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../shared/services/token-storage.service';
import { FollowStateTrackerService } from 'src/app/shared/services/FollowStateTracker.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private tokenStorage: TokenStorageService, private fstService: FollowStateTrackerService) { }

  public countNotifications: number;
  public hiddenBadgeNotifications: boolean;

  ngOnInit(): void {
    this.hiddenBadgeNotifications = true;
    this.fstService.unreadNotificationsCount()
    .then(response => {
      console.log('Unread notifications : ' + response);
      this.countNotifications = response;
      if (this.countNotifications !== 0) {
        this.hiddenBadgeNotifications = false;
      }
    })
    .catch(error => {
      console.log(error);
    });
  }

  onSearch() {
    console.log("works");
  }

  logout() {
    this.tokenStorage.signOut();
  }


}
