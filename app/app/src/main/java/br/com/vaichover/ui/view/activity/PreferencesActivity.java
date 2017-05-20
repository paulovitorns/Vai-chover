package br.com.vaichover.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import br.com.vaichover.R;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.GPlace;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.PreferencesPresenter;
import br.com.vaichover.ui.presenter.impl.PreferencesPresenterImpl;
import br.com.vaichover.ui.view.PreferencesView;
import br.com.vaichover.ui.view.componet.CustomDialog;
import br.com.vaichover.util.EditTextValidadeUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class PreferencesActivity extends BaseActivity implements PreferencesView{

    @Bind(R.id.rangeSeekbar)    CrystalSeekbar  rangeSeekbar;
    @Bind(R.id.txInitial)       TextView        txInitial;
    @Bind(R.id.txLast)          TextView        txLast;

    @Bind(R.id.edtLayoutLocation)   TextInputLayout edtLayoutLocation;
    @Bind(R.id.edtLocation)         EditText        edtLocation;
    @Bind(R.id.edtLoadingIndicator) ProgressBar     edtLoadingIndicator;
    @Bind(R.id.iconClear)           ImageButton     iconClear;
    @Bind(R.id.containerSugests)    LinearLayout    containerSugests;
    @Bind(R.id.recyclerView)        RecyclerView    recyclerView;
    @Bind(R.id.txErrorPlaces)       TextView        txErrorPlaces;
    @Bind(R.id.btnGetLatLocation)   Button          btnGetLatLocation;

    @Bind(R.id.containerButtons)            LinearLayout    containerButtons;
    @Bind(R.id.containerCheckCelsius)       LinearLayout    containerCheckCelsius;
    @Bind(R.id.containerCheckFahrenheit)    LinearLayout    containerCheckFahrenheit;

    @Bind(R.id.imgCheckCelsius)     ImageView imgCheckCelsius;
    @Bind(R.id.imgCheckFahrenheit)  ImageView imgCheckFahrenheit;

    @Bind(R.id.iconCelsius)     ImageView iconCelsius;
    @Bind(R.id.iconFahrenheit)  ImageView iconFahrenheit;

    @Bind(R.id.txCelsius)       TextView txCelsius;
    @Bind(R.id.txFahrenheit)    TextView txFahrenheit;

    @Bind(R.id.btnSave)         Button btnSave;

    private UserPreferences         user;
    private PreferencesPresenter    presenter;
    private UserPreferences.Degrees degreesSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

        ButterKnife.bind(this);

        setupNavigateActionBarHome(R.string.window_preferences);

        // set listener
        rangeSeekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                txInitial.setText(String.valueOf(minValue)+" km");
            }
        });

        // set final value listener
        rangeSeekbar.setOnSeekbarFinalValueListener(new OnSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number value) {
                txInitial.setText(String.valueOf(value));
            }
        });

        if(savedInstanceState != null) {
            user = (UserPreferences) savedInstanceState.getSerializable(UserPreferences.KEY);
        }else {
            if(getIntent().getExtras() != null)
                user = (UserPreferences) getIntent().getExtras().getSerializable(UserPreferences.KEY);
        }

        this.presenter = new PreferencesPresenterImpl(this, user);

        edtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(containerButtons.getVisibility() == View.VISIBLE)
                    containerButtons.setVisibility(View.GONE);

                if(editable.toString().length() > 3) {
                    if(!editable.toString().equalsIgnoreCase(user.getPlace().getResult().getFormatted_address())){
                        presenter.requestPlaces(editable.toString());
                        containerSugests.setVisibility(View.VISIBLE);
                    }else{
                        if(containerButtons.getVisibility() == View.GONE)
                            containerButtons.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable(UserPreferences.KEY, user);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.close_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_fechar){
            finish();
            overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
        }

        return true;
    }

    @Override
    public void setDataInfoUser(UserPreferences user) {

        this.user = user;

        if(this.user.getPlace() != null && ( this.user.getPlace().getResult().getFormatted_address() != null || !this.user.getPlace().getResult().getFormatted_address().isEmpty() ) )
            edtLocation.setText(this.user.getPlace().getResult().getFormatted_address());

        if(this.user.getRadius() > 0){
            rangeSeekbar.setMinStartValue(this.user.getRadius()).apply();
            txInitial.setText(String.valueOf(user.getRadius())+" km");
        }
    }

    @OnClick(R.id.containerCheckCelsius)
    @Override
    public void setCelsiusDegrees() {

        degreesSelected = UserPreferences.Degrees.CELSIUS;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            containerCheckCelsius.setBackground(getDrawable(R.drawable.view_checked_type));
            containerCheckFahrenheit.setBackground(getDrawable(R.drawable.view_check_type));
        }else{
            containerCheckCelsius.setBackground(getResources().getDrawable(R.drawable.view_checked_type));
            containerCheckFahrenheit.setBackground(getResources().getDrawable(R.drawable.view_check_type));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            iconCelsius.setColorFilter(getColor(R.color.white));
            iconFahrenheit.setColorFilter(getColor(R.color.colorPrimaryDark));
            txCelsius.setTextColor(getColor(R.color.white));
            txFahrenheit.setTextColor(getColor(R.color.grey));
        }else{
            iconCelsius.setColorFilter(getResources().getColor(R.color.white));
            iconFahrenheit.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
            txCelsius.setTextColor(getResources().getColor(R.color.white));
            txFahrenheit.setTextColor(getResources().getColor(R.color.grey));
        }

        imgCheckCelsius.setVisibility(View.VISIBLE);
        imgCheckFahrenheit.setVisibility(View.GONE);
    }

    @OnClick(R.id.containerCheckFahrenheit)
    @Override
    public void setFahrenheitDegrees() {

        degreesSelected = UserPreferences.Degrees.FAHRENHEIT;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            containerCheckFahrenheit.setBackground(getDrawable(R.drawable.view_checked_type));
            containerCheckCelsius.setBackground(getDrawable(R.drawable.view_check_type));
        }else{
            containerCheckFahrenheit.setBackground(getResources().getDrawable(R.drawable.view_checked_type));
            containerCheckCelsius.setBackground(getResources().getDrawable(R.drawable.view_check_type));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            iconFahrenheit.setColorFilter(getColor(R.color.white));
            iconCelsius.setColorFilter(getColor(R.color.colorPrimaryDark));
            txFahrenheit.setTextColor(getColor(R.color.white));
            txCelsius.setTextColor(getColor(R.color.grey));
        }else{
            iconFahrenheit.setColorFilter(getResources().getColor(R.color.white));
            iconCelsius.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
            txFahrenheit.setTextColor(getResources().getColor(R.color.white));
            txCelsius.setTextColor(getResources().getColor(R.color.grey));
        }

        imgCheckCelsius.setVisibility(View.GONE);
        imgCheckFahrenheit.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnSave)
    @Override
    public void onClickBtnSave() {

        if(this.user.getPlace() == null)
            this.user.setPlace(new GPlace());

        this.user.getPlace().getResult().setFormatted_address(edtLocation.getText().toString());
        String value = txInitial.getText().toString();
        value = value.replace(" km", "");
        this.user.setRadius(Integer.parseInt(value));
        this.user.setDegrees(degreesSelected);

        this.presenter.sendToRegister(this.user);
    }

    @Override
    public void setLocaleEmptyError() {
        EditTextValidadeUtils.setErrorToView(edtLocation, getContext());
        edtLayoutLocation.setErrorEnabled(true);
        edtLayoutLocation.setError(getString(R.string.error_empty_locale));
    }

    @Override
    public void setLocaleDefaultState() {
        EditTextValidadeUtils.setNormalStateToView(edtLocation, getContext());
        edtLayoutLocation.setErrorEnabled(false);
    }

    @Override
    public void showSuccessDialog() {
        String title    = getString(R.string.dialog_preferences_title);
        String msg      = getString(R.string.dialog_preferences_description);

        CustomDialog customDialog = new CustomDialog(this, title, msg, this);
        customDialog.show();
    }

    @Override
    public void dialogError(String title, String msg) {
        CustomDialog customDialog = new CustomDialog(this, title, msg, this);
        customDialog.show();
    }

    @Override
    public void navigateToDash() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
    }

    @Override
    public void showProgress() {
        iconClear.setVisibility(View.GONE);
        edtLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        iconClear.setVisibility(View.VISIBLE);
        edtLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void loadAdapter() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void setPlacesAdapter(RecyclerView.Adapter adapter) {
        hideErrorPlaces();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setErrorPlaces(ApiResponseType error) {
        btnGetLatLocation.setVisibility(View.VISIBLE);
        txErrorPlaces.setVisibility(View.VISIBLE);
        txErrorPlaces.setText(error.getMsgError());
    }

    @Override
    public void hideErrorPlaces() {
        btnGetLatLocation.setVisibility(View.GONE);
        txErrorPlaces.setVisibility(View.GONE);
    }

    @Override
    public void setPlaceSuggest(GPlace place) {
        this.user.setPlace(place);
        containerButtons.setVisibility(View.VISIBLE);
        edtLocation.setText(place.getResult().getFormatted_address());
        containerSugests.setVisibility(View.GONE);
        edtLocation.setSelection(edtLocation.getText().length());

        // hide keypad
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        btnSave.requestFocus();
    }

    @OnClick(R.id.iconClear)
    public void clearPlace(){
        edtLocation.setText("");
    }

    @OnClick(R.id.btnGetLatLocation)
    public void btnGetLatLocation(){
        presenter.setLastUserLocation();
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }
}
