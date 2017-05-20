package br.com.vaichover.model;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.vaichover.business.api.vo.response.OpenWeatherMapResponseVO;
import br.com.vaichover.business.api.vo.response.OpenWeatherMapResultResponseVO;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherMap implements Serializable {

    public static final String KEY = OpenWeatherMap.class.getSimpleName();

    private String                      message;
    private int                         cod;
    private int                         count;
    private List<OpenWeatherMapResult>  list;
    private boolean                     isLastSearchByDragInMap;

    public OpenWeatherMap(OpenWeatherMapResponseVO responseVO) {
        this.message    = responseVO.message;
        this.cod        = responseVO.cod;
        this.count      = responseVO.count;
        this.list       = new ArrayList<>();

        this.setData(responseVO.list);
    }

    private void setData(List<OpenWeatherMapResultResponseVO> list){
        for (OpenWeatherMapResultResponseVO responseVO : list){
            this.list.add(new OpenWeatherMapResult(responseVO));
        }
    }

    public void reorderBasedOnDistance(Location location){
        for (OpenWeatherMapResult result : this.list){
            Location locationEnd = new Location("end");
            locationEnd.setLatitude(result.getCoord().getLat());
            locationEnd.setLongitude(result.getCoord().getLon());

            result.setDistance((location.distanceTo(locationEnd) / 1000.0));
        }
        this.repopulateDataBasedOnDistante();
    }

    public void repopulateDataBasedOnDistante(){
        Collections.sort(this.list, Collections.<OpenWeatherMapResult>reverseOrder());
    }

    public String getMessage() {
        return message;
    }

    public int getCod() {
        return cod;
    }

    public int getCount() {
        return count;
    }

    public List<OpenWeatherMapResult> getList() {
        return list;
    }

    public boolean isLastSearchByDragInMap() {
        return isLastSearchByDragInMap;
    }

    public void setLastSearchByDragInMap(boolean lastSearchByDragInMap) {
        isLastSearchByDragInMap = lastSearchByDragInMap;
    }
}
