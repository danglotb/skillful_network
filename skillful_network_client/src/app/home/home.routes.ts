import { UsersListComponent } from './lists/users-list/users-list.component';
import { UserComponent } from './lists/elements/user/user.component';
import { ProfileConfComponent } from './profile-conf/profile-conf.component';
import { OffreComponent } from './lists/elements/offre/offre.component';
import { FormationListComponent } from './lists/formation-list/formation-list.component';
import { JobOfferListComponent } from './lists/job-offer-list/job-offer-list.component';
import { FilActualitesComponent } from './fil-actualites/fil-actualites.component';

export const HOME_ROUTES = [
  { path: '', component: FilActualitesComponent },
  { path: 'profile', component: ProfileConfComponent },
  { path: 'users-list', component: UsersListComponent },
  { path: 'job-offer-list', component: JobOfferListComponent },
  { path: 'formation-list', component: FormationListComponent },
  { path: 'user/:id', component: UserComponent },
  { path: 'job/:id', component: OffreComponent, data: { type: 'emploi' } },
  { path: 'training/:id', component: OffreComponent, data: { type: 'formation' } },
  
];
