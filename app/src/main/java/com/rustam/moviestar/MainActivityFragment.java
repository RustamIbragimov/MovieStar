package com.rustam.moviestar;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.rustam.moviestar.adapters.MovieDataAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    static ArrayAdapter<MovieData> mMovieDataAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        FetchMovieDataTask task = new FetchMovieDataTask();
        task.execute("popularity.desc");

        List<MovieData> list = new ArrayList<>();

        mMovieDataAdapter = new MovieDataAdapter(
                getActivity(),
                list
        );

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_list_poster);
        gridView.setAdapter(mMovieDataAdapter);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), DetailActivity.class)
//                        .putExtra(Intent.EXTRA_TEXT, position);
//                startActivity(intent);
//            }
//        });

        return rootView;
    }
}
