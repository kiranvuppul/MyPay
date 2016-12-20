package com.user.easypay.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.user.easypay.Adapters.DragNDropAdapter;
import com.user.easypay.BaseClass.BaseFragment;
import com.user.easypay.Controller.EasyPayController;
import com.user.easypay.Listeners.DragListener;
import com.user.easypay.Listeners.DragNDropListView;
import com.user.easypay.Listeners.DropListener;
import com.user.easypay.Listeners.RemoveListener;
import com.user.easypay.R;


public class SettingsholderFragment extends BaseFragment {


    public SettingsholderFragment() {
    }

    private CheckBox isFixed;
    private CheckBox isRandom;

    private ListView mVMListView;
    private DragNDropAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings_main, container, false);

        isFixed = (CheckBox) rootView.findViewById(R.id.vm_fixed);
        isRandom = (CheckBox) rootView.findViewById(R.id.vm_random);

        mVMListView= (ListView) rootView.findViewById(R.id.vending_machines);

        mAdapter = new DragNDropAdapter(getActivity());

        mVMListView.setAdapter(mAdapter);

        if (mVMListView instanceof DragNDropListView) {
            ((DragNDropListView) mVMListView).setDropListener(mDropListener);
            ((DragNDropListView) mVMListView).setRemoveListener(mRemoveListener);
            ((DragNDropListView) mVMListView).setDragListener(mDragListener);
        }

        isFixed.setOnClickListener(userActionListener);
        isRandom.setOnClickListener(userActionListener);

        showProgress();
        updateUserPreferences();

        return rootView;
    }

    private void updateUserPreferences() {

        if(EasyPayController.getInstance().isFixed()) {
            isFixed.setChecked(true);
            isRandom.setChecked(false);
        } else {
            isFixed.setChecked(false);
            isRandom.setChecked(true);
        }

        stopProgress();
    }

    View.OnClickListener userActionListener = new View.OnClickListener() {

        public void onClick(final View v) {

        switch(v.getId()) {

            case R.id.vm_fixed:
                EasyPayController.getInstance().setVendingMachineOrder(true);
                break;

            case R.id.vm_random:
                EasyPayController.getInstance().setVendingMachineOrder(false);
                break;

        }

        updateUserPreferences();
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private DropListener mDropListener = new DropListener() {
        public void onDrop(int from, int to) {

            if (mAdapter instanceof DragNDropAdapter) {
                ((DragNDropAdapter)mAdapter).onDrop(from, to);
                mVMListView.invalidateViews();
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    private RemoveListener mRemoveListener = new RemoveListener() {
        public void onRemove(int which) {

            if (mAdapter instanceof DragNDropAdapter) {
                ((DragNDropAdapter)mAdapter).onRemove(which);
                mVMListView.invalidateViews();
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    private DragListener mDragListener = new DragListener() {

        int backgroundColor = 0xe0103010;
        int defaultBackgroundColor;

        public void onDrag(int x, int y, ListView listView) {
            // TODO Auto-generated method stub
        }

        public void onStartDrag(View itemView) {
            itemView.setVisibility(View.INVISIBLE);
            defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
            itemView.setBackgroundColor(backgroundColor);
            ImageView iv = (ImageView)itemView.findViewById(R.id.ImageView01);
            if (iv != null) iv.setVisibility(View.INVISIBLE);
        }

        public void onStopDrag(View itemView) {
            itemView.setVisibility(View.VISIBLE);
            itemView.setBackgroundColor(defaultBackgroundColor);
            ImageView iv = (ImageView)itemView.findViewById(R.id.ImageView01);
            if (iv != null) iv.setVisibility(View.VISIBLE);
        }

    };

}
