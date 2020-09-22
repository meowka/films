package com.example.films;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class FragmentDescription extends Fragment {

    TextView name;
    TextView genre;
    TextView description;
    TextView rating;
    ImageView image;
    TextView txtToolbar;
    Toolbar myToolbar;
    AppCompatActivity activity;
    TextView filmsTag;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.description_fragment, container, false);
        init(view);

        myToolbar = view.findViewById(R.id.my_toolbar_description);
        activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(myToolbar);
            activity.getSupportActionBar().setTitle("");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getActivity().onBackPressed();
                }
            });

        }

        Log.d("name", String.valueOf(name));
        Bundle bundle = getArguments();

        if (bundle != null) {
            name.setText(bundle.getString("name"));
            genre.setText(bundle.getString("genre") + " год");
            Picasso.get().load(bundle.getString("image")).placeholder(R.drawable.placeholder).into(image);
            rating.setText(bundle.getString("rating"));
            description.setText(bundle.getString("description"));
            txtToolbar.setText(bundle.getString("nameBar"));
            filmsTag.setText("КиноПоиск");
        }
        setRetainInstance(true);
        return view;
    }


void init(View view){
    name = view.findViewById(R.id.nameFilmDescription);
    genre = view.findViewById(R.id.genreFilmDescription);
    description = view.findViewById(R.id.descriptionFilmsDescription);
    rating = view.findViewById(R.id.ratingFilmsDesription);
    image = view.findViewById(R.id.imageView);
    txtToolbar = view.findViewById(R.id.txt_description);
    filmsTag = view.findViewById(R.id.kinoPoisk);
}

}
