package com.factures.Controllers;

import com.factures.Service.ProductsService;
import com.factures.dto.request.CreateProductRequest;
import com.factures.dto.request.UpdateProductRequest;
import com.factures.dto.response.ProductResponse;
import com.factures.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService){
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.ok(productsService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productsService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest productRequest){
        ProductResponse theProduct = productsService.createProduct(productRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" +
                        "{id}")
                .buildAndExpand(theProduct.id())
                .toUri();
        return ResponseEntity.created(location).body(theProduct);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable long id, @RequestBody UpdateProductRequest request){
        return ResponseEntity.ok( productsService.updateProduct(request, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        productsService.deleteProduct(id);
    }

}
