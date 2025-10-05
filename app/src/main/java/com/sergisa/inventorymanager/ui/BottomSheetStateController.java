package com.sergisa.inventorymanager.ui;

import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BottomSheetStateController<T extends View> {
    private int[] states;
    BottomSheetBehavior<T> bottomSheetBehavior;

    public BottomSheetStateController(BottomSheetBehavior<T> bottomSheetBehavior) {
        this.bottomSheetBehavior = bottomSheetBehavior;
    }

    private int getIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public void increaseState() {
        int index = getIndex(states, bottomSheetBehavior.getState());
        if (index != -1) {
            bottomSheetBehavior.setState(states[index + 1]);
        }
    }

    public void decreaseState() {
        int index = getIndex(states, bottomSheetBehavior.getState());
        if (index != -1) {
            bottomSheetBehavior.setState(states[index - 1]);
        }
    }

    public void setStateSequence(int[] states) {
        this.states = states;
    }
}
