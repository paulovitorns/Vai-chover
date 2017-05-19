package br.com.vaichover.model;

import java.io.Serializable;
import java.util.Locale;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class UserPreferences implements Serializable {

    public static final String KEY = UserPreferences.class.getSimpleName();

    private Degrees                 degrees;
    private int                     radius;
    private String                  lang = Locale.getDefault().getLanguage();
    private GPlace                  place;
    private UserLastDraggedLocation lastDragPlace;

    public UserPreferences(){
        this.degrees    = Degrees.CELSIUS;
        this.radius     = 10;
        this.place      = new GPlace();
    }

    public Degrees getDegrees() {
        return degrees;
    }

    public void setDegrees(Degrees degrees) {
        this.degrees = degrees;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getLang() {
        return lang;
    }

    public GPlace getPlace() {
        return place;
    }

    public void setPlace(GPlace place) {
        this.place = place;
    }

    public UserLastDraggedLocation getLastDragPlace() {
        return lastDragPlace;
    }

    public void setLastDragPlace(UserLastDraggedLocation lastDragPlace) {
        this.lastDragPlace = lastDragPlace;
    }

    public enum Degrees{
        CELSIUS("metric"),
        FAHRENHEIT("imperial");

        String units;

        Degrees(String units) {
            this.units = units;
        }

        public String getUnits() {
            return units;
        }
    }

}
