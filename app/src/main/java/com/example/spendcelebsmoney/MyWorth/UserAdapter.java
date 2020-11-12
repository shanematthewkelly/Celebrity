package com.example.spendcelebsmoney.MyWorth;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.spendcelebsmoney.LocalDatabase.User;
import com.example.spendcelebsmoney.R;
import com.example.spendcelebsmoney.TopWorth.CelebrityAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<User> user = new ArrayList<>();
    private EditButtonClickListener listen;
    private DeleteButtonClickListener listen_delete;
    private Context context;


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);

        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User myUser = user.get(position);

//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.placeholder);
//        requestOptions.error(R.drawable.placeholder);
//        requestOptions.centerCrop();
//
//
//        Glide.with(context)
//                .load(myUser.getMyPhoto())
//                .apply(requestOptions)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        Toast.makeText(context, "Images Not Loaded", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        Toast.makeText(context, "User Image Loaded Successfully", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                })
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(holder.userPhoto);

        holder.userName.setText(myUser.getMyName());
        holder.userDescription.setText(myUser.getMyDescription());
//        holder.userPhoto.setImageURI(Uri.parse(myUser.getMyPhoto()));
        holder.userWorth.setText("$" + myUser.getMyWorth());
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    void setUsers(List<User> user) {
        this.user = user;
        //this is not the best way to do this - change the line below (implement an alternative as this may cause bugs)
        notifyDataSetChanged();
    }

    class UserHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView userDescription;
        private TextView userWorth;
        private CircularImageView userPhoto;

        UserHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userDescription = itemView.findViewById(R.id.userDescription);
            userWorth = itemView.findViewById(R.id.userWorth);
            userPhoto = itemView.findViewById(R.id.userPhoto);

            ImageView updateUser = itemView.findViewById(R.id.updateUser);
            updateUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getAdapterPosition();

                    //invalid position handler
                    if (listen != null && itemPosition != RecyclerView.NO_POSITION) {
                        listen.editButtonClicked(user.get(itemPosition));
                    }
                }
            });


            ImageView deleteUser = itemView.findViewById(R.id.deleteUser);
            deleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getAdapterPosition();

                    //invalid position handler
                    if (listen_delete != null && itemPosition != RecyclerView.NO_POSITION) {
                        listen_delete.deleteButtonClicked(user.get(itemPosition));
                    }
                }
            });
        }
    }

    //responsible for when clicking the edit icon on each card
    public interface EditButtonClickListener {
        void editButtonClicked(User user);
    }

    void setEditButtonClickListener(EditButtonClickListener listen) {
        this.listen = listen;
    }

    //responsible for when clicking the edit icon on each card
    public interface DeleteButtonClickListener {
        void deleteButtonClicked(User user);
    }

    void setDeleteButtonClickListener(DeleteButtonClickListener listen_delete) {
        this.listen_delete = listen_delete;
    }

}




