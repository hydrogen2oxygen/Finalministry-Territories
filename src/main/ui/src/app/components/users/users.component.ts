import {Component, OnInit} from '@angular/core';
import {User} from "../../domain/User";
import {UserService} from "../../services/user.service";
import {SessionService} from "../../services/session.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UserDetailsComponent} from "./user-details/user-details.component";

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
    user.roles = "DEACTIVATED";
    this.userService.putUser(user);
  }

  deleteUser(user: User) {
    // TODO remove from DB
  }

  deactivatedUser(user: User) {
    return this.sessionService.hasRole('DEACTIVATED');
  }
}
