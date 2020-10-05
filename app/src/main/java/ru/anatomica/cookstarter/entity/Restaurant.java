package ru.anatomica.cookstarter.entity;

public class Restaurant {

    private int id;
    private String name;
    private String address;
    private int logo;

    public Restaurant() {
    }

    public Restaurant(String name, String address, int logo) {
        this.name = name;
        this.address = address;
        this.logo = logo;
    }

    public Restaurant(int id, String name, String address, int logo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

}
