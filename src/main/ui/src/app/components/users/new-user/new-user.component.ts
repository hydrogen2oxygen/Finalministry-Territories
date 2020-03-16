import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {UserService} from "../../../services/user.service";
import {User} from "../../../domain/User";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.sass']
})
export class NewUserComponent implements OnInit {

  callbackFunction: () => void;
  userForm: FormGroup;
  formValid: boolean;
  errorMessage: string[] = [];

  constructor(
    private formBuilder: FormBuilder,
    public activeModal: NgbActiveModal,
    private userService: UserService,
    private toastr: ToastrService
  ) {
  }

  ngOnInit() {
    this.userForm = this.formBuilder.group({
      email: '',
      userName: '',
      firstName: '',
      lastName: '',
      password: ''
    });

    this.userForm.valueChanges.subscribe(x => {
      this.validateForm()
    })
  }

  saveUser() {

    let user = this.getUserFromForm();

    this.userService.postUser(user).subscribe(() => {
      this.callbackFunction();
    });
  }

  private getUserFromForm() {
    let user: User = new User();
    let form = this.userForm.getRawValue();

    ((u) => {
      u.email = form.email;
      u.userName = form.userName;
      u.firstName = form.firstName;
      u.lastName = form.lastName;
      u.roles = 'PREACHER';
      u.password = form.password;
    })(user);

    return user;
  }

  validateForm() {

    let f = this.userForm.getRawValue();
    this.errorMessage = [];

    if (f.email.length < 5) this.errorMessage.push("Email missing");
    if (f.userName.length < 1) this.errorMessage.push("Username missing");
    if (f.firstName.length < 1) this.errorMessage.push("Firstname missing");
    if (f.lastName.length < 1) this.errorMessage.push("Lastname missing");
    if (f.password.length < 7) this.errorMessage.push("Password not long enough");

    if (this.errorMessage.length > 0) {
      this.formValid = false;
    } else {

      this.formValid = true;
      let user = this.getUserFromForm();

      this.userService.validateUser(user).subscribe(() => {
        // all fine
      },error => {
        this.errorMessage.push(error.error);
        this.formValid = false;
      });
    }
  }
}
