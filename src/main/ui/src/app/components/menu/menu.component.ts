import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {SessionService} from "../../services/session.service";
import {take, finalize} from 'rxjs/operators';
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

  constructor(private app: SessionService, private http: HttpClient, private router: Router) {
    this.app.authenticate(undefined, undefined);
  }

  ngOnInit() {
  }

  search() {

  }

  logout() {
    this.http.post(this.serverUrl + '/logout', {}).subscribe(() => {
      this.app.authenticated = false;
      this.router.navigateByUrl('/login');
    });
  }
}
