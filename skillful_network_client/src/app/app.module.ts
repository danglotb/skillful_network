
/**
 * Module principal de l'application
 * Pour rappel, un module permet de représenter un lot de fonctionnalités. Il va référencer les composants de votre applications
 * il aura également pour responsabilité d'appeler le composant racine (bootstrap component) qui va être à l'origine de tous les composants
 * Ici, on parle de "AppComponent"
 */
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './shared/modules/material/material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { TokenHttpInterceptorService } from './shared/interceptors/token-http-interceptor.service';
import { PasswordConfirmationComponent } from './password-confirmation/password-confirmation.component';
import { PasswordForgottenComponent } from './password-forgotten/password-forgotten.component';
import { DetailsUserComponent } from './home/lists/elements/user/details-user/details-user.component';
import { SubscriptComponent } from './home/lists/elements/user/subscript/subscript.component';
import { QualificationsComponent } from './home/lists/elements/user/qualifications/qualifications.component';
import { SkillsComponent } from './home/lists/elements/user/skills/skills.component';
import { FormationsAssocieesComponent } from './home/lists/elements/offre/formations-associees/formations-associees.component';
import { ExigencesComponent } from './home/lists/elements/offre/exigences/exigences.component';
import { CandidaterComponent } from './home/lists/elements/offre/candidater/candidater.component';
import { InformationsComponent } from './home/lists/elements/offre/informations/informations.component';
import { OffreComponent } from './home/lists/elements/offre/offre.component';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { getFrenchPaginatorIntl } from './shared/utils/customMatPaginationIntl';
import { MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { PageDataComponent } from './shared/utils/page-data/page-data.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { ExistingAccountDialog } from './login/existing-account-dialog/existing-account-dialog.component';
import { ComponentModule } from './shared/components/component.module';
import { HomeModule } from './home/home.module';

@NgModule({
    declarations: [ // Chaque composant que vous créez doit être déclaré ici
        AppComponent,
        LoginComponent,
        PasswordConfirmationComponent,
        PasswordForgottenComponent,
        DetailsUserComponent,
        SubscriptComponent,
        QualificationsComponent,
        SkillsComponent,
        FormationsAssocieesComponent,
        ExigencesComponent,
        CandidaterComponent,
        InformationsComponent,
        OffreComponent,
        PageDataComponent,
        ExistingAccountDialog,
    ],

    imports: [
        HomeModule,
        ComponentModule,
        BrowserModule,
        AppRoutingModule, // Toutes nos routes sont définies dans ce module
        BrowserAnimationsModule, // Nécessaire pour les animations Angular Material
        HttpClientModule, // Requit pour injecter la D.I. HttpClient qui nous permettra de requêter un serveur distant
        FormsModule, // Permet d'appliquer [(ngModel)] aux inputs
        ReactiveFormsModule, // Va nous permettre de créer des Model Driven Forms
        MaterialModule, // Ce module que nous avons créé contient l'ensemble des modules graphiques material à utiliser dans le projet
        FlexLayoutModule // Permet de positionner à l'aide des fxFlex, fxLayout, fxLayoutAlign etc.     
    ],

    entryComponents: [
        ExistingAccountDialog
    ],

    providers: [
        // Mise en place d'un intercepteur qui permettra d'appliquer le token automatiquement
        // à chaque requête sortante de notre application Angular
        // Le token correspond à notre "badge d'identité", il permet d'indiquer au serveur qui nous sommes
        // L'intercepteur va d'abord chercher à déterminer si le token est bien enregistré en localStorage ou non
        // Si oui, il l'envoie, sinon, il ne fait rien
        {
            provide: HTTP_INTERCEPTORS,
            useClass: TokenHttpInterceptorService,
            multi: true
        },
        { provide: MatPaginatorIntl, useValue: getFrenchPaginatorIntl() }, {
            provide: MAT_RADIO_DEFAULT_OPTIONS,
            useValue: { color: 'primary' },
        },
        MatDatepickerModule,
        MatNativeDateModule
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
