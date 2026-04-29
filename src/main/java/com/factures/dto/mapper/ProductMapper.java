package com.factures.dto.mapper;

import com.factures.dto.request.CreateProductRequest;
import com.factures.dto.request.UpdateProductRequest;
import com.factures.dto.response.ProductResponse;
import com.factures.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public Product createToEntity(CreateProductRequest request){
        return new Product(request.description(),request.unitPrice(), request.taxes());
    }

    public Product updateToEntity(UpdateProductRequest update, Product existingProduct){
        existingProduct.setDescription(update.description());
        existingProduct.setUnitPrice(update.unitPrice());
        existingProduct.setTaxes(update.taxes());
        return existingProduct;
    }

    public ProductResponse entityToDTO(Product product){
        return new ProductResponse(product.getId(),product.getDescription(),product.getUnitPrice(),product.getTaxes());
    }

    public List<ProductResponse> entitiesToDTOList(List<Product> products) {
        return products.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
