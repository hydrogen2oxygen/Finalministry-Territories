import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {PasswordCheckService, PasswordCheckStrength} from "../../services/password-check.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.sass']
})
export class SettingsComponent implements OnInit {
  settingsForm: FormGroup;
  error: string;
  passwordStrength:PasswordCheckStrength;
  bothPasswordsEqual:boolean = false;

  constructor(private formBuilder:FormBuilder,
              private passwordCheckService:PasswordCheckService) { }

  ngOnInit() {
    this.settingsForm = this.formBuilder.group({
      password: '',
      password2: ''
    });
  }

  checkPasswordsEquals() {
    if (this.settingsForm.getRawValue().password == this.settingsForm.getRawValue().password2) {
      this.bothPasswordsEqual = true;
    } else {
      this.bothPasswordsEqual = false;
    }
  }

  checkPasswordStrength():PasswordCheckStrength {
    this.passwordStrength = this.passwordCheckService.checkPasswordStrength(this.settingsForm.getRawValue().password);
    console.log(this.passwordStrength)
    return this.passwordStrength;
  }

  saveSettings() {

    if (this.checkPasswordStrength() != PasswordCheckStrength.Strong) {
      this.error = 'Your password is not strong!';
      return;
    }

    console.log('Save password ...');
  }
}
