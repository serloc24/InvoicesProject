package com.factures.Service;

import com.factures.Repository.ProductsRepository;
import com.factures.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    public Product createProduct(Product product){
        return productsRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productsRepository.findAll();
    }

    public Product getProductById(Long id){
        return productsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("This ProductId does not exist"));
    }

    public List<Product> findByDescriptionContaining(String description){
        return productsRepository.findByDescriptionContainingIgnoreCase(description);
    }

    public Product updateProduct(Product product){
        if(productsRepository.existsById(product.getId())){
            return productsRepository.save(product);
        }
        throw new IllegalArgumentException("We can't update this productId");
    }

    public void deleteProduct(Long id){
        Product productToDelete = productsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Can't delete this productId"));
        productsRepository.delete(productToDelete);
    }
}
