import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { TestComponent } from './test/test.component';
import { ProductsComponent } from './test/products/products.component';
import { ShopsComponent } from './test/shops/shops.component';
import {UserFormComponent} from "./test/user-form/user-form.component";
import {FormsModule} from "@angular/forms";

import {HttpClientModule} from "@angular/common/http";
import {UserService} from "./test/services/UserService";
import {AppRoutingModule} from "./app-routing.module";
import {TestService} from "./test/services/TestService";
import {ErrorHandlerService} from "./test/services/error-handler.service";
import { AddFormComponent } from './test/add-form/add-form.component';


@NgModule({
  declarations: [
    AppComponent,
    TestComponent,
    ProductsComponent,
    ShopsComponent,
    UserFormComponent,
    AddFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [TestService, UserService, ErrorHandlerService, UserFormComponent, ShopsComponent, ProductsComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
