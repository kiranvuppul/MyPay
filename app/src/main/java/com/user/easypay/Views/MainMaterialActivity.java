package com.user.easypay.Views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.user.easypay.Fragments.SettingsholderFragment;
import com.user.easypay.Fragments.VendingDevicesholderFragment;
import com.user.easypay.R;

public class MainMaterialActivity extends ActionBarActivity implements MaterialFragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainMaterialActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private MaterialFragmentDrawer drawerFragment;
    // Progress bar
    public ProgressDialog progressDlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (MaterialFragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new VendingDevicesholderFragment();
                title = getString(R.string.title_vending_machine);
                break;
            case 1:
                fragment = new SettingsholderFragment();
                title = getString(R.string.title_settings);
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    // API to show progress with default style
    public void showProgress() {
        if (progressDlg != null && progressDlg.isShowing()) {
            return;
        }

        if (progressDlg == null && !isFinishing()) {
            try {
                progressDlg = ProgressDialog.show(this, null, null, true, false);
                progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDlg.setContentView(new ProgressBar(this, null, android.R.attr.progressBarStyleLarge));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // API to show progress with custom message.
    public void showProgress(String info) {

        if (progressDlg != null && progressDlg.isShowing()) {
            return;
        }

        if (progressDlg == null && !isFinishing()) {
            try {
                progressDlg = ProgressDialog.show(this, null, info, true, false);
                progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // API to stop progress
    public void stopProgress() {
        try {
            if (progressDlg != null && !isFinishing()) {
                progressDlg.dismiss();
                progressDlg = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
