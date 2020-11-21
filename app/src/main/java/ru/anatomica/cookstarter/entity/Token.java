package ru.anatomica.cookstarter.entity;

public class Token {

    private Long userId;
    private Long restaurantId;
    private String token;

    public Token() {
    }

    public Token(Long userId, Long restaurantId, String token) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}