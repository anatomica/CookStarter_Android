package ru.anatomica.cookstarter.entity;

import java.math.BigDecimal;

public class RestaurantMenu {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Long restaurantId;
    private int logoId;

    public RestaurantMenu() {
    }

    public RestaurantMenu(String title, BigDecimal price, int logoId) {
        this.title = title;
        this.price = price;
        this.logoId = logoId;
    }

    public RestaurantMenu(Long id, String title, BigDecimal price, int logoId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.logoId = logoId;
    }

    public RestaurantMenu(Long id, String title, String description, BigDecimal price, Long restaurantId, int logoId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
        this.logoId = logoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }
}
