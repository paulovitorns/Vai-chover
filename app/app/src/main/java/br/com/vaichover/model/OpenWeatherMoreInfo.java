package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.OpenWeatherMoreInfoResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherMoreInfo implements Serializable {

    public static final String KEY = OpenWeatherMoreInfo.class.getSimpleName();

    private int      id;
    private String   main;
    private String   description;
    private String   icon;

    public OpenWeatherMoreInfo(OpenWeatherMoreInfoResponseVO responseVO) {
        this.id             = responseVO.id;
        this.main           = responseVO.main;
        this.description    = responseVO.description;
        this.icon           = responseVO.icon;
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
