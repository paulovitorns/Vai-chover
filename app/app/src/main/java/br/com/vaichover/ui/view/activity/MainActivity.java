package br.com.vaichover.ui.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.com.vaichover.R;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.MainPresenter;
import br.com.vaichover.ui.presenter.impl.MainPresenterImpl;
import br.com.vaichover.ui.view.DashBoardView;
import br.com.vaichover.ui.view.fragment.MapsFragment;
import br.com.vaichover.ui.view.fragment.PreferencesFragment;
import br.com.vaichover.util.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashBoardView {

    @Bind(R.id.drawer_layout)   DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)        NavigationView navigationView;

    private CircleImageView imgProfile;

    private boolean confirmedExit = false;
    private Menu    menu;
    private int     targetW;
    private int     targetH;
    private UserPreferences user;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupNavigateActionBar(R.string.window_dash);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        this.presenter = new MainPresenterImpl(this);

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
            this.loadDefaultFragment();
        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_preferences) {
            this.changeFragment(PreferencesFragment.newInstance());
        } else if (id == R.id.nav_logout) {
            this.finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setUserInfo(UserPreferences user) {
        this.user = user;

        TextView txName     = (TextView) navigationView.findViewById(R.id.txNameUser);
        TextView txLocation = (TextView) navigationView.findViewById(R.id.txLocationUser);
        this.imgProfile     = (CircleImageView) navigationView.findViewById(R.id.imgProfile);

        if(user.getName() != null || !user.getName().isEmpty())
            txName.setText(user.getName());

        if(user.getAddress() != null || !user.getAddress().isEmpty())
            txLocation.setText(user.getAddress());

        targetH = Utils.dp2px((int) (getResources().getDimension(R.dimen.img_profile_drawer_size) / getResources().getDisplayMetrics().density));
        targetW = Utils.dp2px((int) (getResources().getDimension(R.dimen.img_profile_drawer_size) / getResources().getDisplayMetrics().density));

        if(user.getImgNameResource() != null){
            if(!user.getImgNameResource().isEmpty())
                setPic();
        }
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

    @Override
    public void setPic() {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(user.getImgNameResource(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(user.getImgNameResource(), bmOptions);
        imgProfile.setImageBitmap(bitmap);
    }
}
