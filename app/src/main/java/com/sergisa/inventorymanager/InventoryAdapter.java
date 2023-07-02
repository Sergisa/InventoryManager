package com.sergisa.inventorymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryItemViewHolder> {
    public interface OnInventoryClickListener {
        void onItemClick(Inventory item);
    }

    private final LayoutInflater inflater;
    private final List<Inventory> inventory;
    private OnInventoryOptionsClickListener optionsClickListener;
    OnInventoryClickListener inventoryClickListener;
    Context ctx;

    public InventoryAdapter(Context context, List<Inventory> inventory) {
        ctx = context;
        this.inventory = inventory;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public InventoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item, parent, false);
        InventoryItemViewHolder holder = new InventoryItemViewHolder(view);
        View.OnClickListener clickListener = view1 -> {
            if (optionsClickListener != null) {
                optionsClickListener.onButtonClick(view1, inventory.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        };
        holder.optionsButton.setOnClickListener(clickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryItemViewHolder holder, int position) {
        Inventory singleInventory = inventory.get(position);
        holder.inventoryNumberTextView.setText(singleInventory.getInventoryNumber());
        holder.additionalCodeTextView.setText(singleInventory.getAdditionalCode());
        holder.nameTextView.setText(singleInventory.getName());
        holder.roomTextView.setText(singleInventory.getRoom());

        adjustView(holder.nameTextView, singleInventory.getName());
        adjustView(holder.additionalCodeTextView, singleInventory.getAdditionalCode());
        adjustView(holder.inventoryNumberTextView, singleInventory.getInventoryNumber());
        adjustView(holder.roomTextView, singleInventory.getRoom());
        holder.bind(inventory.get(position), inventoryClickListener);
    }

    private void adjustView(View view, String value) {
        if (value.isEmpty()) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }

    public OnInventoryOptionsClickListener getOptionsClickListener() {
        return optionsClickListener;
    }

    public InventoryAdapter withOptionsClickListener(OnInventoryOptionsClickListener optionsClickListener) {
        this.optionsClickListener = optionsClickListener;
        return this;
    }

    public OnInventoryClickListener getInventoryClickListener() {
        return inventoryClickListener;
    }

    public InventoryAdapter withInventoryClickListener(OnInventoryClickListener inventoryClickListener) {
        this.inventoryClickListener = inventoryClickListener;
        return this;
    }

    public static class InventoryItemViewHolder extends RecyclerView.ViewHolder {
        final TextView inventoryNumberTextView;
        final TextView additionalCodeTextView;
        final TextView nameTextView;
        final TextView roomTextView;
        final Button optionsButton;

        InventoryItemViewHolder(View view) {
            super(view);
            optionsButton = view.findViewById(R.id.actionButton);
            inventoryNumberTextView = view.findViewById(R.id.inventory_code);
            additionalCodeTextView = view.findViewById(R.id.additional_code);
            roomTextView = view.findViewById(R.id.room);
            nameTextView = view.findViewById(R.id.name);
        }

        public void bind(final Inventory item, final OnInventoryClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
