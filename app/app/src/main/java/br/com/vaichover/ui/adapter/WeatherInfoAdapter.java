package br.com.vaichover.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.com.vaichover.R;
import br.com.vaichover.model.OpenWeatherMapResult;
import br.com.vaichover.util.ImageUtils;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class WeatherInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mContents;
    private Activity activity;

    public WeatherInfoAdapter(Activity activity) {
        this.activity   = activity;
        this.mContents  = activity.getLayoutInflater().inflate(R.layout.marker_gmaps, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        render(marker, mContents);
        return mContents;
    }

    private void render(Marker marker, View view){

        OpenWeatherMapResult result = (OpenWeatherMapResult) marker.getTag();

        TextView title  = (TextView) view.findViewById(R.id.txPlaceName);
        TextView temp   = (TextView) view.findViewById(R.id.txTemp);
        ImageView icon  = (ImageView) view.findViewById(R.id.weatherIcon);

        String urlIcon  = activity.getString(R.string.url_icon_weather);
        String txtemp   = activity.getString(R.string.temp);
        txtemp          = txtemp.replace("...", String.valueOf(Math.round(result.getMain().getTemp())));

        title.setText(result.getName());
        temp.setText(txtemp);

        if(result.getWeather().get(0).getIcon() != null && !result.getWeather().get(0).getIcon().equalsIgnoreCase(""))
            ImageUtils.setImageToImageView((urlIcon+result.getWeather().get(0).getIcon()+".png"), icon, activity);
    }

}
