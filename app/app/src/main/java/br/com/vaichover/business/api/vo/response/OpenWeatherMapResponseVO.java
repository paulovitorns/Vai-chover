package br.com.vaichover.business.api.vo.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherMapResponseVO {
    @SerializedName("message")  public String                               message;
    @SerializedName("cod")      public int                                  cod;
    @SerializedName("count")    public int                                  count;
    @SerializedName("list")     public List<OpenWeatherMapResultResponseVO> list;
}
