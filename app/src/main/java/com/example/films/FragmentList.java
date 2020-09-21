package com.example.films;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.films.API.APIClient;
import com.example.films.API.APIInterface;
import com.example.films.POJO.Films;
import com.google.android.material.snackbar.Snackbar;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentList extends Fragment {
    FilmsAdapter adapter;
    APIInterface apiInterface;
    List<String> genre;
    List<Object> data;
    Toolbar toolbar;
    GenreHeader genreHeader;
    FilmsHeader filmsHeader;
    Snackbar snackbar;
    AppCompatActivity activity;
    FragmentList.ClickListener listener;
    ProgressBar progressBar;
    Films resource;
    List<Object> setData;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        showSnackbar(container);
        genre = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBar);
        try {
            if (!isConnected()) {
                progressBar.setVisibility(View.GONE);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        final RecyclerView recyclerView = view.findViewById(R.id.RecyclerList);
        toolbar = view.findViewById(R.id.my_toolbar);
        activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        activity.getSupportActionBar().setTitle(null);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Films> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<Films>() {
            @Override
            public void onResponse(Call<Films> call, Response<Films> response) {
                resource = response.body();
                genreList(resource);
                for (int i = 0; i < genre.size(); i++) {
                    genre.set(i, genre.get(i).substring(0, 1).toUpperCase() + genre.get(i).substring(1));
                }
                Collections.sort(resource.films);
                data = new ArrayList<>();
                genreHeader = new GenreHeader();
                filmsHeader = new FilmsHeader();
                Collections.sort(genre);
                genreHeader.setGenreHeader("Жанры");
                filmsHeader.setFilmsHeader("Фильмы");
                data.add(genreHeader);
                data.addAll(genre);
                data.add(filmsHeader);
                data.addAll(resource.films);
                setData = new ArrayList<>();
                setData.addAll(data);
                if (getActivity() != null) {
                    adapter = new FilmsAdapter(data, getActivity(), setData);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                Log.d("Tag", response.code() + "" + resource.films);
            }

            @Override
            public void onFailure(Call<Films> call, Throwable t) {
                Log.d("Tag", "Error");
                call.cancel();
            }
        });
        return view;
    }

    public boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }

    private void genreList(Films resource) {
        for (int i = 0; i < resource.films.size(); i++) {
            genre.addAll(resource.films.get(i).genres);
        }
        for (int i = 0; i < genre.size(); i++) {
            for (int j = genre.size() - 1; j >= 0; j--) {
                if (i != j && genre.get(i).equals(genre.get(j))) {
                    genre.remove(j);
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showSnackbar(final ViewGroup container) {
        try {
            if (!isConnected()) {
                snackbar = Snackbar.make(container, "Ошибка подключения к сети", Snackbar.LENGTH_INDEFINITE).setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (!isConnected()) {
                                showSnackbar(container);
                            } else {
                                listener.ClickListenerFragment();
                            }
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                snackbar.setActionTextColor(Color.parseColor("#A77DFF"));
                snackbar.getView().setBackground(getResources().getDrawable(R.drawable.container_snackbar));
                snackbar.show();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentList.ClickListener) {
            listener = (FragmentList.ClickListener) context;
        }

    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();

    }

    public FragmentList Filter(String link) {
            for (int i = 0; i < resource.films.size(); i++) {
                if (resource.films.get(i).genres.contains(link.toLowerCase())) {
                    List<Films.ApiResponse> list = new ArrayList<>();
                    list.add(resource.films.get(i));
                    data.addAll(list);
                    adapter.notifyDataSetChanged();
                }
        else {
                    data.addAll(resource.films);
                }
        }
        return null;
    }

    public interface ClickListener {
        void ClickListenerFragment();
    }
}
