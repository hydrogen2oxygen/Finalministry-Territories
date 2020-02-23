import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {SessionService} from "../../../services/session.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

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
      private router: Router,
      private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.loginForm = this.formbuilder.group({
      username: '',
      password: ''
    });
  }

  login() {
    this.app.authenticate(this.loginForm.getRawValue().username, this.loginForm.getRawValue().password, () => {
      if (this.app.authenticated) {
        this.router.navigateByUrl('/');
      } else {
        this.toastr.error("Authentification failed!","Login Error");
      }
    });
    return false;
  }
}
