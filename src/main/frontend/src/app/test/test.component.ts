import {Component, OnInit, ViewChild} from '@angular/core';
import {ShopsComponent} from "./shops/shops.component";
import {UserFormComponent} from "./user-form/user-form.component";
import {AddFormComponent} from "./add-form/add-form.component";
import {Router} from "@angular/router";
import {UserService} from "./services/UserService";
import {User} from "./models/user.model";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  constructor(private router: Router, public userService: UserService) {

  }

  @ViewChild('userC')
  userC: UserFormComponent | undefined;

  ngOnInit(): void {

    if (!!localStorage.getItem('loginnedUser')) {
      window.location.href = '/login'
    }
  }

  public collapsed = true;

  @ViewChild('shopsC')
  shopsC: ShopsComponent | undefined;

  @ViewChild('addC')
  addC: AddFormComponent | undefined;

  toggleCollapsed(): void {
    this.collapsed = !this.collapsed;
  }

  logout(): void {
    this.userService.logout().subscribe(result => {
      this.userService.loginnedUser = new User();
      localStorage.removeItem('loginnedRole');
      window.location.href = '/login';
    });
  }

}
