import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {MapComponent} from "./components/map/map.component";
import {SettingsComponent} from "./components/settings/settings.component";
import {TerritoriesComponent} from "./components/territories/territories.component";

const routes: Routes = [
  {path: '', component: DashboardComponent},
  {path: 'territories', component: TerritoriesComponent},
  {path: 'map', component: MapComponent},
  {path: 'settings', component: SettingsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
