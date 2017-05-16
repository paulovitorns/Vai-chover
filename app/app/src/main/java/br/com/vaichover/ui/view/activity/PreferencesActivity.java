package br.com.vaichover.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import br.com.vaichover.R;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.PreferencesPresenter;
import br.com.vaichover.ui.presenter.impl.PreferencesPresenterImpl;
import br.com.vaichover.ui.view.PreferencesView;
import br.com.vaichover.ui.view.componet.CustomDialog;
import br.com.vaichover.util.EditTextValidadeUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreferencesActivity extends BaseActivity implements PreferencesView{

    @Bind(R.id.rangeSeekbar)    CrystalSeekbar  rangeSeekbar;
    @Bind(R.id.txInitial)       TextView        txInitial;
    @Bind(R.id.txLast)          TextView        txLast;

    @Bind(R.id.edtLayoutLocation)   TextInputLayout         edtLayoutLocation;
    @Bind(R.id.edtLocation)         AutoCompleteTextView    edtLocation;

    private View                    view;
    private UserPreferences         user;
    private PreferencesPresenter    presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

        ButterKnife.bind(this);

        setupNavigateActionBarModal(R.string.window_preferences);

        user = new UserPreferences();

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

        this.presenter = new PreferencesPresenterImpl(this);

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

        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
        }

        return true;
    }

    @Override
    public void setDataInfoUser(UserPreferences user) {

        this.user = user;

        if(user.getAddress() != null || !user.getAddress().isEmpty())
            edtLocation.setText(user.getAddress());
    }

    @OnClick(R.id.btnSave)
    @Override
    public void onClickBtnSave() {

        this.user.setAddress(edtLocation.getText().toString());
        String value = txInitial.getText().toString();
        value = value.replace(" km", "");
        this.user.setRadius(Integer.parseInt(value));

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

        String title    = "Olá, "+user.getName()+"!";
        String msg      = "Seus dados foram salvos com sucesso.";

        CustomDialog customDialog = new CustomDialog(getContext(), title, msg);
        customDialog.show();
    }

    @Override
    public void dialogError(String title, String msg) {
        CustomDialog customDialog = new CustomDialog(getContext(), title, msg);
        customDialog.show();
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
