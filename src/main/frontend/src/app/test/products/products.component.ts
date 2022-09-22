import {Component, OnInit} from '@angular/core';
import {TestService} from "../services/TestService";
import {Product} from "../models/product.model";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../services/UserService";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  id: string = '';

  constructor(private testService: TestService, private route: ActivatedRoute, public userService: UserService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.loadProducts(this.id);
  }


  loadProducts(id: string) {
    this.testService.getProducts(id).subscribe(products => this.products = products);
  }

}
