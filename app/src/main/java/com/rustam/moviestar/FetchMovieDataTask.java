package com.rustam.moviestar;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rustam.moviestar.fragments.MainActivityFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by ribra on 12/19/2015.
 */
public class FetchMovieDataTask extends AsyncTask<String, Void, MovieData[]> {
    private static final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();


    private MovieData[] getMoviesFromJson(String json) throws JSONException {
        final String OWN_RESULTS = "results";
        final String OWN_POSTER_PATH = "poster_path";
        final String OWN_OVERVIEW = "overview";
        final String OWN_RELEASE_DATE = "release_date";
        final String OWN_TITLE = "original_title";
        final String OWN_RATING = "vote_average";

        JSONObject moviesJson = new JSONObject(json);
        JSONArray res = moviesJson.getJSONArray(OWN_RESULTS);

        MovieData[] moviesData = new MovieData[res.length()];

        for (int i = 0; i < res.length(); i++) {
            String title;
            String path;
            String overview;
            String releaseDate;
            String rating;

            JSONObject movieObject = res.getJSONObject(i);

            title = movieObject.getString(OWN_TITLE);
            path = movieObject.getString(OWN_POSTER_PATH);
            overview = movieObject.getString(OWN_OVERVIEW);
            releaseDate = Utility.getYearFromDate(
                    movieObject.getString(OWN_RELEASE_DATE));
            rating = Utility.modifyRating(
                    String.valueOf(movieObject.getDouble(OWN_RATING)));

            moviesData[i] = new MovieData(title, path, overview, rating, releaseDate);

            Log.v(LOG_TAG, moviesData[i].toString());
        }
        return moviesData;
    }


    @Override
    protected MovieData[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;

        try {
            final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT = "sort_by";
            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORT, params[0])
                    .appendQueryParameter(API_KEY, BuildConfig.MOVIE_DP_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built url " + builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder sb = new StringBuilder();
            if (inputStream == null) {
                // nothing
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));


            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            if (sb.length() == 0) {
                // stream was empty
                return null;
            }
            moviesJsonStr = sb.toString();
            Log.v(LOG_TAG, "String : " + sb.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Incorrect url");
        } catch (ProtocolException e) {
            Log.e(LOG_TAG, "Incorrect protocol");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with connection");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing reader");
                }
            }
        }

        try {
            return getMoviesFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in json parsing");
        }

        return null;
    }

    @Override
    protected void onPostExecute(MovieData[] result) {
        if (result != null) {
            ArrayAdapter<MovieData> adapter = MainActivityFragment.mMovieDataAdapter;
            if (!adapter.isEmpty())
                adapter.clear();
            adapter.addAll(result);
        }
    }
}
