import { Component } from '@angular/core';
import {DeviceDetectorService} from "ngx-device-detector";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {

  title = 'Finalministry-Territories';
  mobileDevice:boolean = false;

  constructor(private deviceService: DeviceDetectorService) {
    this.mobileDevice = this.deviceService.isMobile();
  }
}
