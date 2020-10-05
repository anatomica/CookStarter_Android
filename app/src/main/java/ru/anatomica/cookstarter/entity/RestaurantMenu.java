package ru.anatomica.cookstarter.entity;

public class RestaurantMenu {

    private String id;
    private String name;
    private String prize;
    private int logo;

    public RestaurantMenu() {
    }

    public RestaurantMenu(String name, String prize, int logo) {
        this.name = name;
        this.prize = prize;
        this.logo = logo;
    }

    public RestaurantMenu(String id, String name, String prize, int logo) {
        this.id = id;
        this.name = name;
        this.prize = prize;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
