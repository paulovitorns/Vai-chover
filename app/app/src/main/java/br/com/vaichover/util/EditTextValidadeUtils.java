package br.com.vaichover.util;

import android.content.Context;
import android.os.Build;
import android.widget.EditText;

import br.com.vaichover.R;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class EditTextValidadeUtils {


    static public void setErrorToView(EditText edt, Context context) {
        edt.setBackgroundResource(R.drawable.view_edittext_error_background);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            edt.setTextColor(context.getColor(android.R.color.holo_red_light));
            edt.setHintTextColor(context.getColor(android.R.color.holo_red_light));
        }else{
            edt.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
            edt.setHintTextColor(context.getResources().getColor(android.R.color.holo_red_light));
        }
    }

    static public void setNormalStateLoginToView(EditText edt, Context context) {
        edt.setBackgroundResource(R.drawable.view_edittext_background);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            edt.setTextColor(context.getColor(R.color.white));
            edt.setHintTextColor(context.getColor(R.color.white));
        }else{
            edt.setTextColor(context.getResources().getColor(R.color.white));
            edt.setHintTextColor(context.getResources().getColor(R.color.white));
        }
    }

    static public void setNormalStateToView(EditText edt, Context context) {
        edt.setBackgroundResource(R.drawable.view_edittext_background);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            edt.setTextColor(context.getColor(R.color.white));
            edt.setHintTextColor(context.getColor(R.color.white));
        }else{
            edt.setTextColor(context.getResources().getColor(R.color.white));
            edt.setHintTextColor(context.getResources().getColor(R.color.white));
        }
    }
}
