import { NgModule } from '@angular/core';

import { UsersListComponent } from './users-list/users-list.component';
import { JobOfferListComponent } from './job-offer-list/job-offer-list.component';
import { FormationListComponent } from './formation-list/formation-list.component';

@NgModule({
    declarations: [
        UsersListComponent,
        JobOfferListComponent,
        FormationListComponent
    ],
})
export class ListsModule { }