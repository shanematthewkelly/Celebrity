package com.example.spendcelebsmoney.TopWorth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.spendcelebsmoney.API.Client;
import com.example.spendcelebsmoney.API.PullData;
import com.example.spendcelebsmoney.Core.MainActivity;
import com.example.spendcelebsmoney.Models.Product;
import com.example.spendcelebsmoney.Models.ProductData;
import com.example.spendcelebsmoney.Products.ProductAdapter;
import com.example.spendcelebsmoney.R;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CelebrityDetail extends AppCompatActivity {

    //Variables
    private ImageView backarrow, detailedPhoto;
    private TextView detailedName, detailedDescription, detailedWorth;
    private String celebPhotoUrl, celebName, celebDesc, celebWorth;
    private List<ProductData> products = new ArrayList<>();
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager myManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_celebrity_detail);

        //Recycler
        recyclerView = findViewById(R.id.productRecycler);
        myManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        //Animation
        Animation SmallToBig = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.smalltobig);

        //hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        //init views
        backarrow = findViewById(R.id.backarrow2);
        detailedPhoto = findViewById(R.id.deatiledPhoto);
        detailedName = findViewById(R.id.detailedName);
        detailedDescription = findViewById(R.id.detailedDesc);
        detailedWorth = findViewById(R.id.detailedWorth);

        Intent intent = getIntent();
        celebPhotoUrl = intent.getStringExtra("celebrityPhoto");
        celebName = intent.getStringExtra("celebrityName");
        celebDesc = intent.getStringExtra("celebrityDesc");
        celebWorth = intent.getStringExtra("celebrityWorth");

        //Convert the String celebWorth into an BigInt
        assert celebWorth != null;
        BigInteger celebWorthBigInt = new BigInteger(celebWorth);



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.placeholder);
        requestOptions.centerCrop();

        Glide.with(this)
                .load(celebPhotoUrl)
                .apply(requestOptions)
                .into(detailedPhoto);

        detailedName.setText(celebName);
        detailedDescription.setText(celebDesc);
        detailedWorth.setText("$" + celebWorthBigInt);

        //set Animations
        detailedPhoto.setAnimation(SmallToBig);

        //on Click
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        //Network operations - Retrofit
        PullData getRequest = Client.client().create(PullData.class);

        Call<Product> caller;
        caller = getRequest.getProducts();

        caller.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if (response.isSuccessful() && response.body().getProductData() != null){

                    if (!products.isEmpty()){
                        products.clear();
                    }

                    products = response.body().getProductData();
                    adapter = new ProductAdapter(products, CelebrityDetail.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(getApplicationContext(), "No Celebrities Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure to get a response", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void AddToWorth(BigInteger productValue) {

    }


}
