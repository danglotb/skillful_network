import { NgModule } from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

import { CardComponent } from './card/card.component';
import { MaterialModule } from '../modules/material/material.module';
import { ChipComponent } from './chip/chip.component';
import { ListComponent } from './list/list.component';
import { RouterModule } from '@angular/router';
import { UserPageComponent } from './user/user-page.component';
import { UserConfComponent } from './user/user-conf/user-conf.component';
import { FormCardComponent } from './form-card/form-card.component';

const COMPONENT_MODULES = [
    CardComponent,
    ChipComponent,
    ListComponent,
    UserPageComponent,
    UserConfComponent
]

const REACTIVE_FORM_DIRECTIVES = [
    FormsModule,
    ReactiveFormsModule
]

@NgModule({
    declarations: [
        COMPONENT_MODULES,
        FormCardComponent,
    ],
    exports: [
        COMPONENT_MODULES
    ],
    imports: [
        RouterModule,
        MaterialModule,
        CommonModule,
        REACTIVE_FORM_DIRECTIVES
    ]
})
export class ComponentModule { }