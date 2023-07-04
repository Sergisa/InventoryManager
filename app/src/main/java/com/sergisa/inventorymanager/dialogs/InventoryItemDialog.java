package com.sergisa.inventorymanager.dialogs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.db.Inventory;

public class InventoryItemDialog extends DialogFragment {
    View rootView;
    Inventory partialInventory;
    EditText inventoryNumberEditText;
    EditText inventoryNameEditText;
    EditText inventoryAdditionalCodeEditText;
    AlertDialog.Builder builder;

    public void initDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_modify_record, null);
        builder.setView(rootView);
        inventoryNumberEditText = rootView.findViewById(R.id.inventory_number);
        inventoryAdditionalCodeEditText = rootView.findViewById(R.id.additional_code);
        inventoryNameEditText = rootView.findViewById(R.id.inv_name);
        inventoryNumberEditText.setText(partialInventory.getInventoryNumber());
        inventoryAdditionalCodeEditText.setText(partialInventory.getAdditionalCode());
        inventoryNameEditText.setText(partialInventory.getName());
    }
}
