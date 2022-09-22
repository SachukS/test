import {Product} from "./product.model";

export class Shop {
  id: number | undefined;
  name: string | undefined;
  address: string | undefined;
  products: Product[] | undefined;
}
