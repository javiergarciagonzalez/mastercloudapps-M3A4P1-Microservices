package io.eventuate.examples.tram.sagas.ordersandcustomers.products.web;

import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.Product;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductRepository;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.service.ProductService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.CreateProductRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.CreateProductResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.GetProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

  private ProductService productService;
  private ProductRepository productRepository;

  @Autowired
  public ProductController(ProductService productService, ProductRepository productRepository) {
    this.productService = productService;
    this.productRepository = productRepository;
  }

  @RequestMapping(value = "/products", method = RequestMethod.POST)
  public CreateProductResponse createProduct(@RequestBody CreateProductRequest createProductRequest) {
    Product product = productService.createProduct(createProductRequest.getName(), createProductRequest.getCreditLimit());
    return new CreateProductResponse(product.getId());
  }

  @RequestMapping(value="/products/{productId}", method= RequestMethod.GET)
  public ResponseEntity<GetProductResponse> getProduct(@PathVariable Long productId) {
    return productRepository
            .findById(productId)
            .map(c -> new ResponseEntity<>(new GetProductResponse(c.getId(), c.getName(), c.getCreditLimit()), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
