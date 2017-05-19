package br.com.vaichover.ui.view.componet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import br.com.vaichover.R;
import br.com.vaichover.ui.view.PreferencesView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class CustomDialog implements View.OnClickListener{

    @Bind(R.id.txtTitleDialog)  TextView    txtTitleDialog;
    @Bind(R.id.txtTextDialog)   TextView    txtDescDialog;
    @Bind(R.id.btnOk)           Button      btnOk;

    private Dialog          dialog;
    private Context         context;
    private String          title;
    private String          msg;
    private PreferencesView view;

    public CustomDialog(Context context, String title, String msg, PreferencesView view){
        this.context    = context;
        this.title      = title;
        this.msg        = msg;
        this.view       = view;

        this.create();
    }

    private void create(){

        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_fragment);
        dialog.setCancelable(true);

        ButterKnife.bind(this, dialog);

        txtTitleDialog.setText(title);
        txtDescDialog.setText(msg);

        btnOk.setOnClickListener(this);
    }

    public void show(){
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        dialog.cancel();
        view.navigateToDash();
    }
}