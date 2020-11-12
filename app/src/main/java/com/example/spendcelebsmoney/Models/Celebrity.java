package com.example.spendcelebsmoney.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Celebrity {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<CelebrityData> celebrityData;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CelebrityData> getCelebrityData() {
        return celebrityData;
    }

    public void setCelebrityData(List<CelebrityData> celebrityData) {
        this.celebrityData = celebrityData;
    }
}
