package br.com.vaichover.business.api.vo.response;

import com.google.gson.annotations.SerializedName;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class GPlaceLocationDataResponseVO {
    @SerializedName("lat")  public double lat;
    @SerializedName("lng")  public double lng;
}
