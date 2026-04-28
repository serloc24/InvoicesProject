package com.factures.Service;

import com.factures.Repository.ProductsRepository;
import com.factures.dto.mapper.ProductMapper;
import com.factures.dto.request.CreateProductRequest;
import com.factures.dto.request.UpdateProductRequest;
import com.factures.dto.response.ProductResponse;
import com.factures.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private final ProductMapper productMapper;
    private final ProductsRepository productsRepository;

    public ProductsService(ProductMapper productMapper, ProductsRepository productsRepository) {
        this.productMapper = productMapper;
        this.productsRepository = productsRepository;
    }

    public ProductResponse createProduct(CreateProductRequest request){
        Product theProduct = productMapper.createToEntity(request);
        Product saved = productsRepository.save(theProduct);
        return productMapper.entityToDTO(saved);
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> allProducts = productsRepository.findAll();
        return productMapper.entitiesToDTOList(allProducts);
    }

    public ProductResponse getProductById(Long id){
        Product theProduct = productsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("This ProductId does not exist"));
        return productMapper.entityToDTO(theProduct);
    }

    public List<ProductResponse> findByDescriptionContaining(String description){
        List<Product> productsWithDescription = productsRepository.findByDescriptionContainingIgnoreCase(description);
        return productMapper.entitiesToDTOList(productsWithDescription);
    }

    public ProductResponse updateProduct(UpdateProductRequest request, long id){
        if(!productsRepository.existsById(id)){
            throw new IllegalArgumentException("We can't update this productId");
        }
        Product updatedProduct = productMapper.updateToEntity(request);
        updatedProduct = productsRepository.save(updatedProduct);
        return productMapper.entityToDTO(updatedProduct);
    }

    public void deleteProduct(Long id){
        Product productToDelete = productsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Can't delete this productId"));
        productsRepository.delete(productToDelete);
    }
}
