package br.com.vaichover.model;

import java.util.ArrayList;
import java.util.List;

import br.com.vaichover.business.api.vo.response.GPlacesAutoCompleteResponseVO;
import br.com.vaichover.business.api.vo.response.GPlacesResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class GPlaces {

    private List<GPlacesAutoComplete> predictions;

    public GPlaces() {
        predictions = new ArrayList<>();
    }

    public GPlaces(GPlacesResponseVO responseVO) {
        this.predictions = new ArrayList<>();
        this.setList(responseVO);
    }

    private void setList(GPlacesResponseVO responseVO){
        for (GPlacesAutoCompleteResponseVO response : responseVO.predictions){
            predictions.add(new GPlacesAutoComplete(response));
        }
    }

    public List<GPlacesAutoComplete> getPredictions() {
        return predictions;
    }

}
