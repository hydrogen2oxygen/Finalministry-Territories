import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {SessionService} from "../../services/session.service";
import {environment} from "../../../environments/environment";
import {BaseUrlUtility} from "../../utilities/BaseUrlUtility";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {

  isMenuCollapsed: boolean = true;
  serverUrl:string = `${BaseUrlUtility.getBaseUrl()}:${environment.serverPort}`;

  constructor(private sessionService: SessionService, private http: HttpClient, private router: Router) {
  }

  ngOnInit() {
  }

  search() {

  }

  logout() {

    this.sessionService.authenticated = false;

    this.http.post(this.serverUrl + '/logout', {}).subscribe(() => {
      console.log("Logout successful!");
      this.router.navigateByUrl('/login');
    });
  }

  getUser() {
    return this.sessionService.user;
  }

  authenticated() {
    return this.sessionService.authenticated;
  }
}
