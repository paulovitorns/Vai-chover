package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.OpenWeatherCoordsResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherCoords implements Serializable {

    public static final String KEY = OpenWeatherCoords.class.getSimpleName();

    private double lat;
    private double lon;

    public OpenWeatherCoords(OpenWeatherCoordsResponseVO responseVO) {
        this.lat = responseVO.lat;
        this.lon = responseVO.lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
