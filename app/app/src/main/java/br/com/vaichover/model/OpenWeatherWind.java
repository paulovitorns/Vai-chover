package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.OpenWeatherWindResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherWind implements Serializable {

    public static final String KEY = OpenWeatherWind.class.getSimpleName();

    private double  speed;
    private double  deg;

    public OpenWeatherWind(OpenWeatherWindResponseVO responseVO) {
        this.speed  = responseVO.speed;
        this.deg    = responseVO.deg;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }
}
