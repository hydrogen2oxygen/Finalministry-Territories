import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {PasswordCheckService, PasswordCheckStrength} from "../../services/password-check.service";
import {UserService} from "../../services/user.service";
import {SessionService} from "../../services/session.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.sass']
})
export class SettingsComponent implements OnInit {
  settingsPasswordForm: FormGroup;
  error: string;
  passwordStrength:PasswordCheckStrength;
  bothPasswordsEqual:boolean = false;

  constructor(
    private formBuilder:FormBuilder,
    private passwordCheckService:PasswordCheckService,
    private userService:UserService,
    private sessionService:SessionService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.settingsPasswordForm = this.formBuilder.group({
      password: '',
      password2: ''
    });
  }

  checkPasswordsEquals() {
    if (this.settingsPasswordForm.getRawValue().password == this.settingsPasswordForm.getRawValue().password2) {
      this.bothPasswordsEqual = true;
    } else {
      this.bothPasswordsEqual = false;
    }
  }

  checkPasswordStrength():PasswordCheckStrength {
    this.passwordStrength = this.passwordCheckService.checkPasswordStrength(this.settingsPasswordForm.getRawValue().password);
    console.log(this.passwordStrength)
    return this.passwordStrength;
  }

  savePassword() {

    if (!(this.checkPasswordStrength() == PasswordCheckStrength.Ok || this.checkPasswordStrength() == PasswordCheckStrength.Strong)) {
      this.error = 'Your password is not strong enough!';
      return;
    }

    console.log('Save password ...');
    this.userService.saveUserPassword(this.sessionService.getUser().name, this.settingsPasswordForm.getRawValue().password).subscribe(() => {
      console.log("OK password saved");
      this.sessionService.saveAuthorizationInSession(this.sessionService.getUser().name, this.settingsPasswordForm.getRawValue().password);
      this.settingsPasswordForm.reset();
      this.toastr.info('Password saved!','Settings info');
    });
  }
}
