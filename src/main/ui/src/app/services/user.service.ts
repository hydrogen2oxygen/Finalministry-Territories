import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../domain/User";
import {BaseUrlUtility} from "../utilities/BaseUrlUtility";
import {SessionService} from "./session.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private sessionService: SessionService) { }

  getAllUser():Observable<User[]>{
    return this.http.get<User[]>(BaseUrlUtility.getBaseUrl() + "/user", {headers: this.sessionService.getAuthorizationHeaders()});
  }

  saveUserPassword(userName:string, password:string):Observable<void> {
    return this.http.put<void>(BaseUrlUtility.getBaseUrl() + "/user/" + encodeURIComponent(userName) + "/password/" + encodeURIComponent(password), null, {headers: this.sessionService.getAuthorizationHeaders()});
  }

  putUser(user: User):Observable<void> {
    return this.http.put<void>(BaseUrlUtility.getBaseUrl() + "/user/", user, {headers: this.sessionService.getAuthorizationHeaders()});
  }
}
