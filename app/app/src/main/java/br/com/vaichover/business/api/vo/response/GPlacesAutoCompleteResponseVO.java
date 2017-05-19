package br.com.vaichover.business.api.vo.response;

import com.google.gson.annotations.SerializedName;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class GPlacesAutoCompleteResponseVO {
    @SerializedName("description")  public String description;
    @SerializedName("id")           public String id;
    @SerializedName("place_id")     public String place_id;
}
