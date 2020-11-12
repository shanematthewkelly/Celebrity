package com.example.spendcelebsmoney.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<ProductData> productData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductData> getProductData() {
        return productData;
    }

    public void setProductData(List<ProductData> productData) {
        this.productData = productData;
    }
}
