package br.com.vaichover.business.service;

import br.com.vaichover.common.OnGPlacesRequestFinished;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface GPlacesService {
    void onRequestPlaces(OnGPlacesRequestFinished listener, String param);
}
