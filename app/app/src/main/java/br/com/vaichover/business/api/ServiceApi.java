package br.com.vaichover.business.api;

import br.com.vaichover.business.api.vo.response.OpenWeatherMapResponseVO;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface ServiceApi {

    @GET("data/2.5/find")
    Call<OpenWeatherMapResponseVO> requestWeather(
            @Query("lat")   double      lat,
            @Query("lon")   double      lon,
            @Query("cnt")   int         radius,
            @Query("units") String      units,
            @Query("lang")  String      lang,
            @Query("appid") String      appid
    );

}
