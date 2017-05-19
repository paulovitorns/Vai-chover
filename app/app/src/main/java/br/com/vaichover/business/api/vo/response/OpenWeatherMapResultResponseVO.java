package br.com.vaichover.business.api.vo.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherMapResultResponseVO {
    @SerializedName("id")       public long id;
    @SerializedName("name")     public String name;
    @SerializedName("coord")    public OpenWeatherCoordsResponseVO          coord;
    @SerializedName("main")     public OpenWeatherMainResponseVO            main;
    @SerializedName("dt")       public long dt;
    @SerializedName("wind")     public OpenWeatherWindResponseVO            wind;
    @SerializedName("rain")     public OpenWeatherVolumeResponseVO          rain;
    @SerializedName("snow")     public OpenWeatherVolumeResponseVO          snow;
    @SerializedName("clouds")   public OpenWeatherCloudsResponseVO          clouds;
    @SerializedName("weather")  public List<OpenWeatherMoreInfoResponseVO>  weather;

}
