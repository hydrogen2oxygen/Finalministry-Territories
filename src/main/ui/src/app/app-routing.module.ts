import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {MapComponent} from "./components/map/map.component";
import {SettingsComponent} from "./components/settings/settings.component";
import {TerritoriesComponent} from "./components/territories/territories.component";
import {LoginComponent} from "./components/session/login/login.component";
import {ResetPasswordComponent} from "./components/session/reset-password/reset-password.component";
import {RegisterNewUserComponent} from "./components/session/register-new-user/register-new-user.component";

const routes: Routes = [
  {path: '', component: DashboardComponent},
  {path: 'home', component: DashboardComponent},
  {path: 'territories', component: TerritoriesComponent},
  {path: 'map', component: MapComponent},
  {path: 'settings', component: SettingsComponent},
  {path: 'login', component: LoginComponent},
  {path: 'resetPassword', component: ResetPasswordComponent},
  {path: 'registerNewUser', component: RegisterNewUserComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
