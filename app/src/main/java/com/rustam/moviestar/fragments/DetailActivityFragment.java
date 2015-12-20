package com.rustam.moviestar.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rustam.moviestar.MovieData;
import com.rustam.moviestar.R;
import com.rustam.moviestar.Utility;
import com.rustam.moviestar.adapters.MovieDataAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailActivityFragment extends Fragment {
    MovieData mMovieDetails;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_REFERRER)) {
            mMovieDetails = intent.getParcelableExtra(Intent.EXTRA_REFERRER);
            initLayout(rootView);
        }

        return rootView;
    }

    private void initLayout(View rootView) {
        TextView title = (TextView) rootView.findViewById(R.id.movie_detail_title);
        title.setText(mMovieDetails.getTitle());

        ImageView thumbnail = (ImageView) rootView.findViewById(R.id.movie_detail_thumbnail);
        String url = Utility.createRequestString(mMovieDetails.getPosterPath());
        Picasso.with(rootView.getContext()).load(url).into(thumbnail);

        TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_detail_release_date);
        releaseDate.setText(mMovieDetails.getReleaseDate());

        TextView rating = (TextView) rootView.findViewById(R.id.movie_detail_rating);
        rating.setText(mMovieDetails.getRating());

        TextView overview = (TextView) rootView.findViewById(R.id.movie_detail_overview);
        overview.setText(mMovieDetails.getOverview());
    }
}
