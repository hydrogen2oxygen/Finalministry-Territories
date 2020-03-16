import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BaseUrlUtility} from "../utilities/BaseUrlUtility";
import {SESSION_STORAGE, StorageService} from "ngx-webstorage-service";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {User} from "../domain/User";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private authenticated = false;
  private user:any = null;
  private roles:string;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(SESSION_STORAGE) private sessionStorage: StorageService
  ) {
  }

  isAuthenticated():boolean {

    if (!this.authenticated) {
      // check session storage
      if (this.sessionStorage.get("authorization") != null) {
        this.authenticated = true;
      }
    }

    return this.authenticated;
  }

  authenticate(username, password, callback) {

    let authorization = this.saveAuthorizationInSession(username, password);

    const headers = new HttpHeaders( {
      authorization: authorization
    });

    this.http.get(BaseUrlUtility.getBaseUrl() + '/user/authentication', {headers: headers}).subscribe(user => {

      if (user['name']) {
        this.authenticated = true;
        this.user = user;
        this.sessionStorage.set("user",user);
        console.log(user);

        this.http.get<User>(BaseUrlUtility.getBaseUrl() + '/user/current', {headers: headers}).subscribe(user => {
          console.log(user);
          this.roles = user.roles;
          this.sessionStorage.set("roles", this.roles);
        });
      } else {
        this.authenticated = false;
        this.user = null;
        this.sessionStorage.clear();
      }

      return callback && callback();
    }, error => {
      console.log(error);
      this.authenticated = false;
      this.user = null;
      this.sessionStorage.clear();
      return callback && callback();
    });
  }

  saveAuthorizationInSession(username, password) {

    let authorization = 'Basic ' + btoa(username + ':' + password);

    this.sessionStorage.set("authorization", authorization);

    return authorization;
  }

  getAuthorizationHeaders():HttpHeaders {

    let authorization = this.sessionStorage.get("authorization");

    const headers = new HttpHeaders( {
      authorization: authorization
    });

    return headers;
  }

  hasRole(roleName:string):boolean {

    // No need to ask farther if you are not authenticated
    if (!this.isAuthenticated()) return false;

    if (this.roles == null) {
      this.roles = this.sessionStorage.get("roles");
    }

    if (this.roles == null) return false;

    return this.roles.includes(roleName);
  }

  getUser():any {

    if (this.user == null) {
      this.user = this.sessionStorage.get("user");
    }

    return this.user;
  }

  logout():Observable<void> {

    this.user = false;
    this.authenticated = false;
    this.sessionStorage.clear();

    return this.http.post<void>(BaseUrlUtility.getBaseUrl() + '/logout', {});
  }

  /**
   * Navigate to the login page if the user was logged out
   */
  checkSessionActive():boolean {
    if(!this.isAuthenticated()) {
      this.router.navigateByUrl('/login');
      return false;
    }

    return true;
  }
}
