package ru.anatomica.cookstarter.entity;

import java.math.BigDecimal;

public class RestaurantMenu {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long restaurantId;
    private Long pictureId;

    public RestaurantMenu() {
    }

    public RestaurantMenu(Long id, String name, BigDecimal price, Long pictureId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pictureId = pictureId;
    }

    public RestaurantMenu(Long id, String name, String description, BigDecimal price, Long restaurantId, Long pictureId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
        this.pictureId = pictureId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }
}
