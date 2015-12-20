package com.rustam.moviestar.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.rustam.moviestar.DetailActivity;
import com.rustam.moviestar.FetchMovieDataTask;
import com.rustam.moviestar.MovieData;
import com.rustam.moviestar.R;
import com.rustam.moviestar.adapters.MovieDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static ArrayAdapter<MovieData> mMovieDataAdapter;

    public MainActivityFragment() {
        setHasOptionsMenu(true);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        List<MovieData> list = new ArrayList<>();

        mMovieDataAdapter = new MovieDataAdapter(
                getActivity(),
                list
        );

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_list_poster);
        gridView.setAdapter(mMovieDataAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_REFERRER, mMovieDataAdapter.getItem(position));
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieList();
    }

    public void updateMovieList() {
        FetchMovieDataTask task = new FetchMovieDataTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(
                getString(R.string.pref_sort_key),          // key of pref
                getString(R.string.pref_sort_default)       // value if key does not exist
        );
        task.execute(sortOrder);
    }
}
