import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserFormComponent} from "./test/user-form/user-form.component";
import {ShopsComponent} from "./test/shops/shops.component";
import {ProductsComponent} from "./test/products/products.component";



const routes: Routes = [
  { path: 'register', component: UserFormComponent },
  { path: 'login', component: UserFormComponent },
  { path: 'shops', component: ShopsComponent },
  { path: 'shops/:id', component: ProductsComponent },
  { path: '', redirectTo: 'shops', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
