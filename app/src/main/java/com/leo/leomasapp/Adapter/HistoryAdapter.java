package com.leo.leomasapp.Adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leo.leomasapp.Data.ProductClass;
import com.leo.leomasapp.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<ProductClass> productClass;

    public HistoryAdapter(List<ProductClass> productClass) {
        this.productClass = productClass;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
            ProductClass productClasses = productClass.get(position);
            holder.nameProduct.setText(productClasses.getNameProduct());
            Long harga = productClasses.getPriceProduct();
            if (harga != null) {
                String formatHarga = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(harga);
                holder.priceProduct.setText(formatHarga);
            } else {
                holder.priceProduct.setText("Harga tidak tersedia");
            }
            holder.imageProduct.setImageResource(productClasses.getImageProduct1());
    }

    @Override
    public int getItemCount() {
        return productClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameProduct, priceProduct, status;
        ImageView imageProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.name_product);
            priceProduct = itemView.findViewById(R.id.harga_product);
            status = itemView.findViewById(R.id.status);
            imageProduct = itemView.findViewById(R.id.image_product);
        }
    }
}
