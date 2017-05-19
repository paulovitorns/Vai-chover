package br.com.vaichover.business.api.vo.response;

import com.google.gson.annotations.SerializedName;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherWindResponseVO {
    @SerializedName("speed")    public double   speed;
    @SerializedName("deg")      public double   deg;
}
