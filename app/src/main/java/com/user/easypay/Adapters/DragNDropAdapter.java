package com.user.easypay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.user.easypay.Controller.EasyPayController;
import com.user.easypay.DataModels.VendingMachinesObject;
import com.user.easypay.Listeners.DropListener;
import com.user.easypay.Listeners.RemoveListener;
import com.user.easypay.R;

import java.util.List;

public final class DragNDropAdapter extends BaseAdapter implements RemoveListener, DropListener {

    private static final String TAG = "DragNDropAdapter" ;

    private Context mContext;
    private LayoutInflater mInflater;

    public List<VendingMachinesObject> myVMColections;

    public DragNDropAdapter(Context context) {

        mContext            = context;
        myVMColections      = EasyPayController.getInstance().getVMCollection();
        mInflater           = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    /**
     * The number of items in the list
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        return myVMColections.size();
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficient to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public VendingMachinesObject getItem(int position) {
        return myVMColections.get(position);
    }

    /**
     * Use the array index as a unique id.
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {

            // convertView = mInflater.inflate(mLayouts[0], null);
            convertView = mInflater.inflate(R.layout.vendingmachineslist, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.vmImage = (ImageView) convertView.findViewById(R.id.vmImage);
            holder.vmName = (TextView) convertView.findViewById(R.id.vm_name);
            holder.vmAvgSS = (TextView) convertView.findViewById(R.id.average_ss);

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        VendingMachinesObject vmObject = myVMColections.get(position);
        if(vmObject != null) {

            // Bind the data efficiently with the holder.
            holder.vmImage.setImageResource(vmObject.getVMId());
            holder.vmName.setText(vmObject.getVMName());
            String average = String.valueOf(vmObject.getAverageValue());
            if(average != null) {
                holder.vmAvgSS.setText(mContext.getResources().getString(R.string.vm_average_ss) + average);
            }
        }

        return convertView;
    }

    static class ViewHolder {
        private ImageView vmImage;
        private TextView vmName;
        private TextView vmAvgSS;
    }

    // API to remove the current selection.
    public void onRemove(int which) {

        if (which < 0 || which > myVMColections.size()) return;
        myVMColections.remove(which);
    }

    // API to move the object to targeted position
    public void onDrop(int from, int to) {

        VendingMachinesObject temp = myVMColections.get(from);
        myVMColections.remove(from);
        myVMColections.add(to,temp);

        // Update global list after rearrangement.
        EasyPayController.getInstance().updateVMStates(myVMColections);
    }

}
