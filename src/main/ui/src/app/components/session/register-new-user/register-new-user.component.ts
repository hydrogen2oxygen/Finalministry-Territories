import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {BaseUrlUtility} from "../../../utilities/BaseUrlUtility";

@Component({
  selector: 'app-register-new-user',
  templateUrl: './register-new-user.component.html',
  styleUrls: ['./register-new-user.component.sass']
})
export class RegisterNewUserComponent implements OnInit {

  registerUserForm: FormGroup;

  constructor(private formbuilder: FormBuilder,
              private http: HttpClient) {
  }

  ngOnInit() {
    this.registerUserForm = this.formbuilder.group({
      email: '',
      userName: '',
      firstName: '',
      lastName: '',
      congregation: ''
    });
  }

  registerNewUser() {
    let form: any = this.registerUserForm.getRawValue();
    console.log(form);
    this.http.post(BaseUrlUtility.getBaseUrl() + "/userRegistration", form).subscribe(
      result => {
        console.log(result);
      },
      error => {
        console.error(error);
      }
    );
  }
}
