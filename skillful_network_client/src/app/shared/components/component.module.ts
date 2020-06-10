import { NgModule } from '@angular/core';

import { CardComponent } from './card/card.component';
import { MaterialModule } from '../modules/material/material.module';

@NgModule({
    declarations: [
        CardComponent
    ],
    imports: [
        MaterialModule
    ]
})
export class ComponentModule {}