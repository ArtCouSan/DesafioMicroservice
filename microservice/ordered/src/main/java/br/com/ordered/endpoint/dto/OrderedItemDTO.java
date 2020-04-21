package br.com.ordered.endpoint.dto;

import java.math.BigDecimal;

public class OrderedItemDTO {

    private Long idProduct;

    private Integer quantity;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderedItemDTO() {
    }
}
