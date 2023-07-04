package com.sergisa.inventorymanager.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.sergisa.inventorymanager.R;
import com.sergisa.inventorymanager.db.Inventory;

public class EditItemDialogFragment extends InventoryItemDialog {
    String id = "";

    NoticeDialogListener dialogListener;

    public EditItemDialogFragment(Inventory inv) {
        this.partialInventory = inv;
    }

    public interface NoticeDialogListener {
        void onDialogRemoveClick(EditItemDialogFragment dialog);

        void onDialogEditClick(EditItemDialogFragment dialog);
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initDialog(savedInstanceState);
        (rootView.findViewById(R.id.btn_save)).setOnClickListener(view -> {
            dialogListener.onDialogEditClick(EditItemDialogFragment.this);
        });
        (rootView.findViewById(R.id.btn_delete)).setOnClickListener(view -> {
            dialogListener.onDialogRemoveClick(EditItemDialogFragment.this);
        });
        return builder
                .setTitle("Редактирование")
                .setIcon(R.drawable.edit_svgrepo_com)
                .create();
    }

    public Inventory getInventory() {
        partialInventory.withInventoryNumber(inventoryNumberEditText.getText().toString())
                .withAdditionalCode(inventoryAdditionalCodeEditText.getText().toString())
                .withName(inventoryNameEditText.getText().toString());
        return partialInventory;
    }

    public EditItemDialogFragment withDialogListener(NoticeDialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }
}
