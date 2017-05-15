package br.com.vaichover.ui.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.vaichover.R;
import br.com.vaichover.ui.view.DashBoardView;
import br.com.vaichover.ui.view.fragment.MapsFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashBoardView {

    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;

    private boolean confirmedExit = false;
    private Menu                menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupNavigateActionBar(R.string.window_dash);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadDefaultFragment();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if(confirmedExit) {
                finish();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.toast_exit_alert), Toast.LENGTH_SHORT).show();
                loadDefaultFragment();
                confirmedExit = true;
            }
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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            
        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_preferences) {

        } else if (id == R.id.nav_logout) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadDefaultFragment() {

        MapsFragment fragment = MapsFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void changeFragment(Fragment fragment) {

    }

    @Override
    public void onTapSearch() {

    }

    @Override
    public void onTapSettings() {

    }

    @Override
    public void onTapDashMap() {

    }

    @Override
    public void onTapDashList() {

    }
}
