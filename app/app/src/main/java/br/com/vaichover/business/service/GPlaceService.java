package br.com.vaichover.business.service;

import br.com.vaichover.common.OnPlaceResponseFinishedListener;
import br.com.vaichover.model.GPlacesAutoComplete;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface GPlaceService {
    void resquestPlace(OnPlaceResponseFinishedListener listener, GPlacesAutoComplete autoComplete);
}
