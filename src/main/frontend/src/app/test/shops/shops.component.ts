import {Component, OnInit} from '@angular/core';
import {TestService} from "../services/TestService";
import {Shop} from "../models/shop.model";
import {Router} from "@angular/router";
import {UserService} from "../services/UserService";

@Component({
  selector: 'app-shops',
  templateUrl: './shops.component.html',
  styleUrls: ['./shops.component.css']
})
export class ShopsComponent implements OnInit {
  shops: Shop[] = [];
  shopToSave: Shop = new Shop();
  role: string = localStorage.getItem('loginnedRole')!;

  constructor(private testService: TestService, public userService: UserService, private router: Router) {
  }

  ngOnInit() {

    this.loadShops();
  }

  onSubmit() {
    this.testService.saveShop(this.shopToSave).subscribe(result => this.gotoHome());

  }

  gotoHome() {
    this.router.navigate(['/']);
  }

  loadShops() {
    this.testService.getAllShops().subscribe(shops => this.shops = shops);
    this.role = localStorage.getItem('loginnedRole')!;
  }
}
