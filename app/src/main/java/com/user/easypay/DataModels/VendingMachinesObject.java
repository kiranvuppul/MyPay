package com.user.easypay.DataModels;

public class VendingMachinesObject {

    private int position;
    private String mVMName;
    private int mVMId;
    private double mAverageValue;
    private int mSignalStrength1;
    private int mSignalStrength2;
    private int mSignalStrength3;
    private boolean isVMUpdated;

    // Constructor to set default objects
    public VendingMachinesObject(int position, String vmName, int vmId, double avgValue, int signalStrength1, int signalStrength2, int signalStrength3) {
        this.position = position;
        this.mVMName = vmName;
        this.mVMId = vmId;
        this.mAverageValue = avgValue;
        this.mSignalStrength1 = signalStrength1;
        this.mSignalStrength2 = signalStrength2;
        this.mSignalStrength3 = signalStrength3;
        this.isVMUpdated = false;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getVMName() {
        return mVMName;
    }

    public void setVMName(String mVMName) {
        this.mVMName = mVMName;
    }

    public int getVMId() {
        return mVMId;
    }

    public void setVMIds(int mVMIds) {
        this.mVMId = mVMIds;
    }

    public double getAverageValue() {
        return mAverageValue;
    }

    public void setAverageValue(double mAverageValue) {
        this.mAverageValue = mAverageValue;
    }

    public int getSignalStrength1() {
        return mSignalStrength1;
    }

    public void setSignalStrength1(int mSignalStrength1) {
        this.mSignalStrength1 = mSignalStrength1;
    }

    public int getSignalStrength2() {
        return mSignalStrength2;
    }

    public void setSignalStrength2(int mSignalStrength2) {
        this.mSignalStrength2 = mSignalStrength2;
    }

    public double getSignalStrength3() {
        return mSignalStrength3;
    }

    public void setSignalStrength3(int mSignalStrength3) {
        this.mSignalStrength3 = mSignalStrength3;
    }

    public boolean isVMUpdated() {
        return isVMUpdated;
    }

    public void setVMUpdated(boolean isVMUpdated) {
        this.isVMUpdated = isVMUpdated;
    }

}
