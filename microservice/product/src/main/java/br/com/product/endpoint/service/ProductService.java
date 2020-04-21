package br.com.product.endpoint.service;

import br.com.core.model.ProductEntity;
import br.com.product.endpoint.dto.ProductSaveDTO;

import java.util.List;

public interface ProductService {

    public Iterable<ProductEntity> listProducts();
    public ProductEntity saveProduct(ProductSaveDTO productSaveDTO);
    public void saveAllProductUpdate(List<ProductEntity> productEntityList);
    public ProductEntity findProductById(Long id);
    public ProductEntity deleteProductById(Long id);
    public ProductEntity validateQuantity(Long id, Integer quantity);
    public ProductEntity validateProductExist(Long id);

}
