package br.com.vaichover.model;

import java.io.Serializable;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class UserPreferences implements Serializable {

    public static final String KEY = UserPreferences.class.getSimpleName();

    private String  name;
    private int     radius;
    private String  address;
    private String  neighborhood;
    private String  city;
    private String  state;
    private String  country;
    private double  lat;
    private double  lng;
    private String  imgNameResource;

    public UserPreferences() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getImgNameResource() {
        return imgNameResource;
    }

    public void setImgNameResource(String imgNameResource) {
        this.imgNameResource = imgNameResource;
    }

}
