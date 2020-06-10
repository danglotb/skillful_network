import { NgModule } from '@angular/core';

import { UserConfComponent } from './user-conf/user-conf.component';
import { JobApplicationsList } from './job-applications-list/job-applications-list.component';
import { NewPasswordComponent } from './new-password/new-password.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ComponentModule } from 'src/app/shared/components/component.module';
import { MaterialModule } from 'src/app/shared/modules/material/material.module';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { ProfileConfComponent } from './profile-conf.component';

const PROFILE_CONF_COMPONENTS = [ 
    ProfileConfComponent,
    UserConfComponent,
    JobApplicationsList,
    NewPasswordComponent
]

const REACTIVE_FORM_DIRECTIVES = [
    FormsModule,
    ReactiveFormsModule
]

@NgModule({
    declarations: [
        PROFILE_CONF_COMPONENTS
    ],
    exports: [
        PROFILE_CONF_COMPONENTS
    ],
    imports: [
        ComponentModule,
        MaterialModule,
        CommonModule,
        REACTIVE_FORM_DIRECTIVES,
        AppRoutingModule
    ]
})
export class ProfileConfModule { }