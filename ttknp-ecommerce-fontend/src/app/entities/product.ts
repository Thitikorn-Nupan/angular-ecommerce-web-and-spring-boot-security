export class Product {
  pid: number
  sku: String
  name: String
  description: String
  price: number
  imageUrl: String
  unitInStock: number

  constructor(pid: number, sku: String, name: String, description: String, price: number, imageUrl: String, unitInStock: number) {
    this.pid = pid;
    this.sku = sku;
    this.name = name;
    this.description = description;
    this.price = price;
    this.imageUrl = imageUrl;
    this.unitInStock = unitInStock;
  }
}
