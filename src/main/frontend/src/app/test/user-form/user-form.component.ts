import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../services/UserService';
import {User} from '../models/user.model';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {

  user: User = new User();

  constructor(private userService: UserService, private router: Router, public route: ActivatedRoute) {
    console.log(this.route.snapshot.routeConfig?.path);
  }

  onSubmit() {
    if (this.route.snapshot.routeConfig?.path === 'register')
      this.userService.register(this.user).subscribe(result => this.gotoLogin());
    else {

      this.userService.login(this.user).subscribe(result => {
        console.log(JSON.parse(result));
        this.userService.loginnedUser = JSON.parse(result);
        localStorage.setItem('loginnedRole', this.userService.loginnedUser.roles.pop()!)
        this.gotoHome()
      });

    }

  }

  gotoLogin() {
    this.router.navigate(['/login']);
  }

  gotoHome() {
    this.router.navigate(['/']);
  }
}
