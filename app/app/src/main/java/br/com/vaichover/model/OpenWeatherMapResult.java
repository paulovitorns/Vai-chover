package br.com.vaichover.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.vaichover.business.api.vo.response.OpenWeatherMapResultResponseVO;
import br.com.vaichover.business.api.vo.response.OpenWeatherMoreInfoResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherMapResult implements Serializable {

    public static final String KEY = OpenWeatherMapResult.class.getSimpleName();

    private long                        id;
    private String                      name;
    private OpenWeatherCoords           coord;
    private OpenWeatherMain             main;
    private long                        dt;
    private OpenWeatherWind             wind;
    private OpenWeatherVolume           rain;
    private OpenWeatherVolume           snow;
    private OpenWeatherClouds           clouds;
    private List<OpenWeatherMoreInfo>   weather;

    public OpenWeatherMapResult(OpenWeatherMapResultResponseVO responseVO) {
        this.id         = responseVO.id;
        this.name       = responseVO.name;
        if(responseVO.coord != null)
            this.coord      = new OpenWeatherCoords(responseVO.coord);
        if(responseVO.main != null)
            this.main       = new OpenWeatherMain(responseVO.main);
        this.dt         = responseVO.dt;
        if(responseVO.wind != null)
            this.wind       = new OpenWeatherWind(responseVO.wind);
        if(responseVO.rain != null)
            this.rain       = new OpenWeatherVolume(responseVO.rain);
        if(responseVO.snow != null)
            this.snow       = new OpenWeatherVolume(responseVO.snow);
        if(responseVO.clouds != null)
            this.clouds     = new OpenWeatherClouds(responseVO.clouds);
        this.weather    = new ArrayList<>();

        this.setData(responseVO.weather);
    }

    private void setData(List<OpenWeatherMoreInfoResponseVO> list){
        for (OpenWeatherMoreInfoResponseVO responseVO : list){
            this.weather.add(new OpenWeatherMoreInfo(responseVO));
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public OpenWeatherCoords getCoord() {
        return coord;
    }

    public OpenWeatherMain getMain() {
        return main;
    }

    public long getDt() {
        return dt;
    }

    public OpenWeatherWind getWind() {
        return wind;
    }

    public OpenWeatherVolume getRain() {
        return rain;
    }

    public OpenWeatherVolume getSnow() {
        return snow;
    }

    public OpenWeatherClouds getClouds() {
        return clouds;
    }

    public List<OpenWeatherMoreInfo> getWeather() {
        return weather;
    }
}
