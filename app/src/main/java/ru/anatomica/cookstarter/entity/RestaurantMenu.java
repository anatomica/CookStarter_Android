package ru.anatomica.cookstarter.entity;

import java.math.BigDecimal;

public class RestaurantMenu {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long restaurantId;
    private int pictureId;

    public RestaurantMenu() {
    }

    public RestaurantMenu(String name, BigDecimal price, int pictureId) {
        this.name = name;
        this.price = price;
        this.pictureId = pictureId;
    }

    public RestaurantMenu(Long id, String name, BigDecimal price, int pictureId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pictureId = pictureId;
    }

    public RestaurantMenu(Long id, String name, String description, BigDecimal price, Long restaurantId, int pictureId) {
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

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
