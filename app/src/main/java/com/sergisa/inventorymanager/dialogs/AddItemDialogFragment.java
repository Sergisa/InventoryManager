package com.sergisa.inventorymanager.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.sergisa.inventorymanager.Inventory;
import com.sergisa.inventorymanager.R;

public class AddItemDialogFragment extends DialogFragment {
    String id = "";
    View rootView;
    Inventory partialInventory;
    EditText inventoryNumberEditText;
    EditText inventoryNameEditText;
    EditText inventoryAdditionalCodeEditText;

    public AddItemDialogFragment(String code) {
        this(new Inventory(code));
    }

    public AddItemDialogFragment(Inventory inv) {
        partialInventory = inv;
    }

    public AddItemDialogFragment() {
        this(new Inventory(null));
    }

    public interface NoticeDialogListener {
        void onDialogAddClick(AddItemDialogFragment dialog);
    }

    NoticeDialogListener dialogListener;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_add_record, null);
        builder.setView(rootView);
        inventoryNumberEditText = rootView.findViewById(R.id.inventory_number);
        inventoryNameEditText = rootView.findViewById(R.id.inv_name);
        inventoryAdditionalCodeEditText = rootView.findViewById(R.id.additional_code);
        inventoryNumberEditText.setText(partialInventory.getInventoryNumber());
        (rootView.findViewById(R.id.add_record)).setOnClickListener(view -> {
            partialInventory
                    .withName(inventoryNameEditText.getText().toString())
                    .withAdditionalCode(inventoryAdditionalCodeEditText.getText().toString())
                    .withInventoryNumber(inventoryNumberEditText.getText().toString());
            dialogListener.onDialogAddClick(AddItemDialogFragment.this);
        });
        return builder
                .setTitle("Добавление")
                .setIcon(R.drawable.add_to_queue_svgrepo_com)
                .create();
    }

    public String getInventoryId() {
        return id;
    }

    public AddItemDialogFragment withId(String id) {
        this.id = id;
        return this;
    }

    public AddItemDialogFragment withDialogListener(NoticeDialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }

    public Inventory getPartialInventory() {
        return partialInventory;
    }
}
