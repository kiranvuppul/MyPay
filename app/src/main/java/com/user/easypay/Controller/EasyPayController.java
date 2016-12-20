package com.user.easypay.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;

import com.user.easypay.Constants.EasyPayConstants;
import com.user.easypay.DataModels.VendingMachinesObject;
import com.user.easypay.R;

import java.util.LinkedList;
import java.util.List;

public class EasyPayController {

    private static EasyPayController instance  = null;
    private Context mContext;

    public List<VendingMachinesObject> myVMList;

    private final static String PREF_NAME               = "easypay";
    private final static String PREF_KEY_IS_FIXED       = "pref_is_fixed";

    public static EasyPayController getInstance(){
        if(instance == null)
            instance = new EasyPayController();
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
        initializeVM();
    }

    // True for Fixed False for Shuffled
    public void setVendingMachineOrder(boolean isFixed) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(PREF_KEY_IS_FIXED, isFixed).commit();
    }

    // Get the user VM status
    public boolean isFixed() {
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(PREF_KEY_IS_FIXED, true);
    }

    // Initialize VM machinges and its data.
    public void initializeVM() {

        myVMList = new LinkedList<>();

        TypedArray mVendingMachineIcons = mContext.getResources().obtainTypedArray(R.array.vending_machines);

        String[] mVMNames = mContext.getResources().getStringArray(R.array.vm_machines_names);

        for(int i=0; i<EasyPayConstants.DEFAULT_VM_COUNT; i++) {

            VendingMachinesObject vmObj = new VendingMachinesObject( i,
                    mVMNames[i],
                    mVendingMachineIcons.getResourceId(i, -1),
                    EasyPayConstants.DEFAULT_AVERAGE_VALUE,
                    EasyPayConstants.DEFAULT_SIGNAL_VALUE,
                    EasyPayConstants.DEFAULT_SIGNAL_VALUE,
                    EasyPayConstants.DEFAULT_SIGNAL_VALUE );

            myVMList.add(vmObj);
        }
    }

    // API to return list of Vending Machine Objects
    public List<VendingMachinesObject> getVMCollection() {
        return myVMList;
    }

    // API to update Vending Machine Objects
    public void updateVMStates(List<VendingMachinesObject> newCollections) {
        myVMList = newCollections;
    }


    // API to reset vm update state to false.
    public void resetVMState() {

        if(myVMList != null) {
            for (VendingMachinesObject vMachine : myVMList) {
                vMachine.setVMUpdated(false);
            }
        }
    }

    // API to check if all the VMs are updated.
    public boolean isVMStatesUpdated() {

        boolean retVal = true;

        if(myVMList != null) {
            for (VendingMachinesObject vMachine : myVMList) {
                if(!vMachine.isVMUpdated()) {
                    retVal = false;
                    break;
                }
            }
        }
        return retVal;
    }

    // API to update Vending Machine signal strength
    public void updateVendingMachine(VendingMachinesObject vm, String SS1, String SS2, String SS3) {

        int ss1 = 0, ss2 = 0, ss3 = 0;
        double avg = 0.0;

        if(myVMList != null) {
            for (VendingMachinesObject vMachine : myVMList) {

                if(vMachine.getPosition() == vm.getPosition()) {

                    vMachine.setVMUpdated(true);

                    if(SS1 != null) {
                        ss1 = Integer.parseInt(SS1);
                        vMachine.setSignalStrength1(ss1);
                    }
                    if(SS2 != null) {
                        ss2 = Integer.parseInt(SS2);
                        vMachine.setSignalStrength2(ss2);

                    }
                    if(SS3 != null) {
                        ss3 = Integer.parseInt(SS3);
                        vMachine.setSignalStrength3(ss3);
                    }

                    avg = (ss1 + ss2 + ss3) / 3 ;
                    vMachine.setAverageValue(avg);
                }
            }
        }
    }

}
