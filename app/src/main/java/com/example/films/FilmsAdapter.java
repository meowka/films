package com.example.films;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.POJO.Films;
import com.squareup.picasso.Picasso;

import java.util.List;


class FilmsViewHolder extends RecyclerView.ViewHolder {

    ImageView imgFilms;
    TextView nameFilm;

    public FilmsViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFilms = itemView.findViewById(R.id.imageFilms);
        nameFilm = itemView.findViewById(R.id.nameFilm);
    }
}

public class FilmsAdapter extends RecyclerView.Adapter<FilmsViewHolder> {

    LayoutInflater inflater;
    private List<Films.ApiResponse> data;
    Context context;

    public FilmsAdapter(List<Films.ApiResponse> data, Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {
        Log.d("Tag1", "name");
        Picasso.get().load(String.valueOf(data.get(position).imageUrl)).into(holder.imgFilms);
        holder.nameFilm.setText(data.get(position).name);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}


