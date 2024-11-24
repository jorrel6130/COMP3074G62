package com.example.personalrestaurantguide;

public class RestaurantModel {
    String RestaurantName;
    int image;
    String RestaurantAddress;
    String RestaurantDescription;
    String[] RestaurantTags;
    int RestaurantRating;


    public RestaurantModel(String restaurantName, int image, String restaurantAddress, String restaurantDescription, String[] restaurantTags, int restaurantRating) {
        RestaurantName = restaurantName;
        this.image = image;
        this.RestaurantAddress = restaurantAddress;
        this.RestaurantDescription = restaurantDescription;
        this.RestaurantTags = restaurantTags;
        this.RestaurantRating = restaurantRating;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public int getImage() {
        return image;
    }

    public String getRestaurantDescription() {
        return RestaurantDescription;
    }

    public String[] getRestaurantTags() {
        return RestaurantTags;
    }

    public int getRestaurantRating() {
        return RestaurantRating;
    }

    public String getRestaurantAddress() {
        return RestaurantAddress;
    }
}
