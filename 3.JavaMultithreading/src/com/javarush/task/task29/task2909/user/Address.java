package com.javarush.task.task29.task2909.user;

/**
 * Created by DmRG on 03.06.2017.
 */
public class Address {
    private String country;
    private String city;
    private String house;
    private Address address;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return country + " " + city + " " + house;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
