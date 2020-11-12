package com.example.spendcelebsmoney.TopWorth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.spendcelebsmoney.Models.CelebrityData;
import com.example.spendcelebsmoney.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class CelebrityAdapter extends RecyclerView.Adapter<CelebrityAdapter.MyViewHolder> {

    private List<CelebrityData> celebrities;
    private Context context;
    private OnItemClickListener onItemClickListener;



    CelebrityAdapter(List<CelebrityData> celebrities, Context context) {
        this.celebrities = celebrities;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final  MyViewHolder holder = holders;
        CelebrityData model = celebrities.get(position);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.placeholder);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getCelebrityPhoto())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(holder.celebrityPhoto);

        holder.celebrityPhoto.setImageURI(Uri.parse(model.getCelebrityPhoto()));
        holder.celebrityName.setText(model.getCelebrityName());
        holder.celebrityWorth.setText("$" + model.getCelebrityWorth());
        holder.celebrityDesc.setText(model.getCelebrityDescription());


    }

    @Override
    public int getItemCount() {
        return celebrities.size();
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView celebrityName, celebrityWorth, celebrityDesc;
        CircularImageView celebrityPhoto;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            celebrityName = itemView.findViewById(R.id.celebrityName);
            celebrityPhoto = itemView.findViewById(R.id.celebrityPhoto);
            celebrityWorth = itemView.findViewById(R.id.celebrityWorth);
            celebrityDesc = itemView.findViewById(R.id.CelebrityDesc);
            progressBar = itemView.findViewById(R.id.imageloading);

            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v){
            onItemClickListener.onItemClick(v, getAdapterPosition());

        }
    }
}

