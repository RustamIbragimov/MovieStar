package com.rustam.moviestar.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.rustam.moviestar.MovieData;
import com.rustam.moviestar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ribra on 12/19/2015.
 */
public class MovieDataAdapter extends ArrayAdapter<MovieData> {
    private static final String LOG_TAG = MovieData.class.getSimpleName();


    public MovieDataAdapter(Context context, List<MovieData> objects) {
        super(context, 0, objects);
    }


    private String createRequestString(String path) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String SIZE = "w185/";
        return BASE_URL + SIZE + path;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieData movieData = getItem(position);
        View rootView = convertView;
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_poster, parent, false);
        }

        ImageView posterView = (ImageView) rootView.findViewById(R.id.grid_item_poster_imageview);
        String url = createRequestString(movieData.getPosterPath());
        Picasso.with(rootView.getContext()).load(url).into(posterView);

        return rootView;
    }

}
