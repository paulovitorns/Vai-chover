package br.com.vaichover.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.vaichover.R;
import br.com.vaichover.model.GPlace;
import br.com.vaichover.model.GPlaces;
import br.com.vaichover.model.GPlacesAutoComplete;
import br.com.vaichover.ui.presenter.PreferencesPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private GPlaces                     places;
    private Context                     context;
    private final PreferencesPresenter  presenter;

    public PlacesAdapter(GPlaces places, Context context, PreferencesPresenter presenter) {
        this.places     = places;
        this.context    = context;
        this.presenter  = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(context).inflate(R.layout.simple_drop_down_item, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    public void addItem(GPlacesAutoComplete autoComplete){
        this.places.getPredictions().add(autoComplete);
        notifyDataSetChanged();
    }

    public void removeAllItens(){
        this.places.getPredictions().removeAll(places.getPredictions());
        notifyDataSetChanged();
    }

    public void addAll(List<GPlacesAutoComplete> list){
        this.places.getPredictions().addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GPlacesAutoComplete autoComplete = places.getPredictions().get(position);
        if(autoComplete != null){
            holder.txtItem.setText(autoComplete.getDescription());
            holder.setParam(autoComplete);
        }
    }

    @Override
    public int getItemCount() {
        return places.getPredictions().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.txItem)   TextView txtItem;
        private GPlacesAutoComplete autoComplete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setParam(GPlacesAutoComplete autoComplete){
            this.autoComplete = autoComplete;
        }

        @Override
        public void onClick(View view) {
            presenter.setPlaceSelected(autoComplete);
        }
    }

}
