package com.leo.leomasapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leo.leomasapp.Data.ProductClass;
import com.leo.leomasapp.DetailActivity;
import com.leo.leomasapp.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private  List<ProductClass> productClass;
    public FavoriteAdapter(List<ProductClass> productClass){
        this.productClass = productClass;
    }
    public List<ProductClass> getProductClasses() {
        return productClass;
    }
    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_layout,parent,false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
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
        holder.favoriteIcon.setImageResource(R.drawable.baseline_favorite_24);
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("nameProduct",productClasses.getNameProduct());
                intent.putExtra("priceProduct",productClasses.getPriceProduct());
                intent.putExtra("detailProduct",productClasses.getDetailProduct());
                intent.putExtra("jenisProduct",productClasses.getJenisProduct());
                intent.putExtra("imageProduct1",productClasses.getImageProduct1());
                intent.putExtra("imageProduct2",productClasses.getImageProduct2());
                intent.putExtra("imageProduct3",productClasses.getImageProduct3());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteIcon, imageProduct;
        TextView nameProduct, priceProduct, btnBuy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteIcon = itemView.findViewById(R.id.favorite_icon);
            imageProduct = itemView.findViewById(R.id.image_product);
            nameProduct = itemView.findViewById(R.id.name_product);
            priceProduct = itemView.findViewById(R.id.harga_product);
            btnBuy = itemView.findViewById(R.id.btn_buy);
        }
    }
}
