import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {SessionService} from "../../../services/session.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  error: boolean = false;
  public loginForm: FormGroup;

  constructor(
      private app: SessionService,
      private formbuilder: FormBuilder,
      private http: HttpClient,
      private router: Router) { }

  ngOnInit() {
    this.loginForm = this.formbuilder.group({
      username: '',
      password: ''
    });
  }

  login() {
    this.app.authenticate(this.loginForm.getRawValue().username, this.loginForm.getRawValue().password, () => {
      this.router.navigateByUrl('/');
    });
    return false;
  }
}
