package br.com.vaichover.ui.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import java.io.File;
import java.io.IOException;

import br.com.vaichover.BuildConfig;
import br.com.vaichover.R;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.PreferencesPresenter;
import br.com.vaichover.ui.presenter.impl.PreferencesPresenterImpl;
import br.com.vaichover.ui.view.DashBoardView;
import br.com.vaichover.ui.view.PreferencesView;
import br.com.vaichover.ui.view.componet.CustomDialog;
import br.com.vaichover.util.EditTextValidadeUtils;
import br.com.vaichover.util.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class PreferencesFragment extends Fragment implements PreferencesView {

    @Bind(R.id.rangeSeekbar)        CrystalSeekbar  rangeSeekbar;
    @Bind(R.id.txInitial)           TextView        txInitial;
    @Bind(R.id.txLast)              TextView        txLast;

    @Bind(R.id.edtLayoutName)       TextInputLayout         edtLayoutName;
    @Bind(R.id.edtLayoutLocation)   TextInputLayout         edtLayoutLocation;
    @Bind(R.id.edtName)             EditText                edtName;
    @Bind(R.id.edtLocation)         AutoCompleteTextView    edtLocation;
    @Bind(R.id.imgProfile)          CircleImageView         imageView;

    private View view;
    private UserPreferences user;
    private PreferencesPresenter presenter;
    static final int REQUEST_IMAGE_CAPTURE  = 1;
    static final int REQUEST_RESULT_CODE    = -1;

    private int targetW;
    private int targetH;

    public PreferencesFragment() {
    }

    public static PreferencesFragment newInstance(){
        return new PreferencesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_preferences, container, false);

        ButterKnife.bind(this, view);

        ButterKnife.bind(this, view);

        targetH = Utils.dp2px((int) (getResources().getDimension(R.dimen.img_profile_size) / getResources().getDisplayMetrics().density));
        targetW = Utils.dp2px((int) (getResources().getDimension(R.dimen.img_profile_size) / getResources().getDisplayMetrics().density));

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

        return view;
    }

    @Override
    public void showLoading() {
        ((DashBoardView)getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((DashBoardView)getActivity()).hideLoading();
    }

    @Override
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public void setDataInfoUser(UserPreferences user) {

        this.user = user;

        if(user.getName() != null || !user.getName().isEmpty())
            edtName.setText(user.getName());

        if(user.getAddress() != null || !user.getAddress().isEmpty())
            edtLocation.setText(user.getAddress());

        if(user.getImgNameResource() != null){
            if(!user.getImgNameResource().isEmpty())
                setPic();
        }
    }

    @OnClick(R.id.btnSave)
    @Override
    public void onClickBtnSave() {

        this.user.setName(edtName.getText().toString());
        this.user.setAddress(edtLocation.getText().toString());
        String value = txInitial.getText().toString();
        value = value.replace(" km", "");
        this.user.setRadius(Integer.parseInt(value));

        this.presenter.sendToRegister(this.user);
    }

    @Override
    public void setNameEmptyError() {
        EditTextValidadeUtils.setErrorToView(edtName, getContext());
        edtLayoutName.setErrorEnabled(true);
        edtLayoutName.setError(getString(R.string.error_empty_name));
    }

    @Override
    public void setNomeDefaultState() {
        EditTextValidadeUtils.setNormalStateToView(edtName, getContext());
        edtLayoutName.setErrorEnabled(false);
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

        ((DashBoardView)getActivity()).setUserInfo(user);
    }

    @OnClick(R.id.fabCamera)
    @Override
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID+".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "profile_image";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        user.setImgNameResource(image.getAbsolutePath());

        return image;
    }

    @Override
    public void setPic() {
        // Get the dimensions of the View

        if(targetW == 0)
            targetW = imageView.getMeasuredWidth();

        if(targetH == 0)
            targetH = imageView.getMeasuredHeight();

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

        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void dialogError(String title, String msg) {
        CustomDialog customDialog = new CustomDialog(getContext(), title, msg);
        customDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == REQUEST_RESULT_CODE) {

            if(user.getName() != null){
                if(!user.getName().isEmpty()){
//                    presenter.updateImagemUser(user);
                }
            }
            setPic();
        }
    }
}
