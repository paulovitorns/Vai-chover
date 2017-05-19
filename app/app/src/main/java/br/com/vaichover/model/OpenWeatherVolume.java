package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.OpenWeatherVolumeResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherVolume implements Serializable {

    public static final String KEY = OpenWeatherVolume.class.getSimpleName();

    private double volume;

    public OpenWeatherVolume(OpenWeatherVolumeResponseVO responseVO) {
        this.volume = responseVO.volume;
    }

}
