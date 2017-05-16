package br.com.vaichover.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.vaichover.R;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.MainPresenter;
import br.com.vaichover.ui.presenter.impl.MainPresenterImpl;
import br.com.vaichover.ui.view.DashBoardView;
import br.com.vaichover.ui.view.fragment.MapsFragment;
import butterknife.ButterKnife;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class MainActivity extends BaseActivity implements DashBoardView {

    private boolean confirmedExit = false;
    private Menu    menu;
    private UserPreferences user;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupNavigateActionBarHome(R.string.window_dash);

        this.presenter = new MainPresenterImpl(this);

    }

    @Override
    public void onBackPressed() {
        if(confirmedExit) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_exit_alert), Toast.LENGTH_SHORT).show();
            loadDefaultFragment();
            confirmedExit = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_list) {
            item.setVisible(false);
            MenuItem mapsMenu = menu.findItem(R.id.action_gmaps);
            mapsMenu.setVisible(true);
            return true;
        }

        if (id == R.id.action_gmaps) {
            item.setVisible(false);
            MenuItem listMenu = menu.findItem(R.id.action_list);
            listMenu.setVisible(true);
            return true;
        }

        if(id == R.id.action_scale){
            startActivity(new Intent(this, PreferencesActivity.class));
            overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadDefaultFragment() {

        confirmedExit = true;

        MapsFragment fragment = MapsFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void changeFragment(Fragment fragment) {
        if(confirmedExit)
            confirmedExit = false;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onTapDashMap() {

    }

    @Override
    public void onTapDashList() {

    }

}
