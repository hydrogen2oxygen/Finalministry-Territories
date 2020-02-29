import { Component, OnInit } from '@angular/core';
import {User} from "../../domain/User";
import {UserService} from "../../services/user.service";
import {error} from "util";
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent implements OnInit {

  users:User[];

  constructor(private userService:UserService, private sessionService: SessionService) { }

  ngOnInit() {

    if (!this.sessionService.checkSessionActive()) return;

    this.userService.getAllUser().subscribe( users => {
      this.users = users;
    }, error => {
      console.error(error);
    });
  }

  editUser(user: User) {

  }
}
