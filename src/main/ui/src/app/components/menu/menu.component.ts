import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {SessionService} from "../../services/session.service";
import {BaseUrlUtility} from "../../utilities/BaseUrlUtility";
import {
  faBook,
  faBuilding,
  faCog,
  faMapMarkedAlt,
  faSignInAlt,
  faSignOutAlt,
  faTools,
  faUser,
  faUsers
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {

  // Icons ... but why do I need to declare them all ... is there not a better way??? Tell me on Github!
  faUser = faUser;
  faUsers = faUsers;
  faSignInAlt = faSignInAlt;
  faSignOutAlt = faSignOutAlt;
  faTools = faTools;
  faMapMarkerAlt = faMapMarkedAlt;
  faCog = faCog;
  faBuilding = faBuilding;
  faBook = faBook;

  isMenuCollapsed: boolean = true;
  serverUrl:string = `${BaseUrlUtility.getBaseUrl()}}`;

  constructor(private sessionService: SessionService, private http: HttpClient, private router: Router) {
  }

  ngOnInit() {
  }

  search() {

  }

  logout() {

    this.isMenuCollapsed = true;
    this.sessionService.authenticated = false;
    localStorage.removeItem("userObject");

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

  collapseMenu() {
    this.isMenuCollapsed = true;
  }
}
