package br.com.product.service;

import br.com.core.model.ProductEntity;
import br.com.core.model.UserEntity;
import br.com.core.repository.ProductRepository;
import br.com.product.endpoint.dto.ProductSaveDTO;
import br.com.product.endpoint.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.jupiter.api.Assertions;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testFindAll() {
        ArrayList<ProductEntity> productEntities = new ArrayList<ProductEntity>();
        productEntities.add(getProductEntityMock());
        when(productRepository.findAll()).thenReturn(productEntities);
        Iterable<ProductEntity> result = productService.listProducts();
        Assert.assertNotNull(result);
    }

    @Test
    public void testFindById() {
        Optional<ProductEntity> productEntityOptional = Optional.of(getProductEntityMock());
        when(productRepository.findById(1l)).thenReturn(productEntityOptional);
        ProductEntity result = productService.findProductById(1l);
        Assert.assertNotNull(result);
    }

    @Test
    public void testDeleteById() {
        ProductEntity productEntity = getProductEntityMock();
        productEntity.setStatus(false);
        Optional<ProductEntity> productEntityOptional = Optional.of(productEntity);
        when(productRepository.findById(1l)).thenReturn(productEntityOptional);
        ProductEntity result = productService.deleteProductById(1l);
        Assert.assertNotNull(result);
    }

    @Test
    public void testValidateQuantityFail() {
        ProductEntity productEntity = getProductEntityMock();
        Optional<ProductEntity> productEntityOptional = Optional.of(productEntity);
        when(productRepository.findById(1l)).thenReturn(productEntityOptional);
        assertThrows(ResponseStatusException.class, () -> {
            ProductEntity result = productService.validateQuantity(1l, 20);
        });
    }

    @Test
    public void testValidateQuantitySucess() {
        ProductEntity productEntity = getProductEntityMock();
        Optional<ProductEntity> productEntityOptional = Optional.of(productEntity);
        when(productRepository.findById(1l)).thenReturn(productEntityOptional);
        ProductEntity result = productService.validateQuantity(1l, 3);
        Assert.assertNotNull(result);
    }

    private ProductEntity getProductEntityMock(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1l);
        productEntity.setName("MockName");
        productEntity.setDescription("MockDescription");
        productEntity.setQuantity(10);
        productEntity.setPrice(new BigDecimal(10));
        productEntity.setStatus(true);
        return productEntity;
    }

    private ProductSaveDTO getProductSaveDTOMock() {
        ProductSaveDTO productSaveDTO = new ProductSaveDTO();
        productSaveDTO.setName("MockName");
        productSaveDTO.setDescription("MockDescription");
        productSaveDTO.setPrice(new BigDecimal(10));
        productSaveDTO.setQuantity(10);
        return productSaveDTO;
    }

}
