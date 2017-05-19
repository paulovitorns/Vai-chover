package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.GPlacedetailResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class GPlacedetail implements Serializable{

    public static final String KEY = GPlacedetail.class.getSimpleName();

    private String         formatted_address;
    private GPlaceLocation geometry;

    public GPlacedetail() {
        this.formatted_address = "minha localização";
        this.geometry = new GPlaceLocation();
    }

    public GPlacedetail(GPlacedetailResponseVO responseVO) {
        this.formatted_address  = responseVO.formatted_address;
        this.geometry           = new GPlaceLocation(responseVO.geometry);
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public GPlaceLocation getGeometry() {
        return geometry;
    }

    public void setGeometry(GPlaceLocation geometry) {
        this.geometry = geometry;
    }
}
