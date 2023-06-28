package com.sergisa.inventorymanager.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.sergisa.inventorymanager.R;

public class AddItemDialogFragment extends DialogFragment {
    String id = "";
    View rootView;

    public AddItemDialogFragment() {
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
        (rootView.findViewById(R.id.add_record)).setOnClickListener(view -> {
            dialogListener.onDialogAddClick(AddItemDialogFragment.this);
        });
        return builder
                .setTitle("Добавление")
                .setIcon(R.drawable.add_to_queue_svgrepo_com)
                .create();
    }

    /*@Override
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

    public AddItemDialogFragment withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return ((TextView) rootView.findViewById(R.id.subject_edittext)).getText().toString();
    }

    public String getDescription() {
        return ((TextView) rootView.findViewById(R.id.description_edittext)).getText().toString();
    }

    public AddItemDialogFragment withDialogListener(NoticeDialogListener dialogListener) {
        this.dialogListener = dialogListener;
        return this;
    }
}
