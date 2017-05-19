package br.com.vaichover.business.api.vo.response;

import com.google.gson.annotations.SerializedName;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherMoreInfoResponseVO {
    @SerializedName("id")           public int      id;
    @SerializedName("main")         public String   main;
    @SerializedName("description")  public String   description;
    @SerializedName("icon")         public String   icon;
}
