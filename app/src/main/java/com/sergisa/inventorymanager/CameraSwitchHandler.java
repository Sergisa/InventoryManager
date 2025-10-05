package com.sergisa.inventorymanager;

import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

public class CameraSwitchHandler {
    ToggleButton togglingButton;
    private int currentState;
    private int[] states;
    CameraSelectionChangedListener selectionListener;

    public CameraSwitchHandler(ToggleButton button, int initialState, int oppositeState) {
        togglingButton = button;
        currentState = initialState;
        states = new int[]{initialState, oppositeState};
        togglingButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentState = getOppositeState(currentState);
            if (selectionListener != null) selectionListener.onChange(currentState);
        });
    }

    private int getOppositeState(int startState) {
        for (int state : states) {
            if (state != startState) {
                return state;
            }
        }
        return currentState;
    }

    public int getCameraSelection() {
        return currentState;
    }

    public void setSelectionListener(CameraSelectionChangedListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public interface CameraSelectionChangedListener {
        void onChange(int cameraSelection);
    }
}

