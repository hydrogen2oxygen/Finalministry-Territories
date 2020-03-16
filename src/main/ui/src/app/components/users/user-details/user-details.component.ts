import {Component, OnInit} from '@angular/core';
import {User} from "../../../domain/User";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {UserService} from "../../../services/user.service";
import {UserUtility} from "../../../utilities/UserUtility";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.sass']
})
export class UserDetailsComponent implements OnInit {

  callbackFunction:() => void;
  user: User;
  selectedRole:string;
  roles: string[] = ["PREACHER","MAP-DESIGNER","TERRITORY-MANAGER","ADMIN","DEACTIVATED"];

  constructor(
    public activeModal: NgbActiveModal,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.selectedRole = this.roles[0];
  }

  assignRole() {
    this.user.roles = UserUtility.addRole(this.user.roles, this.selectedRole);
    this.persistUser();
  }

  hasRoleAssigned():boolean {
    return this.user.roles.indexOf(this.selectedRole) >= 0;
  }

  setSelectedRole($event: Event) {
    let selectElement = (<HTMLSelectElement>$event.target);
    let text = selectElement.options[selectElement.selectedIndex].text;
    this.selectedRole = text;
  }

  removeRole() {
    console.log("Remove role ", this.selectedRole);
    this.user.roles = UserUtility.removeRole(this.user.roles, this.selectedRole);
    this.persistUser();
  }

  private persistUser() {
    this.userService.putUser(this.user).subscribe(() => {
      this.callbackFunction();
    });
  }
}
