package com.example.films;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.films.POJO.Films;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


class FilmsViewHolder extends RecyclerView.ViewHolder {

    ImageView imgFilms;
    TextView nameFilm;

    public FilmsViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFilms = itemView.findViewById(R.id.imageFilms);
        nameFilm = itemView.findViewById(R.id.nameFilm);
        itemView.isClickable();
    }
}

class GenreViewHolder extends RecyclerView.ViewHolder {
    TextView genreFilms;

    public GenreViewHolder(@NonNull final View itemView) {
        super(itemView);
        genreFilms = itemView.findViewById(R.id.genreItem);
    }

}

class GenreHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView genreHeaderFilms;

    public GenreHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        genreHeaderFilms = itemView.findViewById(R.id.filmsHeaderId);
    }
}

class FilmHeaderViewHolder extends RecyclerView.ViewHolder {

    TextView filmsHeader;

    public FilmHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        filmsHeader = itemView.findViewById(R.id.filmsHeaderId);
    }
}

public class FilmsAdapter extends RecyclerView.Adapter {

    LayoutInflater inflater;
    static private List<Object> data;
    Context context;
    private static final int TYPE_GENRE = 1;
    private static final int TYPE_FILM = 2;
    private static final int TYPE_GENRE_HEADER = 3;
    private static final int TYPE_FILM_HEADER = 4;
    Films.ApiResponse filmsItem;
    String genreItem;
    Films.ApiResponse f;
    GenreHeader genreHeader;
    FilmsHeader filmHeader;
    String genre;
    Boolean click = false;
    List<Object> list;
    List<Object> setData;
    String copyGenre = "";
    int pos;
    int a;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public FilmsAdapter(List<Object> data, Context context, List<Object> setData) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        FilmsAdapter.data = data;
        this.setData = setData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view;
        Log.d("size", String.valueOf(data.size()));
        if (viewType == TYPE_FILM) {
            view = inflater.inflate(R.layout.item_list, parent, false);
            final FilmsViewHolder viewHolder = new FilmsViewHolder(view);
             a = viewHolder.getAdapterPosition();
            return viewHolder;
        } else if (viewType == TYPE_GENRE_HEADER) {
            view = inflater.inflate(R.layout.ittem_genre_header, parent, false);
            final ViewGroup.LayoutParams lp = view.getLayoutParams();
            setFullSpan(view);
            return new GenreHeaderViewHolder(view);
        } else if (viewType == TYPE_FILM_HEADER) {
            view = inflater.inflate(R.layout.ittem_films_header, parent, false);
            setFullSpan(view);
            return new FilmHeaderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.genrelist, parent, false);
            final GenreViewHolder genreViewHolder = new GenreViewHolder(view);
            setFullSpan(view);
            return genreViewHolder;
        }
    }

    @SuppressLint({"WrongConstant", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FILM) {
            filmsItem = (Films.ApiResponse) data.get(position);
            ((FilmsViewHolder) holder).nameFilm.setText(((Films.ApiResponse) data.get(position)).localizedName);
            Picasso.get().load((String.valueOf(filmsItem.imageUrl))).placeholder(R.drawable.placeholder).into(((FilmsViewHolder) holder).imgFilms);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentDescription fd = new FragmentDescription();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", ((Films.ApiResponse) data.get(position)).localizedName);
                    if (filmsItem.genres.size() == 0) {
                        bundle.putString("genre", String.valueOf(((Films.ApiResponse) data.get(position)).year));
                    } else {
                        bundle.putString("genre", (((Films.ApiResponse) data.get(position)).genres.toString().replace("[", "").replace("]", "")) + ", " + ((Films.ApiResponse) data.get(position)).year);
                    }
                    bundle.putString("description", ((Films.ApiResponse) data.get(position)).description);
                    if (((Films.ApiResponse) data.get(position)).rating == null) {
                        bundle.putString("rating", ("--"));
                    } else {
                        bundle.putString("rating", (((Films.ApiResponse) data.get(position)).rating.toString().substring(0, 3)));
                    }
                    bundle.putString("image", String.valueOf(((Films.ApiResponse) data.get(position)).imageUrl));
                    bundle.putString("nameBar", ((Films.ApiResponse) data.get(position)).name);
                    fd.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    fragmentManager = activity.getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, fd);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        } else if (getItemViewType(position) == TYPE_GENRE_HEADER) {
            genreHeader = (GenreHeader) data.get(position);
            ((GenreHeaderViewHolder) holder).genreHeaderFilms.setText(String.valueOf(genreHeader.genreHeader));
        } else if (getItemViewType(position) == TYPE_FILM_HEADER) {
            filmHeader = (FilmsHeader) data.get(position);
            ((FilmHeaderViewHolder) holder).filmsHeader.setText(filmHeader.filmsHeader);
        } else if (getItemViewType(position) == TYPE_GENRE) {
            genreItem = (String) data.get(position);
            ((GenreViewHolder) holder).genreFilms.setText(genreItem);
            if (data.get(position).equals(copyGenre)){
                holder.itemView.setBackgroundColor(R.color.genreItem);
                click = true;
            }else holder.itemView.setBackgroundColor(0);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.get(position).equals(copyGenre)) {
                            copyGenre= "";
                            data.clear();
                            data.addAll(setData);
                        } else {
                            copyGenre = (String) data.get(position);
                            list = new ArrayList<>();
                            for (int i = 0; i < setData.size(); i++) {
                                if (setData.get(i) instanceof GenreHeader) {
                                    list.add(setData.get(i));
                                } else if (setData.get(i) instanceof String) {
                                    list.add(setData.get(i));
                                } else if (setData.get(i) instanceof FilmsHeader) {
                                    list.add(setData.get(i));
                                }
                                if (setData.get(i) instanceof Films.ApiResponse)
                                    if (((Films.ApiResponse) setData.get(i)).genres.contains(((String) data.get(position)).toLowerCase())) {
                                        list.add(setData.get(i));
                                    }
                            }
                            data.clear();
                            data.addAll(list);
                        }
                    notifyDataSetChanged();
                    }
                });

        }
    }

    public void setCopyGenre(String copyGenre) {
        this.copyGenre = copyGenre;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof Films.ApiResponse) {
            return TYPE_FILM;
        } else if (data.get(position) instanceof GenreHeader) {
            return TYPE_GENRE_HEADER;
        } else if (data.get(position) instanceof FilmsHeader) {
            return TYPE_FILM_HEADER;
        } else if (data.get(position) instanceof String) {
            return TYPE_GENRE;
        }
        return 10;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void setFullSpan(View view){
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
            sglp.setFullSpan(true);
        }
    }
}


