package br.com.vaichover.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.vaichover.R;
import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.OpenWeatherMapResult;
import br.com.vaichover.util.ImageUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private OpenWeatherMap  map;
    private Context         context;
    private String          urlIcon;
    public WeatherAdapter(OpenWeatherMap map, Context context) {
        this.map = map;
        this.context = context;
        urlIcon = context.getString(R.string.url_icon_weather);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(context).inflate(R.layout.view_weather_row, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OpenWeatherMapResult result = map.getList().get(position);

        if(result != null){

            holder.txLocate.setText(result.getName());
            holder.txDistanceFromMe.setText(String.valueOf(Math.round(result.getDistance()))+"km");
            holder.txWeatherDesc.setText(result.getWeather().get(0).getDescription());

            String txtemp = context.getString(R.string.temp);
            txtemp = txtemp.replace("...", String.valueOf(Math.round(result.getMain().getTemp())));
            holder.txTemp.setText(txtemp);

            String txMin = context.getString(R.string.min_temp);
            txMin = txMin.replace("...", String.valueOf(Math.round(result.getMain().getTemp_min())));
            holder.txMin.setText(txMin);

            String txMax = context.getString(R.string.max_temp);
            txMax = txMax.replace("...", String.valueOf(Math.round(result.getMain().getTemp_max())));
            holder.txMax.setText(txMax);

            if(result.getWeather().get(0).getIcon() != null && !result.getWeather().get(0).getIcon().equalsIgnoreCase("")) {

                ImageUtils.setImageToImageView((urlIcon+result.getWeather().get(0).getIcon()+".png"), holder.weatherIcon, context);
            }

        }
    }

    @Override
    public int getItemCount() {
        return map.getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.txLocate)            TextView    txLocate;
        @Bind(R.id.txDistanceFromMe)    TextView    txDistanceFromMe;
        @Bind(R.id.txWeatherDesc)       TextView    txWeatherDesc;
        @Bind(R.id.txTemp)              TextView    txTemp;
        @Bind(R.id.txMin)               TextView    txMin;
        @Bind(R.id.txMax)               TextView    txMax;
        @Bind(R.id.weatherIcon)         ImageView   weatherIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
