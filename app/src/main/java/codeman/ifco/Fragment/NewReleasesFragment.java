package codeman.ifco.Fragment;

import android.app.Dialog;
import android.app.FragmentManager;
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

public class NewReleasesFragment extends Fragment {

    private View view;

    private ListView listView;
    private ProgressDialog progressDialog;
    private CustomListViewAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    private static ArrayList<String> movieTitle = new ArrayList<>();
    private static ArrayList<String> movieRating = new ArrayList<>();
    private static ArrayList<String> movieRunningTime = new ArrayList<>();
    private static ArrayList<String> movieReleaseDate = new ArrayList<>();

    private final static String upcomingURL = "http://ifco.ie/website/ifco/ifcoweb.nsf/web/currentfilms?opendocument&current=yes&type=graphic";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_releases, container, false);
        listView = (ListView) view.findViewById(R.id.lvNewReleases);
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
            movieTitle = "The" + movieTitle.substring(1, movieTitle.length() - 5);
            movieTitle = movieTitle.replaceAll("\\s+", " ");
        } else if (movieTitle.toUpperCase().trim().contains(", A")) {
            movieTitle = "A" + movieTitle.substring(1, movieTitle.length() - 3);
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
        String[] year= {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};

        String[] date = {"31st", "30th", "29th", "28th", "27th", "26th", "25th", "24th", "23rd", "22nd",
                "21st", "20th", "19th", "18th", "17th", "16th", "15th", "14th", "13th", "12th",
                "11th", "10th", "9th", "8th", "7th", "6th", "5th", "4th", "3rd", "2nd", "1st"};

        int index, value, foundCharacter, runningTimeStart;

        for (int i = 0; i < webpageCode.size(); i++) {
            index = 0;
            do {
                foundCharacter = webpageCode.get(i).indexOf(date[index]);

                if (date[index].length() == 3) {
                    value = 3;
                } else {
                    value = 4;
                }
                index++;

                if (index == date.length) {
                    break;
                }
            } while (foundCharacter == -1);

            index = 0;
            do {
                runningTimeStart = webpageCode.get(i).indexOf(year[index]);
                index++;
            } while (runningTimeStart == -1);

            movieRating.add(webpageCode.get(i).substring(0, 4));
            movieTitle.add(reformatTitle(webpageCode.get(i).substring(4, foundCharacter)).trim());

            if (value == 3) {
                movieReleaseDate.add(webpageCode.get(i).substring(foundCharacter, foundCharacter + 12));
            } else {
                movieReleaseDate.add(webpageCode.get(i).substring(foundCharacter, foundCharacter + 13));
            }
            movieRunningTime.add(reformatRunningTime(webpageCode.get(i).substring(runningTimeStart + 4, webpageCode.get(i).length()-1)));
        }
    }

    private class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewReleasesFragment.this.getActivity());
            progressDialog.setMessage("Retrieving new releases...");
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
                    ageRatingIcon = ((BitmapDrawable) NewReleasesFragment.this.getResources().getDrawable(R.drawable.rating_18)).getBitmap();
                } else if (rating.trim().contains("16")) {
                    ageRatingIcon = ((BitmapDrawable) NewReleasesFragment.this.getResources().getDrawable(R.drawable.rating_16)).getBitmap();
                } else if (rating.trim().contains("15A")) {
                    ageRatingIcon = ((BitmapDrawable) NewReleasesFragment.this.getResources().getDrawable(R.drawable.rating_15a)).getBitmap();
                } else if (rating.trim().contains("12")) {
                    ageRatingIcon = ((BitmapDrawable) NewReleasesFragment.this.getResources().getDrawable(R.drawable.rating_12a)).getBitmap();
                } else if (rating.trim().contains("PG")) {
                    ageRatingIcon = ((BitmapDrawable) NewReleasesFragment.this.getResources().getDrawable(R.drawable.rating_pg)).getBitmap();
                } else {
                    ageRatingIcon = ((BitmapDrawable) NewReleasesFragment.this.getResources().getDrawable(R.drawable.rating_g)).getBitmap();
                }
                movie.setMovieTitle(movieTitle.get(i).trim());
                movie.setAgeRating(ageRatingIcon);
                movieList.add(movie);
            }
            adapter = new CustomListViewAdapter(NewReleasesFragment.this, movieList);
            listView.setAdapter(adapter);
            progressDialog.dismiss();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Dialog dialog = new Dialog(NewReleasesFragment.this.getActivity(), R.style.cust_dialog);
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
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}

