package com.example.personalrestaurantguide;

public class RestaurantModel {
    String RestaurantName;
    int image;
    String RestaurantAddress;
    String RestaurantDescription;
    String RestaurantTags;
    float RestaurantRating;


    public RestaurantModel(String restaurantName, int image, String restaurantAddress, String restaurantDescription, String restaurantTags, float restaurantRating) {
        RestaurantName = restaurantName;
        this.image = image;
        this.RestaurantAddress = restaurantAddress;
        this.RestaurantDescription = restaurantDescription;
        this.RestaurantTags = restaurantTags;
        this.RestaurantRating = restaurantRating;
    }

    public String getName() {
        return RestaurantName;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return RestaurantDescription;
    }

    public String getTags() {
        return RestaurantTags;
    }

    public float getRating() {
        return RestaurantRating;
    }

    public String getAddress() {
        return RestaurantAddress;
    }

    public void setName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setAddress(String restaurantAddress) {
        RestaurantAddress = restaurantAddress;
    }

    public void setDescription(String restaurantDescription) {
        RestaurantDescription = restaurantDescription;
    }

    public void setTags(String restaurantTags) {
        RestaurantTags = restaurantTags;
    }

    public void setRating(float restaurantRating) {
        RestaurantRating = restaurantRating;
    }
}
