import { NgModule } from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

import { CardComponent } from './card/card.component';
import { MaterialModule } from '../modules/material/material.module';
import { ChipComponent } from './chip/chip.component';
import { ListComponent } from './list/list.component';

const COMPONENT_MODULES = [
    CardComponent,
    ChipComponent,
    ListComponent,
]

const REACTIVE_FORM_DIRECTIVES = [
    FormsModule,
    ReactiveFormsModule
]

@NgModule({
    declarations: [
        COMPONENT_MODULES,
    ],
    exports: [
        COMPONENT_MODULES
    ],
    imports: [
        MaterialModule,
        CommonModule,
        REACTIVE_FORM_DIRECTIVES
    ]
})
export class ComponentModule { }