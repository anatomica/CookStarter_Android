package ru.anatomica.cookstarter.entity;

public class Restaurant {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String telephone;
    private Long pictureId;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String address, Long pictureId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.pictureId = pictureId;
    }

    public Restaurant(Long id, String name, String description, String address, String telephone, Long pictureId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.telephone = telephone;
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

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }
}
