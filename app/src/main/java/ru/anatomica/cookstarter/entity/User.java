package ru.anatomica.cookstarter.entity;

public class User {

    private Long id;
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private boolean userBlock;
    private Role roles;

    public User() {
    }

    public User(Long id, String phone, String password, String firstName, String lastName, String fullName, String email, boolean userBlock, Role roles) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.userBlock = userBlock;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isUserBlock() {
        return userBlock;
    }

    public void setUserBlock(boolean userBlock) {
        this.userBlock = userBlock;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }
}
