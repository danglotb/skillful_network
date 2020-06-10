import { NgModule } from '@angular/core';

import { UsersListComponent } from './users-list/users-list.component';
import { JobOfferListComponent } from './job-offer-list/job-offer-list.component';
import { FormationListComponent } from './formation-list/formation-list.component';
import { ComponentModule } from 'src/app/shared/components/component.module';
import { MaterialModule } from 'src/app/shared/modules/material/material.module';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from 'src/app/app-routing.module';

const REACTIVE_FORM_DIRECTIVES = [
    FormsModule,
    ReactiveFormsModule
]

@NgModule({
    declarations: [
        UsersListComponent,
        JobOfferListComponent,
        FormationListComponent
    ],
    imports: [
        ComponentModule,
        MaterialModule,
        CommonModule,
        REACTIVE_FORM_DIRECTIVES,
        AppRoutingModule
    ]
})
export class ListsModule { }