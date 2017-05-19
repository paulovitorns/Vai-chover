package br.com.vaichover.model;

import java.io.Serializable;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class UserLastDraggedLocation implements Serializable {

    public static final String KEY = UserLastDraggedLocation.class.getSimpleName();

    private double lat;
    private double lng;

    public UserLastDraggedLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
