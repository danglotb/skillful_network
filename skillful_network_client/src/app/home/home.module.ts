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

@NgModule({
    declarations: [
        HomeComponent,
        HeaderComponent,
        MenuSideBarComponent,
        NewsFeedComponent,
        ProfilePictureUploader,
        ProfileConfComponent
    ],
    imports: [
        CommonModule,
        ListsModule,
        ComponentModule,
        MaterialModule,
        AppRoutingModule
    ]
})
export class HomeModule { }