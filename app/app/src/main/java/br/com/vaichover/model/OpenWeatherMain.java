package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.OpenWeatherMainResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherMain implements Serializable {

    public static final String KEY = OpenWeatherMain.class.getSimpleName();

    private double      temp;
    private double      pressure;
    private int         humidity;
    private double      temp_min;
    private double      temp_max;

    public OpenWeatherMain(OpenWeatherMainResponseVO responseVO) {
        this.temp       = responseVO.temp;
        this.pressure   = responseVO.pressure;
        this.humidity   = responseVO.humidity;
        this.temp_min   = responseVO.temp_min;
        this.temp_max   = responseVO.temp_max;
    }

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }
}
