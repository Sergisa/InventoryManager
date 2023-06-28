package com.sergisa.inventorymanager.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.sergisa.inventorymanager.Inventory;
import com.sergisa.inventorymanager.R;

public class EditItemDialogFragment extends DialogFragment {
    private final Inventory inventory;
    String id = "";
    View rootView;

    public EditItemDialogFragment(Inventory inv) {
        this.inventory = inv;
    }

    public interface NoticeDialogListener {
        void onDialogRemoveClick(EditItemDialogFragment dialog);

        void onDialogEditClick(EditItemDialogFragment dialog);
    }

    NoticeDialogListener dialogListener;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_modify_record, null);
        builder.setView(rootView);
        (rootView.findViewById(R.id.btn_update)).setOnClickListener(view -> {
            dialogListener.onDialogEditClick(EditItemDialogFragment.this);
        });
        (rootView.findViewById(R.id.btn_delete)).setOnClickListener(view -> {
            dialogListener.onDialogRemoveClick(EditItemDialogFragment.this);
        });
        EditText invNumberTextEditView = rootView.findViewById(R.id.inventory_number);
        EditText additionalCodeTextEditView = rootView.findViewById(R.id.additional_code);
        EditText invNameTextEditView = rootView.findViewById(R.id.inv_name);
        invNumberTextEditView.setText(inventory.getInventoryNumber());
        additionalCodeTextEditView.setText(inventory.getAdditionalCode());
        invNameTextEditView.setText(inventory.getName());
        return builder
                .setTitle("Редактирование")
                .setIcon(R.drawable.edit_svgrepo_com)
                //.setMessage("Радактирование элемента")
                //.setPositiveButton("OK", null)
                //.setNegativeButton("Отмена", null)
                .create();
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }*/

    public String getInventoryId() {
        return id;
    }

    public EditItemDialogFragment withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return ((TextView) rootView.findViewById(R.id.subject_edittext)).getText().toString();
    }

    public String getDescription() {
        return ((TextView) rootView.findViewById(R.id.description_edittext)).getText().toString();
    }

    public EditItemDialogFragment withDialogListener(NoticeDialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }
}