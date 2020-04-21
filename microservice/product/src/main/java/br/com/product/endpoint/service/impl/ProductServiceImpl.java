package br.com.product.endpoint.service.impl;

import br.com.core.model.ProductEntity;
import br.com.core.repository.ProductRepository;
import br.com.product.endpoint.dto.ProductSaveDTO;
import br.com.product.endpoint.service.ProductService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<ProductEntity> listProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public ProductEntity saveProduct(ProductSaveDTO productSaveDTO) {
        ProductEntity productEntity = productSaveDTO.parseProductEntity();
        return this.productRepository.save(productEntity);
    }

    @Override
    public ProductEntity findProductById(Long id) {
        ProductEntity productFinded = validateProductExist(id);
        return productFinded;
    }

    @Override
    public ProductEntity deleteProductById(Long id) {
        ProductEntity productFinded = validateProductExist(id);
        productFinded.setStatus(false);
        this.productRepository.save(productFinded);
        return productFinded;
    }

    @Override
    public ProductEntity validateQuantity(Long id, Integer quantity) {
        ProductEntity productFinded = validateProductExist(id);

        if (Math.subtractExact(productFinded.getQuantity(), quantity) >= 0) {
            return productFinded;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Quantity invalid for product " + productFinded.getId());
        }
    }

    @Override
    public ProductEntity validateProductExist(Long id) {
        Optional<ProductEntity> productEntity = this.productRepository.findById(id);

        if (productEntity.isPresent()) {
            ProductEntity productFinded = productEntity.get();
            return productFinded;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    public void saveAllProductUpdate(List<ProductEntity> productEntityList){
        this.productRepository.saveAll(productEntityList);
    }

}
