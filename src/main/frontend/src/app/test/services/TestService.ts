import {HttpClient} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {Shop} from "../models/shop.model";
import {map, Observable} from "rxjs";
import {Product} from "../models/product.model";
import {catchError} from "rxjs/operators";
import {ErrorHandlerService} from "./error-handler.service";

@Injectable()
export class TestService {
  private shopsUrl = "/api/v1/shops";

  constructor(private http: HttpClient, private errorHandlerService: ErrorHandlerService) {
  }

  getAllShops(): Observable<Shop[]> {
    return this.http.get(this.shopsUrl)
      .pipe(map(response => {
        if (response) {
          return Object.values(response);
        }
        return [];
      })).pipe(catchError(this.errorHandlerService.handleError));
  }

  getProducts(id: String): Observable<Product[]> {
    return this.http.get(this.shopsUrl + '/' + id + '/products')
      .pipe(map(response => {
        if (response) {
          return Object.values(response);
        }
        return [];
      }));
  }

  saveShop(shop: Shop) {
    return this.http.post(this.shopsUrl + '/add', shop, {responseType: 'text'}).pipe(catchError(this.errorHandlerService.handleError));
  }

  saveProduct(product: Product, id: String) {
    return this.http.post(this.shopsUrl + '/' + id + '/products/add', product, {responseType: 'text'}).pipe(catchError(this.errorHandlerService.handleError));
  }

}
