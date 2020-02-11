import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent implements OnInit {

  isMenuCollapsed:boolean=true;

  constructor() { }

  ngOnInit() {
  }

  search() {

  }
}
