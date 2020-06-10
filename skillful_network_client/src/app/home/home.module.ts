import { NgModule } from '@angular/core';

import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { MenuSideBarComponent } from './menu-side-bar/menu-side-bar.component';
import { FilActualitesComponent } from './fil-actualites/fil-actualites.component';

@NgModule({
    declarations: [
        HeaderComponent,
        FooterComponent,
        MenuSideBarComponent,
        FilActualitesComponent
    ],
})
export class HomeModule { }