package ru.anatomica.cookstarter.entity;

public class Restaurant {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String telephone;
    private int logoId;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String address, int logoId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.logoId = logoId;
    }

    public Restaurant(Long id, String name, String description, String address, String telephone, int logoId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.telephone = telephone;
        this.logoId = logoId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }
}
