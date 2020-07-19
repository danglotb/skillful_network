import { NotificationsComponent } from './notifications/notifications.component';
import { UsersListComponent } from './lists/users-list/users-list.component';
import { UserComponent } from './lists/elements/user/user.component';
import { ProfileConfComponent } from './profile-conf/profile-conf.component';
import { FormationListComponent } from './lists/formation-list/formation-list.component';
import { JobOfferListComponent } from './lists/job-offer-list/job-offer-list.component';
import { NewsFeedComponent } from './news-feed/news-feed.component';
import { JobComponent } from './lists/elements/job/job.component';

export const HOME_ROUTES = [
  { path: '', component: NewsFeedComponent },
  { path: 'profile', component: ProfileConfComponent },
  { path: 'users-list', component: UsersListComponent },
  { path: 'job-offer-list', component: JobOfferListComponent },
  { path: 'formation-list', component: FormationListComponent },
  { path: 'user/:id', component: UserComponent },
  { path: 'job/:id', component: JobComponent },
  { path: 'notifications', component: NotificationsComponent}
  // { path: 'training/:id', component: OffreComponent, data: { type: 'formation' } },
];