package com.example.personalrestaurantguide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class RestaurantModel {
    String RestaurantName;
    String RestaurantAddress;
    String RestaurantDescription;
    String[] RestaurantTags;
    ArrayList<String> test;
    float RestaurantRating;


    public RestaurantModel(String restaurantName, String restaurantAddress, String restaurantDescription, String[] restaurantTags, float restaurantRating) {
        RestaurantName = restaurantName;
        this.RestaurantAddress = restaurantAddress;
        this.RestaurantDescription = restaurantDescription;
        this.RestaurantTags = restaurantTags;
        this.RestaurantRating = restaurantRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantModel)) return false;
        RestaurantModel that = (RestaurantModel) o;
        return Float.compare(RestaurantRating, that.RestaurantRating) == 0 && Objects.equals(RestaurantName, that.RestaurantName) && Objects.equals(RestaurantAddress, that.RestaurantAddress) && Objects.equals(RestaurantDescription, that.RestaurantDescription) && Objects.deepEquals(RestaurantTags, that.RestaurantTags) && Objects.equals(test, that.test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RestaurantName, RestaurantAddress, RestaurantDescription, Arrays.hashCode(RestaurantTags), test, RestaurantRating);
    }

    public String getName() {
        return RestaurantName;
    }

    public String getDescription() {
        return RestaurantDescription;
    }

    public String[] getTags() {
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

    public void setAddress(String restaurantAddress) {
        RestaurantAddress = restaurantAddress;
    }

    public void setDescription(String restaurantDescription) {
        RestaurantDescription = restaurantDescription;
    }

    public void setTags(String[] restaurantTags) {
        RestaurantTags = restaurantTags;
    }

    public void setRating(float restaurantRating) {
        RestaurantRating = restaurantRating;
    }
}
