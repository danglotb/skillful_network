import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { HeaderComponent } from './header/header.component';
import { MenuSideBarComponent } from './menu-side-bar/menu-side-bar.component';
import { NewsFeedComponent } from './news-feed/news-feed.component';
import { ListsModule } from './lists/list.module';
import { ComponentModule } from '../shared/components/component.module';
import { MaterialModule } from '../shared/modules/material/material.module';
import { AppRoutingModule } from '../app-routing.module';
import { HomeComponent } from './home.component';
import { ProfilePictureUploader } from './profile-picture-uploader/profile-picture-uploader';
import { ProfileConfComponent } from './profile-conf/profile-conf.component';
import { CommonModule } from '@angular/common';
import { DialogueElementsComponent } from './news-feed/dialogue-elements/dialogue-elements.component';
import { AddPublicationComponent } from './news-feed/add-publication/add-publication.component';
import { ListPublicationsComponent } from './news-feed/list-publications/list-publications.component';
import { PublicationComponent } from './news-feed/list-publications/publication/publication.component';

@NgModule({
    declarations: [
        HomeComponent,
        HeaderComponent,
        MenuSideBarComponent,
        NewsFeedComponent,
        ProfilePictureUploader,
        ProfileConfComponent,
        DialogueElementsComponent,
        AddPublicationComponent,
        ListPublicationsComponent,
        PublicationComponent
    ],
    imports: [
        CommonModule,
        ListsModule,
        ComponentModule,
        FormsModule, // Permet d'appliquer [(ngModel)] aux inputs
        ReactiveFormsModule,
        MaterialModule,
        AppRoutingModule
    ]
})
export class HomeModule { }