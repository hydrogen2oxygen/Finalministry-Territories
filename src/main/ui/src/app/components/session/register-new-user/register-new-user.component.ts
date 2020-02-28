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
  registered: boolean =false;
  error: string;

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

    this.error = null;
    this.registered = false;

    this.http.post(BaseUrlUtility.getBaseUrl() + "/userRegistration", form).subscribe(
      result => {
        this.registered = true;
        console.log(result);
      },
      error => {
        console.error(error);
        this.error = error.error;
      }
    );
  }
}
