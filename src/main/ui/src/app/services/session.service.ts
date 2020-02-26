import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BaseUrlUtility} from "../utilities/BaseUrlUtility";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  authenticated = false;
  user:any = null;

  constructor(private http: HttpClient) {
  }

  authenticate(username, password, callback) {

    const headers = new HttpHeaders( {
      authorization: 'Basic ' + btoa(username + ':' + password)
    });

    this.http.get(BaseUrlUtility.getBaseUrl() + '/authentication', {headers: headers}).subscribe(user => {

      if (user['name']) {
        this.authenticated = true;
        this.user = user;
        console.log(user);
        localStorage.setItem("userObject", JSON.stringify(this.user));
      } else {
        this.authenticated = false;
        this.user = null;
        localStorage.removeItem("userObject");
      }

      return callback && callback();
    }, error => {
      console.log(error);
      this.authenticated = false;
      this.user = null;
      return callback && callback();
    });

  }
}
