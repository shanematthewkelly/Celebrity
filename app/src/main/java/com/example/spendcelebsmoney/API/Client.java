package com.example.spendcelebsmoney.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {



    private static final String BASE_URL = "https://api.npoint.io/";

    private static Retrofit retrofit;

    public static Retrofit client() {


        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}




