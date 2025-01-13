package com.leo.leomasapp.Adapter;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
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

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Remove Item")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("history")
                                    .whereEqualTo("name_product", productClasses.getNameProduct())
                                    .get()
                                    .addOnSuccessListener(queryDocumentSnapshots -> {
                                        queryDocumentSnapshots.forEach(documentSnapshot -> {
                                            db.collection("history")
                                                    .document(documentSnapshot.getId())
                                                    .delete()
                                                    .addOnSuccessListener(aVoid -> {
                                                        Toast.makeText(v.getContext(), "Item successfully deleted", Toast.LENGTH_SHORT).show();
                                                        productClass.remove(holder.getAdapterPosition());
                                                        notifyItemRemoved(holder.getAdapterPosition());
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(v.getContext(), "Failed ", Toast.LENGTH_SHORT).show();
                                                    });
                                        });
                                    });
                        }
                    })
                    .setNegativeButton("Tidak", null)
                    .show();

            return true; // Menandakan long click sudah ditangani
        });
    }

    @Override
    public int getItemCount() {
        return productClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameProduct, priceProduct, status;
        ImageView imageProduct, emptyLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.name_product);
            priceProduct = itemView.findViewById(R.id.harga_product);
            status = itemView.findViewById(R.id.status);
            imageProduct = itemView.findViewById(R.id.image_product);
        }
    }
}
