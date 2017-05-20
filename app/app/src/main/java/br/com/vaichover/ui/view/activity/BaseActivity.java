package br.com.vaichover.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.vaichover.R;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.ui.view.componet.ProgressDialog;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @Bind(R.id.toolbar) Toolbar toolbar;

    //Empty State
    @Nullable
    @Bind(R.id.emptyStateContainer) LinearLayout emptyStateContainer;
    @Nullable
    @Bind(R.id.iconError)       ImageView   iconError;
    @Nullable
    @Bind(R.id.txError)         TextView    txError;
    @Nullable
    @Bind(R.id.btnReTry)        Button      btnReTry;
    @Nullable
    @Bind(R.id.btnConectar)     Button      btnConectar;
    @Nullable
    @Bind(R.id.btnNovaBusca)    Button      btnNovaBusca;
    @Nullable
    @Bind(R.id.btnLocation)     Button      btnLocation;

    public ActionBar        actionBar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Montar toolbar comum
     * <p>
     *     Metodo usado para exibir um toolbar comum
     * </p>
     *
     * @param resId resource id da string do titulo do modal
     */
    public void setupNavigateActionBarHome(int resId) {

        toolbar.setTitle(resId);

        setSupportActionBar(toolbar);

        this.actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

    }

    /**
     * Montar toolbar comum
     * <p>
     *     Metodo usado para exibir um toolbar comum
     * </p>
     *
     * @param resId resource id da string do titulo do modal
     */
    public void setupNavigateActionBar(int resId) {

        toolbar.setTitle(resId);

        setSupportActionBar(toolbar);

        this.actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * Montar toolbar para o modal
     * <p>
     *     Metodo usado para exibir um toolbar exclusivamente para modal
     * </p>
     *
     * @param resId resource id da string do titulo do modal
     */
    public void setupNavigateActionBarModal(int resId) {

        toolbar.setTitle(resId);

        Drawable icon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icon = getDrawable( R.drawable.ic_check_white_24dp );
        }else{
            icon = getResources().getDrawable( R.drawable.ic_check_white_24dp );
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            icon.setColorFilter(getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
        }else{
            icon.setColorFilter(getResources().getColor(android.R.color.holo_green_light), PorterDuff.Mode.SRC_ATOP);
        }

        toolbar.setNavigationIcon(icon);

        setSupportActionBar(toolbar);

        this.actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * Exibir loading
     * <p>
     *     Metodo usado para exibir o loading da aplicação
     * </p>
     */
    public void showLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            hideLoading();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
    }

    /**
     * Esconder loading
     * <p>
     *     Metodo usado para esconder o loading da aplicação
     * </p>
     */
    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * Exibir empty state
     * <p>
     *     Metodo usado para exibir o empty state para dar um feedback para o usuário em caso de erros
     * </p>
     *
     * @param error ApiResponseType a ser exibiddo
     */
    public void showEptyState(ApiResponseType error){
        emptyStateContainer.setVisibility(View.VISIBLE);
        iconError.setImageResource(error.getIconResId());
        txError.setText(error.getMsgError());

        if(!error.isTentarNovamente()){
            btnReTry.setVisibility(View.GONE);
        }else{
            btnReTry.setVisibility(View.VISIBLE);
        }

        if(error == ApiResponseType.NO_INTENET_CONNECTION){
            btnConectar.setVisibility(View.VISIBLE);
        }else{
            btnConectar.setVisibility(View.GONE);
        }

        if(error == ApiResponseType.EMPTY_STATE){
            btnNovaBusca.setVisibility(View.VISIBLE);
            btnReTry.setVisibility(View.GONE);
        }else{
            btnNovaBusca.setVisibility(View.GONE);
        }

        if(error == ApiResponseType.NO_LOCATION_AVAILABLE){
            btnLocation.setVisibility(View.VISIBLE);
        }else{
            btnLocation.setVisibility(View.GONE);
        }
    }

    /**
     * Recupera o Context da Activity
     * <p>
     *     Metodo usado para recuperar o context da Activity
     * </p>
     *
     * @return      Context  context da Activity
     * @see         Context
     */
    public Context getContext(){
        return getApplicationContext();
    }

    /**
     * Conectar
     * <p>
     *     Metodo usado para abrir as configurações do app e ativar as conexões de rede
     * </p>
     */
    @Nullable
    @OnClick(R.id.btnConectar)
    public void connect(){
        startActivity(new Intent(Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK));
    }

    /**
     * Ativar a localização
     * <p>
     *     Metodo usado para abrir as configurações do app e ativar a localização do usuário
     * </p>
     */
    @Nullable
    @OnClick(R.id.btnLocation)
    public void activeLocation(){
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK));
    }
}