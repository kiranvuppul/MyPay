package com.user.easypay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.user.easypay.DataModels.VendingMachinesObject;
import com.user.easypay.R;

import java.util.List;

public class VendingMachinesAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    public List<VendingMachinesObject> myVMColections;

    public VendingMachinesAdapter(Context context, List<VendingMachinesObject> vms) {

        this.mContext       = context;
        this.myVMColections = vms;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        int retVal = 0;

        if(myVMColections != null) {
            retVal = myVMColections.size();
        }
        return retVal;
    }

    @Override
    public VendingMachinesObject getItem(int position) {
        return myVMColections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Holder for each row.
    private static class ViewHolder {

        private ImageView mVMpicture;
        private TextView mVMAverage;
        private TextView mSignalStrength1;
        private TextView mSignalStrength2;
        private TextView mSignalStrength3;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder pageHolder;

        if (convertView == null) {

            pageHolder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.vending_items, null);
            pageHolder.mVMpicture = (ImageView)  convertView.findViewById(R.id.imgView);

            pageHolder.mVMAverage       = (TextView)  convertView.findViewById(R.id.vm_average);
            pageHolder.mSignalStrength1 = (TextView)  convertView.findViewById(R.id.vm_signal_1);
            pageHolder.mSignalStrength2 = (TextView)  convertView.findViewById(R.id.vm_signal_2);
            pageHolder.mSignalStrength3 = (TextView)  convertView.findViewById(R.id.vm_signal_3);

            convertView.setTag(R.string.list_tag_1, pageHolder);

        } else {
            pageHolder = (ViewHolder) convertView.getTag(R.string.list_tag_1);
        }

        VendingMachinesObject vmObject = myVMColections.get(position);

        if(vmObject != null) {
            // Bind the data efficiently with the holder.
            pageHolder.mVMAverage.setText(mContext.getResources().getString(R.string.vm_average) + vmObject.getAverageValue());
            pageHolder.mSignalStrength1.setText(mContext.getResources().getString(R.string.vm_ss_1) + vmObject.getSignalStrength1() );
            pageHolder.mSignalStrength2.setText(mContext.getResources().getString(R.string.vm_ss_2) + vmObject.getSignalStrength2() );
            pageHolder.mSignalStrength3.setText(mContext.getResources().getString(R.string.vm_ss_3) + vmObject.getSignalStrength3() );

            pageHolder.mVMpicture.setImageResource(vmObject.getVMId());

        }

        return convertView;
    }

}
