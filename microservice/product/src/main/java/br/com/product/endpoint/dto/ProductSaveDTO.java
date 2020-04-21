package br.com.product.endpoint.dto;

import br.com.core.model.ProductEntity;

import java.math.BigDecimal;

public class ProductSaveDTO {

    private String name;

    private String description;

    private Integer quantity;

    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductEntity parseProductEntity() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(this.getName());
        productEntity.setDescription(this.getDescription());
        productEntity.setPrice(this.getPrice());
        productEntity.setQuantity(this.getQuantity());
        productEntity.setStatus(true);
        return productEntity;
    }

    public ProductSaveDTO() {
    }
}
