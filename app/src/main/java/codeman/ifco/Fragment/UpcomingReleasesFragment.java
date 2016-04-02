package codeman.ifco.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import codeman.ifco.CustomListView.CustomListViewAdapter;
import codeman.ifco.CustomListView.Movie;
import codeman.ifco.R;

public class UpcomingReleasesFragment extends Fragment {

    private View view;

    private ListView listView;
    private ProgressDialog progressDialog;
    private CustomListViewAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    private static ArrayList<String> movieTitle = new ArrayList<>();
    private static ArrayList<String> movieRating = new ArrayList<>();
    private static ArrayList<String> movieRunningTime = new ArrayList<>();
    private static ArrayList<String> movieReleaseDate = new ArrayList<>();

    private final static String upcomingURL = "http://ifco.ie/website/ifco/ifcoweb.nsf/web/upcomingfilms?opendocument&upcoming=yes&type=graphic";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcoming_releases, container, false);
        listView = (ListView) view.findViewById(R.id.lvUpcomingReleases);
        clearAllLists();
        new RetrieveFeedTask().execute();

        return view;
    }


    private void clearAllLists() {
        movieList.clear();
        movieTitle.clear();
        movieRating.clear();
        movieRunningTime.clear();
        movieReleaseDate.clear();
    }

    private String reformatTitle(String movieTitle) {
        if (movieTitle.toUpperCase().contains(", THE")) {
            movieTitle = "The " + movieTitle.substring(0, movieTitle.length() - 5);
        } else if (movieTitle.toUpperCase().contains(", A")) {
            movieTitle = "A " + movieTitle.substring(0, movieTitle.length() - 3);
        }
        return movieTitle;
    }

    private String reformatRunningTime(String runningTime) {
        return runningTime.substring(0, runningTime.indexOf("'")) + " min";
    }

    private void retrieveUpcomingReleases() {
        try {
            URLConnection connection = establishConnection(upcomingURL);
            ArrayList<String> webpageCode = readInput(connection);
            webpageCode = extractHTMLTags(webpageCode);
            reformatData(webpageCode);
        } catch (IOException exception) {
            Log.e("IO Exception: ", exception.toString());
        }
    }

    private URLConnection establishConnection(String webURL) throws IOException {
        URL url = new URL(webURL);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        return connection;
    }

    private ArrayList<String> readInput(URLConnection connection) throws IOException {
        ArrayList<String> code = new ArrayList<>();
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        boolean ignore = true;

        while ((line = bufferedReader.readLine()) != null) {
            if (ignore) {
                if (line.contains("Running")) {
                    ignore = false;
                }
                continue;
            } else {
                code.add(line);
            }
        }
        return code;
    }

    private ArrayList<String> extractHTMLTags(ArrayList<String> webpageCode) {
        ArrayList<String> movieData = new ArrayList<>();
        String line = null;

        for (int i = 0; i < webpageCode.size(); i++) {
            line = webpageCode.get(i).replaceAll("\\<.*?>","");
            line = line.trim();

            if (!line.equals("") || !line.isEmpty()) {
                movieData.add(line);
            }
        }
        return movieData;
    }

    private void reformatData(ArrayList<String> webpageCode) {
        String[] ratings = {" 18 ", " 16 ", " 15A ", " 12A ", " PG ", " G "};
        int index, foundCharacter;

        for (int i = 0; i < webpageCode.size(); i++) {
            index = 0;
            do {
                foundCharacter = webpageCode.get(i).indexOf(ratings[index]);
                index++;
                if (index == ratings.length) {
                    break;
                }
            } while (foundCharacter == -1);

            movieTitle.add(reformatTitle(webpageCode.get(i).substring(0, foundCharacter)));
            movieRating.add(webpageCode.get(i).substring(foundCharacter + 1, foundCharacter + 5));
            movieRunningTime.add(reformatRunningTime(webpageCode.get(i).substring(foundCharacter + 6, webpageCode.get(i).indexOf('"'))));
            movieReleaseDate.add(webpageCode.get(i).substring(webpageCode.get(i).indexOf('"') + 2, webpageCode.get(i).length()));
        }
    }

    private class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UpcomingReleasesFragment.this.getActivity());
            progressDialog.setMessage("Retrieving upcoming releases...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            retrieveUpcomingReleases();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            for (int i = 0; i < movieTitle.size(); i++) {
                Movie movie = new Movie();

                Bitmap ageRatingIcon;
                String rating = movieRating.get(i);

                if (rating.trim().contains("18")) {
                    ageRatingIcon = ((BitmapDrawable) UpcomingReleasesFragment.this.getResources().getDrawable(R.drawable.rating_18)).getBitmap();
                } else if (rating.trim().contains("16")) {
                    ageRatingIcon = ((BitmapDrawable) UpcomingReleasesFragment.this.getResources().getDrawable(R.drawable.rating_16)).getBitmap();
                } else if (rating.trim().contains("15A")) {
                    ageRatingIcon = ((BitmapDrawable) UpcomingReleasesFragment.this.getResources().getDrawable(R.drawable.rating_15a)).getBitmap();
                } else if (rating.trim().contains("12")) {
                    ageRatingIcon = ((BitmapDrawable) UpcomingReleasesFragment.this.getResources().getDrawable(R.drawable.rating_12a)).getBitmap();
                } else if (rating.trim().contains("PG")) {
                    ageRatingIcon = ((BitmapDrawable) UpcomingReleasesFragment.this.getResources().getDrawable(R.drawable.rating_pg)).getBitmap();
                } else {
                    ageRatingIcon = ((BitmapDrawable) UpcomingReleasesFragment.this.getResources().getDrawable(R.drawable.rating_g)).getBitmap();
                }
                movie.setMovieTitle(movieTitle.get(i).trim());
                movie.setAgeRating(ageRatingIcon);
                movieList.add(movie);
            }
            progressDialog.dismiss();

            adapter = new CustomListViewAdapter(UpcomingReleasesFragment.this, movieList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Dialog dialog = new Dialog(UpcomingReleasesFragment.this.getActivity(), R.style.cust_dialog);
                    dialog.setContentView(R.layout.dialog_movie_description);
                    dialog.setTitle(movieTitle.get(position));

                    TextView tvMovieRating = (TextView) dialog.findViewById(R.id.tvUpcomingReleasesRatingAnswer);
                    TextView tvMovieRunningTime = (TextView) dialog.findViewById(R.id.tvUpcomingReleasesRunningTimeAnswer);
                    TextView tvMovieReleaseDate = (TextView) dialog.findViewById(R.id.tvUpcomingReleaseDateAnswer);

                    tvMovieRating.setText(movieRating.get(position).trim());
                    tvMovieRunningTime.setText(movieRunningTime.get(position).trim());
                    tvMovieReleaseDate.setText(movieReleaseDate.get(position).trim());

                    Button dialogButton = (Button) dialog.findViewById(R.id.btnDialogOK);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}
