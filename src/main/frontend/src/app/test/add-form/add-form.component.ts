import {Component, OnInit} from '@angular/core';
import {Shop} from "../models/shop.model";
import {TestService} from "../services/TestService";
import {ActivatedRoute} from "@angular/router";
import {ShopsComponent} from "../shops/shops.component";
import {Product} from "../models/product.model";
import {ProductsComponent} from "../products/products.component";

@Component({
  selector: 'app-add-form',
  templateUrl: './add-form.component.html',
  styleUrls: ['./add-form.component.css']
})
export class AddFormComponent implements OnInit {
  shopToSave: Shop = new Shop();
  productToSave: Product = new Product();

  constructor(private testService: TestService, public shopComponent: ShopsComponent, private productComponent: ProductsComponent, public route: ActivatedRoute) {
  }

  ngOnInit(): void {
  }

  onShop() {
    this.testService.saveShop(this.shopToSave).subscribe(result => {
      alert(result)
      this.shopComponent.loadShops()
    });
  }

  onProduct() {
    let id = this.route.snapshot.paramMap.get('id')!;
    this.testService.saveProduct(this.productToSave, id).subscribe(result => {
      alert(result)
      this.productComponent.loadProducts(id)
    });
  }

}
