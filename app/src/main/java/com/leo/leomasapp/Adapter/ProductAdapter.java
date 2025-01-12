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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductClass>productClasses;

    public ProductAdapter(List<ProductClass> productClasses) {
        this.productClasses = productClasses;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rings_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        ProductClass productClass = productClasses.get(position);
        holder.nameProduct.setText(productClass.getNameProduct());
        Long harga = productClass.getPriceProduct();
        String formatHarga = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(harga);
        holder.priceProduct.setText(formatHarga);
        holder.imageProduct.setImageResource(productClass.getImageProduct1());
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("nameProduct",productClass.getNameProduct());
                intent.putExtra("priceProduct",productClass.getPriceProduct());
                intent.putExtra("detailProduct",productClass.getDetailProduct());
                intent.putExtra("jenisProduct",productClass.getJenisProduct());
                intent.putExtra("imageProduct1",productClass.getImageProduct1());
                intent.putExtra("imageProduct2",productClass.getImageProduct2());
                intent.putExtra("imageProduct3",productClass.getImageProduct3());
                v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return productClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameProduct,priceProduct,detailProduct,jenisProduct,btnBuy;
        ImageView imageProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.name_product);
            priceProduct = itemView.findViewById(R.id.harga_product);
            btnBuy = itemView.findViewById(R.id.btn_buy);
            imageProduct = itemView.findViewById(R.id.image_product);

        }
    }
}
