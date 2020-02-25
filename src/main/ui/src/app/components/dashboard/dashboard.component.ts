import { Component, OnInit } from '@angular/core';
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.sass']
})
export class DashboardComponent implements OnInit {

  constructor(private sessionService: SessionService) { }

  ngOnInit() {
  }

  authenticated() {
    return this.sessionService.authenticated;
  }
}
