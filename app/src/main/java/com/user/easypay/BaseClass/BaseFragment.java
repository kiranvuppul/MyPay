package com.user.easypay.BaseClass;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.user.easypay.Views.MainMaterialActivity;

public class BaseFragment extends Fragment {

    public static final String TAG = "EasyPay";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Remember the position of the selected item.
     */
    public static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    public static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    // API to show progress bar.
    protected void showProgress() {
        MainMaterialActivity activity = (MainMaterialActivity) getActivity();
        if (activity != null && isVisible()) {
            activity.showProgress();
        }
    }

    // API to show progress bar with custom message.
    protected void showProgress(String msg) {
        MainMaterialActivity activity = (MainMaterialActivity) getActivity();
        if (activity != null && isVisible()) {
            activity.showProgress(msg);
        }
    }

    // API to stop progress bar.
    protected void stopProgress() {
        MainMaterialActivity activity = (MainMaterialActivity) getActivity();
        if (activity != null) {
            activity.stopProgress();
        }
    }

}
