package br.com.vaichover.business.api;

import br.com.vaichover.business.api.vo.response.GPlaceResponseVO;
import br.com.vaichover.business.api.vo.response.GPlacesResponseVO;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface ServiceGmapsApi {

    @GET("place/autocomplete/json")
    Call<GPlacesResponseVO> requestPlaces(
            @Query("input") String input,
            @Query("types") String types,
            @Query("key") String key
    );

    @GET("place/details/json")
    Call<GPlaceResponseVO> requestPlace(
            @Query("placeid")   String placeid,
            @Query("key")       String key
    );
}
