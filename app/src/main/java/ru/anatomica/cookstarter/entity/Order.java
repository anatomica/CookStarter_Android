package ru.anatomica.cookstarter.entity;

import java.math.BigDecimal;

public class Order {

    private Long id;
    private String productTitle;
    private BigDecimal price;
    private int quantity;

    public Order() {
    }

    public Order(Long id, String productTitle, BigDecimal price, int quantity) {
        this.id = id;
        this.productTitle = productTitle;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
