package br.com.vaichover.common;

import br.com.vaichover.model.OpenWeatherMap;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface OnOpenWeatherFinishedListener extends BaseCommonListener {
    void onSuccess(OpenWeatherMap openWeatherMap);
}
