package com.example.spendcelebsmoney.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class ProductData {

    @SerializedName("_id")
    @Expose
    private String productId;

    @SerializedName("productPhoto")
    @Expose
    private String productPhoto;

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("productValue")
    @Expose
    private BigInteger productValue;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigInteger getProductValue() {
        return productValue;
    }

    public void setProductValue(BigInteger productValue) {
        this.productValue = productValue;
    }
}
