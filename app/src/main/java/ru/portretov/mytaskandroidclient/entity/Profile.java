package ru.portretov.mytaskandroidclient.entity;

public class Profile {

    private String id;

    private String firstName;

    private String lastName;

    private String miniBio;

    private String location;

    private Integer age;

    private boolean postTask;

    private boolean earnMoney;

    private boolean confirmed;

    private Double creatorRating;

    private Double executeRating;

    private Image image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMiniBio() {
        return miniBio;
    }

    public void setMiniBio(String miniBio) {
        this.miniBio = miniBio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isPostTask() {
        return postTask;
    }

    public void setPostTask(boolean postTask) {
        this.postTask = postTask;
    }

    public boolean isEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(boolean earnMoney) {
        this.earnMoney = earnMoney;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Double getCreatorRating() {
        return creatorRating;
    }

    public void setCreatorRating(Double creatorRating) {
        this.creatorRating = creatorRating;
    }

    public Double getExecuteRating() {
        return executeRating;
    }

    public void setExecuteRating(Double executeRating) {
        this.executeRating = executeRating;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
