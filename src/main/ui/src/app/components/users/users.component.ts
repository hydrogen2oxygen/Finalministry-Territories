import {Component, OnInit} from '@angular/core';
import {User} from "../../domain/User";
import {UserService} from "../../services/user.service";
import {SessionService} from "../../services/session.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UserDetailsComponent} from "./user-details/user-details.component";
import {NewUserComponent} from "./new-user/new-user.component";
import {UserRoles} from "../../enums/UserRoles";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent implements OnInit {

  users:User[];

  constructor(
    private userService:UserService,
    private sessionService: SessionService,
    private modalService: NgbModal
  ) { }

  ngOnInit() {

    if (!this.sessionService.checkSessionActive()) return;

    this.reloadUsers();
  }

  reloadUsers() {
    this.userService.getAllUser().subscribe(users => {
      this.users = users;
    }, error => {
      console.error(error);
    });
  }

  editUser(user: User) {
    const modalRef = this.modalService.open(UserDetailsComponent);
    modalRef.componentInstance.user = user;

    let that = this;

    modalRef.componentInstance.callbackFunction = function () {
      that.reloadUsers();
    };
  }

  deactivateUser(user: User) {
    user.roles = UserRoles.DEACTIVATED.toString();
    this.updateUser(user);
  }

  deleteUser(user: User) {
    this.userService.deleteUser(user).subscribe(result => {
      this.reloadUsers();
    });
  }

  deactivatedUser(user: User) {

    if (user.roles == null) return false;
    return user.roles.includes(UserRoles.DEACTIVATED.toString());
  }

  reactivateUser(user: User) {
    user.roles = UserRoles.PREACHER.toString();
    this.userService.putUser(user).subscribe( result =>{});
  }

  addNewUser() {
    const modalRef = this.modalService.open(NewUserComponent);
    let that = this;
    modalRef.componentInstance.callbackFunction = function () {
      that.reloadUsers();
      modalRef.close();
    };
  }

  private updateUser(user: User) {
    this.userService.putUser(user).subscribe(result => {
    });
  }
}
