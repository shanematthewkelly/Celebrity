package com.example.spendcelebsmoney.API;

import com.example.spendcelebsmoney.Models.Celebrity;
import com.example.spendcelebsmoney.Models.Product;


import retrofit2.Call;
import retrofit2.http.GET;

public interface PullData {

    //get request made to celebrities
    @GET("60fa79fb99d9f62a7add")
    Call<Celebrity> getCelebrities();

    //get request made to products
    @GET("7831c77596365ae62f10")
    Call<Product> getProducts();
}

