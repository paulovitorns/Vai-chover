package br.com.vaichover.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import br.com.vaichover.R;

/**
 * © Copyright 2016 Brewjas.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Brewjas app.
 */

public class ImageUtils {

    public static void setImageToImageView(final String image, final ImageView view, final Context context){

        Picasso.with(context)
            .load(image)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(view, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(context)
                            .load(image)
                            .into(view, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError() {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        view.setImageDrawable(context.getDrawable(R.drawable.ic_app));
                                    }else{
                                        view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_app));
                                    }
                                    Log.v("PICASSO_ERROR", "Couldn't not fetch image");
                                }
                            });
                }
            });
    }

}
