package com.example.spendcelebsmoney.TopWorth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendcelebsmoney.API.PullData;
import com.example.spendcelebsmoney.API.Client;
import com.example.spendcelebsmoney.Core.MainActivity;
import com.example.spendcelebsmoney.Models.Celebrity;
import com.example.spendcelebsmoney.Models.CelebrityData;
import com.example.spendcelebsmoney.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopPaidFragment extends Fragment {

    //Variables
    private List<CelebrityData> celebrities = new ArrayList<>();
    private CelebrityAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ConstraintLayout noConnection;
    private LinearLayout connected;
    private ImageView refresh;
    private CardView noConnectionCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top_paid, container, false);

        //Animation
        Animation TopToBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.top_to_bottom);

        //Recycler
        recyclerView = v.findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        //init views
        noConnection = v.findViewById(R.id.noConnection);
        connected = v.findViewById(R.id.connected);
        refresh = v.findViewById(R.id.refresh);
        noConnectionCard = v.findViewById(R.id.noConnectionCard);

        noConnectionCard.setAnimation(TopToBottom);
        refresh.setAnimation(TopToBottom);

        refresh.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent restart = new Intent(getActivity(), MainActivity.class);
                startActivity(restart);
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        //Network operations - Retrofit
        PullData getRequest = Client.client().create(PullData.class);

        Call<Celebrity> caller;
        caller = getRequest.getCelebrities();

        caller.enqueue(new Callback<Celebrity>() {
            @Override
            public void onResponse(@NotNull Call<Celebrity> call, @NotNull Response<Celebrity> response) {

                if (response.body() != null) {
                    if (response.isSuccessful() && response.body().getCelebrityData() != null){
                        if (!celebrities.isEmpty()){
                            celebrities.clear();
                        }

                        celebrities = response.body().getCelebrityData();
                        adapter = new CelebrityAdapter(celebrities, getContext());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                        passData();

                    } else {
                        Toast.makeText(getContext(), "No Celebrities Found", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Celebrity> call, Throwable t) {
                noConnection.setVisibility(View.VISIBLE);
                connected.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }

    private void passData() {

        adapter.setOnItemClickListener(new CelebrityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CelebrityDetail.class);

                //This passes the data for the news article to the next intent
                CelebrityData data = celebrities.get(position);

                intent.putExtra("celebrityName", data.getCelebrityName());
                intent.putExtra("celebrityPhoto", data.getCelebrityPhoto());
                intent.putExtra("celebrityDesc", data.getCelebrityDescription());
                intent.putExtra("celebrityWorth", data.getCelebrityWorth().toString());

                startActivity(intent);
            }
        });
    }
    }
