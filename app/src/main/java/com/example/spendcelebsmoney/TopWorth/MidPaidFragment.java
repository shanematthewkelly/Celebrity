package com.example.spendcelebsmoney.TopWorth;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spendcelebsmoney.Models.CelebrityData;
import com.example.spendcelebsmoney.R;

import java.util.ArrayList;
import java.util.List;


public class MidPaidFragment extends Fragment {

    //Variables
    private List<CelebrityData> celebrities = new ArrayList<>();
    private CelebrityAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mid_paid, container, false);

        return v;
    }
}
