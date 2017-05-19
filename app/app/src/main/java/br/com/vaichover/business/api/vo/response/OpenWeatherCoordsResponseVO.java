package br.com.vaichover.business.api.vo.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherCoordsResponseVO {
    @SerializedName("lat")  public double lat;
    @SerializedName("lon")  public double lon;
}
