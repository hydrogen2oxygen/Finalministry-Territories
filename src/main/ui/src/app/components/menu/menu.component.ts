import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {SessionService} from "../../services/session.service";
import {BaseUrlUtility} from "../../utilities/BaseUrlUtility";
import {SESSION_STORAGE, StorageService} from "ngx-webstorage-service";
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

  constructor(private sessionService: SessionService,
              private http: HttpClient,
              private router: Router,
              @Inject(SESSION_STORAGE) private sessionStorage: StorageService
  ) {
  }

  ngOnInit() {
  }

  search() {

  }

  logout() {

    this.isMenuCollapsed = true;
    this.sessionService.logout().subscribe(() => {
      console.log("Logout successful!");
      this.router.navigateByUrl('/login');
    });
  }

  getUser() {
    return this.sessionService.getUser();
  }

  authenticated() {
    return this.sessionService.isAuthenticated();
  }

  collapseMenu() {
    this.isMenuCollapsed = true;
  }
}
