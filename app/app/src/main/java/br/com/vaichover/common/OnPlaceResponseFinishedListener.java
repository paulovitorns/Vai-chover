package br.com.vaichover.common;

import br.com.vaichover.model.GPlace;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface OnPlaceResponseFinishedListener extends BaseCommonListener {
    void onSuccess(GPlace place);
}
