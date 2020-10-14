package ru.anatomica.cookstarter.entity;

import java.math.BigDecimal;

public class Order {

    private Long id;
    private RestaurantMenu restaurantMenu;
    private BigDecimal price;
    private int quantity;
    private Order order;

    public Order() {
    }

    public Order(Long id, RestaurantMenu restaurantMenu, BigDecimal price, int quantity, Order order) {
        this.id = id;
        this.restaurantMenu = restaurantMenu;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RestaurantMenu getRestaurantMenu() {
        return restaurantMenu;
    }

    public void setRestaurantMenu(RestaurantMenu restaurantMenu) {
        this.restaurantMenu = restaurantMenu;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
