package ru.anatomica.cookstarter.entity;

public class RestaurantDescription {

    private String id;
    private String description;
    private int logo;

    public RestaurantDescription() {
    }

    public RestaurantDescription(String id, String description, int logo) {
        this.id = id;
        this.description = description;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
