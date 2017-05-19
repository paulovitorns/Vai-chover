package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.GPlaceLocationResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class GPlaceLocation implements Serializable {

    public static final String KEY = GPlaceLocation.class.getSimpleName();

    private double lat;
    private double lng;

    public GPlaceLocation() {}

    public GPlaceLocation(GPlaceLocationResponseVO responseVO) {
        this.lat = responseVO.location.lat;
        this.lng = responseVO.location.lng;
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
}
