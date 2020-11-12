package com.example.spendcelebsmoney.Products;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.spendcelebsmoney.Models.ProductData;
import com.example.spendcelebsmoney.R;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<ProductData> products;
    private Context context;
    private ProductAdapter.OnItemClickListener onItemClickListener;



    public ProductAdapter(List<ProductData> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductAdapter.MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holders, int position) {
        ProductData model = products.get(position);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.burger);
        requestOptions.error(R.drawable.burger);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getProductPhoto())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holders.productPhoto);

        holders.productPhoto.setImageURI(Uri.parse(model.getProductPhoto()));
        holders.productName.setText(model.getProductName());
        holders.productValue.setText("$" + model.getProductValue());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    interface OnItemClickListener {
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productValue;
        ImageView productPhoto, addValue, minusValue;
        EditText productInput;
        ProductAdapter.OnItemClickListener onItemClickListener;

        MyViewHolder(View itemView, ProductAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productPhoto = itemView.findViewById(R.id.productPhoto);
            productValue = itemView.findViewById(R.id.productValue);
            addValue = itemView.findViewById(R.id.addValue);
            minusValue = itemView.findViewById(R.id.minusValue);
            productInput = itemView.findViewById(R.id.productInput);

            addValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = productInput.getText().toString();

                    int currentValue = Integer.parseInt(productInput.getText().toString());
                    productInput.setText(String.valueOf(currentValue + 1));

                    //make a call to the celebrity detail
                    if (input.isEmpty()) {
                        Toast.makeText(context, "Enter a valid digit", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            minusValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int currentValue = Integer.parseInt(productInput.getText().toString());
                    if (currentValue != 0) {
                        productInput.setText(String.valueOf(currentValue - 1));
                    }


                }
            });

            this.onItemClickListener = onItemClickListener;
        }


    }


}
