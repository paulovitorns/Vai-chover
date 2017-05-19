package br.com.vaichover.model;

import java.io.Serializable;

import br.com.vaichover.business.api.vo.response.GPlaceResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class GPlace implements Serializable {

    public static final String KEY = GPlace.class.getSimpleName();

    private GPlacedetail result;

    public GPlace() {
        this.result = new GPlacedetail();
    }

    public GPlace(GPlaceResponseVO responseVO) {
        this.result = new GPlacedetail(responseVO.result);
    }

    public GPlacedetail getResult() {
        return result;
    }
}
