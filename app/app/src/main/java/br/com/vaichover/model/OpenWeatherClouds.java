package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.OpenWeatherCloudsResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherClouds implements Serializable {

    public static final String KEY = OpenWeatherClouds.class.getSimpleName();

    private int clouds;

    public OpenWeatherClouds(OpenWeatherCloudsResponseVO responseVO) {
        this.clouds = responseVO.clouds;
    }

    public int getClouds() {
        return clouds;
    }
}
