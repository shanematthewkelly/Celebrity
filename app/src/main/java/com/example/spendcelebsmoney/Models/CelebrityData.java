package com.example.spendcelebsmoney.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class CelebrityData {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("celebrityName")
    @Expose
    private String celebrityName;

    @SerializedName("celebrityDescription")
    @Expose
    private String celebrityDescription;

    @SerializedName("celebrityPhoto")
    @Expose
    private String celebrityPhoto;

    @SerializedName("celebrityWorth")
    @Expose
    private BigInteger celebrityWorth;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCelebrityName() {
        return celebrityName;
    }

    public void setCelebrityName(String celebrityName) {
        this.celebrityName = celebrityName;
    }

    public String getCelebrityDescription() {
        return celebrityDescription;
    }

    public void setCelebrityDescription(String celebrityDescription) {
        this.celebrityDescription = celebrityDescription;
    }

    public String getCelebrityPhoto() {
        return celebrityPhoto;
    }

    public void setCelebrityPhoto(String celebrityPhoto) {
        this.celebrityPhoto = celebrityPhoto;
    }

    public BigInteger getCelebrityWorth() {
        return celebrityWorth;
    }

    public void setCelebrityWorth(BigInteger celebrityWorth) {
        this.celebrityWorth = celebrityWorth;
    }
}
