import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BaseUrlUtility} from "../utilities/BaseUrlUtility";
import {SESSION_STORAGE, StorageService} from "ngx-webstorage-service";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  authenticated = false;
  user:any = null;

  constructor(
    private http: HttpClient,
    @Inject(SESSION_STORAGE) private sessionStorage: StorageService
  ) {
  }

  authenticate(username, password, callback) {

    let authorization = 'Basic ' + btoa(username + ':' + password);

    this.sessionStorage.set("authorization",authorization);

    const headers = new HttpHeaders( {
      authorization: authorization
    });

    this.http.get(BaseUrlUtility.getBaseUrl() + '/user/authentication', {headers: headers}).subscribe(user => {

      if (user['name']) {
        this.authenticated = true;
        this.user = user;
        console.log(user);
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

  getAuthorizationHeaders():HttpHeaders {

    let authorization = this.sessionStorage.get("authorization");

    const headers = new HttpHeaders( {
      authorization: authorization
    });

    return headers;
  }
}
