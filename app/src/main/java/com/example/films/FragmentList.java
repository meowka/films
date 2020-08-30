package com.example.films;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.API.APIClient;
import com.example.films.API.APIInterface;
import com.example.films.POJO.Films;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentList extends Fragment {
    FilmsAdapter adapter;
    APIInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);


        final RecyclerView recyclerView = view.findViewById(R.id.RecyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Films> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<Films>() {
            @Override
            public void onResponse(Call<Films> call, Response<Films> response) {


                Films resource = response.body();
                Log.d("Tag", response.code() + "" + resource.films);
                adapter = new FilmsAdapter(resource.films, getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Films> call, Throwable t) {
                Log.d("Tag", "Error");
                call.cancel();
            }
        });

        return view;
    }
}
