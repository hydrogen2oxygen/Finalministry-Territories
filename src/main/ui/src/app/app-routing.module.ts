import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {MapComponent} from "./components/map/map.component";
import {SettingsComponent} from "./components/settings/settings.component";
import {TerritoriesComponent} from "./components/territories/territories.component";
import {LoginComponent} from "./components/session/login/login.component";

const routes: Routes = [
  {path: '', component: DashboardComponent},
  {path: 'home', component: DashboardComponent},
  {path: 'territories', component: TerritoriesComponent},
  {path: 'map', component: MapComponent},
  {path: 'settings', component: SettingsComponent},
  { path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
