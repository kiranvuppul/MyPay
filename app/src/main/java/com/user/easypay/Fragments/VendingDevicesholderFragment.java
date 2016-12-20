package com.user.easypay.Fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.user.easypay.Adapters.VendingMachinesAdapter;
import com.user.easypay.BaseClass.BaseFragment;
import com.user.easypay.Constants.EasyPayConstants;
import com.user.easypay.Controller.EasyPayController;
import com.user.easypay.DataModels.VendingMachinesObject;
import com.user.easypay.R;
import com.user.easypay.Utils.NetworkUtils;
import com.user.easypay.Utils.VMComparator;
import com.user.easypay.ViewFlowlibrary.CircleFlowIndicator;
import com.user.easypay.ViewFlowlibrary.ViewFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.List;


public class VendingDevicesholderFragment extends BaseFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VendingDevicesholderFragment newInstance(int sectionNumber) {
        VendingDevicesholderFragment fragment = new VendingDevicesholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public VendingDevicesholderFragment() {
    }


    private ViewFlow viewFlow;
    private CircleFlowIndicator indicator;
    private VendingMachinesAdapter mAdapter;

    private VendingMachinesObject tempObj = null;
    private int mPreviousPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.vending_preview_layout, container, false);

        viewFlow = (ViewFlow) rootView.findViewById(R.id.viewflow);

        mAdapter = new VendingMachinesAdapter(getActivity(), EasyPayController.getInstance().getVMCollection());
        viewFlow.setAdapter(mAdapter);
        indicator = (CircleFlowIndicator) rootView.findViewById(R.id.viewflowindic);
        viewFlow.setFlowIndicator(indicator);
        viewFlow.setSelection(0);

        setHasOptionsMenu(true);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.vending_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh:
                // Do Fragment menu item stuff here

                if(NetworkUtils.getInstance().isNetworkAvailable(getActivity())) {
                    EasyPayController.getInstance().resetVMState();
                    updateVMStates();
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.warning_no_network), Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                break;
        }

        return false;
    }

    private void updateVMStates() {

        // Save the old values for updating after reload complete
        mPreviousPosition = viewFlow.getSelectedItemPosition();
        tempObj = mAdapter.getItem(mPreviousPosition);

        List<VendingMachinesObject> myVMColections = EasyPayController.getInstance().getVMCollection();

        showProgress(getActivity().getResources().getString(R.string.info_update_signal_strength));

        if(myVMColections != null && myVMColections.size() > 0) {
            for (VendingMachinesObject vMAchine : myVMColections) {
                // We use serial pool to make API requests in serial one after another
                new updateVMList().executeOnExecutor( AsyncTask.SERIAL_EXECUTOR, vMAchine);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        stopProgress();
    }

    private class updateVMList extends AsyncTask<VendingMachinesObject, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(VendingMachinesObject... vmMachine) {

            boolean retVal = false;
            String ss1 = null, ss2 = null, ss3 = null;

            String url = EasyPayConstants.RANDOM_REST_URL;

            URL obj = null;
            try {
                obj = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) obj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // optional default is GET
            try {
                con.setRequestMethod(EasyPayConstants.HTTP_GET);
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            //add request header
            int responseCode = 0;
            try {
                responseCode = con.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(responseCode == EasyPayConstants.HTTP_OK) {

                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String inputLine;

                try {
                    while ((inputLine = in.readLine()) != null) {

                        if(ss1 == null) {
                            ss1 = inputLine;
                        }
                        else if (ss2 == null) {
                            ss2 = inputLine;
                        }
                        else if( ss3 == null) {
                            ss3 = inputLine;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                EasyPayController.getInstance().updateVendingMachine(vmMachine[0], ss1, ss2, ss3);

                boolean isUpdate = EasyPayController.getInstance().isVMStatesUpdated();
                if(isUpdate) {
                    List<VendingMachinesObject> myVMList = EasyPayController.getInstance().getVMCollection();
                    Collections.sort(myVMList, new VMComparator());
                    EasyPayController.getInstance().updateVMStates(myVMList);

                    retVal = true;
                }

            } else {
                stopProgress();
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_network_error), Toast.LENGTH_LONG).show();

                retVal = false;
            }

            return retVal;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result) {

                stopProgress();

                if (EasyPayController.getInstance().isFixed()) {

                    for (VendingMachinesObject vMachine : EasyPayController.getInstance().getVMCollection()) {

                       if(tempObj != null && TextUtils.equals(tempObj.getVMName(), vMachine.getVMName())) {
                           viewFlow.setSelection(mPreviousPosition);
                           break;
                       }
                    }

                } else {

                    int temp = 0;
                    for (VendingMachinesObject vMachine : EasyPayController.getInstance().getVMCollection()) {
                        if(tempObj != null && TextUtils.equals(tempObj.getVMName(), vMachine.getVMName())) {
                            viewFlow.setSelection(temp);
                            break;
                        }
                        temp++;
                    }
                }

                // More optimized code... bringing two cases to one.
                /*
                int count = 0;
                for (VendingMachinesObject vMachine : EasyPayController.getInstance().getVMCollection()) {

                    if(tempObj != null && TextUtils.equals(tempObj.getVMName(), vMachine.getVMName())) {

                        if (EasyPayController.getInstance().isFixed()) {
                            viewFlow.setSelection(mPreviousPosition);
                            break;
                        } else {
                            viewFlow.setSelection(count);
                            break;
                        }
                    }
                    count++;
                }
                */

                viewFlow.invalidate();
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}
