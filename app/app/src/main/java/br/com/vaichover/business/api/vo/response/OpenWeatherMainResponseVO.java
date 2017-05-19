package br.com.vaichover.business.api.vo.response;

import com.google.gson.annotations.SerializedName;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherMainResponseVO {
    @SerializedName("temp")     public double   temp;
    @SerializedName("pressure") public double   pressure;
    @SerializedName("humidity") public int      humidity;
    @SerializedName("temp_min") public double   temp_min;
    @SerializedName("temp_max") public double   temp_max;
}
