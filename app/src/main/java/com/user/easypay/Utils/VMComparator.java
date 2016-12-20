package com.user.easypay.Utils;

import com.user.easypay.DataModels.VendingMachinesObject;

import java.util.Comparator;

/*
    Class used to compare Vending Machine objects
*/
public class VMComparator implements Comparator<VendingMachinesObject> {
    @Override
    public int compare(VendingMachinesObject VM1, VendingMachinesObject VM2) {

        if (VM1 == null || VM2 == null)
            return 0;

        if(VM1.getAverageValue() > VM2.getAverageValue()) {
            return -1;
        } else if(VM1.getAverageValue() <  VM2.getAverageValue()) {
            return 1;
        }
        return 0;
    }

}

