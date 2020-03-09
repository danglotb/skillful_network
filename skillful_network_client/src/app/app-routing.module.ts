/**
 * Ce module déclare l'ensemble des routes relatives à notre projet
 */

import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {HOME_ROUTES} from './home/home.routes';
import {IsLoggedGuard} from './shared/guards/is-logged.guard';

const routes: Routes = [
    {
        path: '',
        component: LoginComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'home',
        component: HomeComponent,
        children: HOME_ROUTES,
        // Pour accéder à cette route, Angular va appeler la méthode canActivate de "isLoggedGuard"
        // Si cette méthode renvoie true, alors le user sera autorisé à rentrer, sinon non.
        canActivate: [IsLoggedGuard]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
