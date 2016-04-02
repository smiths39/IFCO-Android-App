package codeman.ifco.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import codeman.ifco.CustomListView.CustomListViewAdapter;
import codeman.ifco.CustomListView.Movie;
import codeman.ifco.R;
import codeman.ifco.URL_base;

public class NewsFragment extends Fragment implements View.OnClickListener {

    private TextView tvSurvey2015, tvSurvey2013, tvSurvey2012, tvSurvey2011, tvSurvey2010, tvSurvey2009, tvSurvey2008,
            tvSurvey2007, tvSurvey2006, tvSurvey2005, tvSurvey2004, tvParentalAttitudes, tvMinisterForJustice,
            tvAdolescentsSurvey, tvParentSurvey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        initialiseComponents(view);

        return view;
    }

    private String pdfLink(String subject) {
        URL_base URLBase = new URL_base();

        if (subject.equals("2013 Report (English)")) {
            return URLBase.URL_2013_report_english;
        } else if (subject.equals("2013 Report (Irish)")) {
            return URLBase.URL_2013_report_irish;
        } else if (subject.equals("2012 Report (English)")) {
            return URLBase.URL_2012_report_english;
        } else if (subject.equals("2012 Report (Irish)")) {
            return URLBase.URL_2012_report_irish;
        } else if (subject.equals("2011 Report (English)")) {
            return URLBase.URL_2011_report_english;
        } else if (subject.equals("2011 Report (Irish)")) {
            return URLBase.URL_2011_report_irish;
        } else if (subject.equals("2010 Report (English)")) {
            return URLBase.URL_2010_report_english;
        } else if (subject.equals("2010 Report (Irish)")) {
            return URLBase.URL_2010_report_english;
        } else if (subject.equals("2009 Report (English)")) {
            return URLBase.URL_2009_report_english;
        } else if (subject.equals("2009 Report (Irish)")) {
            return URLBase.URL_2009_report_irish;
        } else if (subject.equals("2008 Report (English)")) {
            return URLBase.URL_2008_report_english;
        } else if (subject.equals("2008 Report (Irish)")) {
            return URLBase.URL_2008_report_irish;
        } else if (subject.equals("2007 Report (English)")) {
            return URLBase.URL_2007_report_english;
        } else if (subject.equals("2007 Report (Irish)")) {
            return URLBase.URL_2007_report_english;
        } else if (subject.equals("2006 Report (English)")) {
            return URLBase.URL_2006_report_english;
        } else if (subject.equals("2006 Report (Irish)")) {
            return URLBase.URL_2006_report_irish;
        } else if (subject.equals("2005 Report")) {
            return URLBase.URL_2005_report;
        } else if (subject.equals("2004 Report")) {
            return URLBase.URL_2004_report;
        } else if (subject.equals("Classification Scope")) {
            return URLBase.URL_2006_classification_scope;
        } else if (subject.equals("2006 Press Release")) {
            return URLBase.URL_2006_press_release;
        } else if (subject.equals("Pilot Review")) {
            return URLBase.URL_2006_report_pilot;
        } else if (subject.equals("Assessing Strong Language")) {
            return URLBase.URL_2006_strong_language;
        } else if (subject.equals("Adolescents Survey Results")) {
            return URLBase.URL_adolescent_survey;
        } else if (subject.equals("Press Release")) {
            return URLBase.URL_minister_for_justice;
        } else if (subject.equals("Parental Attitudes (2013)")) {
            return URLBase.URL_parental_attitudes_2013;
        } else if (subject.equals("Parental Attitudes (Post Primary 2015)")) {
            return URLBase.URL_parental_attitudes_2015;
        } else if (subject.equals("Parents Survey Results")) {
            return URLBase.URL_parents_survey;
        } else {
            return null;
        }
    }

    private void downloadFile(String fileURL, File directory) {
        try {
            FileOutputStream file = new FileOutputStream(directory);
            URL url = new URL(fileURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                file.write(buffer, 0, len);
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayDownloadDialog(String title, ArrayList<String> downloadOptions) {
        final CharSequence[] items = downloadOptions.toArray(new CharSequence[downloadOptions.size()]);
        final String[] selectedDownload = new String[1];

        AlertDialog dialog = new AlertDialog.Builder(this.getActivity())
                .setTitle(title)
                .setSingleChoiceItems(items, downloadOptions.size(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDownload[0] = items[which].toString();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (selectedDownload[0].isEmpty()) {
                            Toast.makeText(NewsFragment.this.getActivity(), "Please select a download option", Toast.LENGTH_SHORT).show();
                        } else {
                            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/IFCO");
                            if (!folder.exists()) {
                                folder.getParentFile().mkdirs();
                            }
                            downloadFile(pdfLink(selectedDownload[0]), folder);
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    private void initialiseComponents(View view) {
        tvSurvey2015 = (TextView) view.findViewById(R.id.tvNews2015Survey);
        tvSurvey2013 = (TextView) view.findViewById(R.id.tvNews2013Report);
        tvSurvey2012 = (TextView) view.findViewById(R.id.tvNews2012Report);
        tvSurvey2011 = (TextView) view.findViewById(R.id.tvNews2011Report);
        tvSurvey2010 = (TextView) view.findViewById(R.id.tvNews2010Report);
        tvSurvey2009 = (TextView) view.findViewById(R.id.tvNews2009Report);
        tvSurvey2008 = (TextView) view.findViewById(R.id.tvNews2008Report);
        tvSurvey2007 = (TextView) view.findViewById(R.id.tvNews2007Report);
        tvSurvey2006 = (TextView) view.findViewById(R.id.tvNews2006Report);
        tvSurvey2005 = (TextView) view.findViewById(R.id.tvNews2005Report);
        tvSurvey2004 = (TextView) view.findViewById(R.id.tvNews2004Report);
        tvParentalAttitudes = (TextView) view.findViewById(R.id.tvNewsParentalAttitudes);
        tvMinisterForJustice = (TextView) view.findViewById(R.id.tvNewsMinisterForJustice);
        tvAdolescentsSurvey = (TextView) view.findViewById(R.id.tvNewsAdolescentsSurvey);
        tvParentSurvey = (TextView) view.findViewById(R.id.tvNewsParentSurvey);

        tvSurvey2015.setOnClickListener(this);
        tvSurvey2013.setOnClickListener(this);
        tvSurvey2012.setOnClickListener(this);
        tvSurvey2011.setOnClickListener(this);
        tvSurvey2010.setOnClickListener(this);
        tvSurvey2009.setOnClickListener(this);
        tvSurvey2008.setOnClickListener(this);
        tvSurvey2007.setOnClickListener(this);
        tvSurvey2006.setOnClickListener(this);
        tvSurvey2005.setOnClickListener(this);
        tvSurvey2004.setOnClickListener(this);
        tvParentalAttitudes.setOnClickListener(this);
        tvMinisterForJustice.setOnClickListener(this);
        tvAdolescentsSurvey.setOnClickListener(this);
        tvParentSurvey.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ArrayList<String> downloadOptions = new ArrayList<>();
        String title;

        switch (view.getId()) {
            case R.id.tvNews2015Survey:
                title = getString(R.string.survey_2015_title);
                downloadOptions.add("Parental Attitudes (2013)");
                downloadOptions.add("Parental Attitudes (Post Primary 2015)");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2013Report:
                title = getString(R.string.survey_2013_title);
                downloadOptions.add("2013 Report (English)");
                downloadOptions.add("2013 Report (Irish)");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2012Report:
                title = getString(R.string.survey_2012_title);
                downloadOptions.add("2012 Report (English)");
                downloadOptions.add("2012 Report (Irish)");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2011Report:
                title = getString(R.string.survey_2011_title);
                downloadOptions.add("2011 Report (English)");
                downloadOptions.add("2011 Report (Irish)");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2010Report:
                title = getString(R.string.survey_2010_title);
                downloadOptions.add("2010 Report (English)");
                downloadOptions.add("2010 Report (Irish)");
                ////displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2009Report:
                title = getString(R.string.survey_2009_title);
                downloadOptions.add("2009 Report (English)");
                downloadOptions.add("2009 Report (Irish)");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2008Report:
                title = getString(R.string.survey_2008_title);
                downloadOptions.add("2008 Report (English)");
                downloadOptions.add("2008 Report (Irish)");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2007Report:
                title = getString(R.string.survey_2007_title);
                downloadOptions.add("2007 Report (English)");
                downloadOptions.add("2007 Report (Irish)");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2006Report:
                title = getString(R.string.survey_2006_title);
                downloadOptions.add("2006 Report (English)");
                downloadOptions.add("2006 Report (Irish)");
                downloadOptions.add("2006 Press Release");
                downloadOptions.add("Assessing Strong Language");
                downloadOptions.add("Classification Scope");
                downloadOptions.add("Pilot Review");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2005Report:
                title = getString(R.string.survey_2005_title);
                downloadOptions.add("2005 Report");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNews2004Report:
                title = getString(R.string.survey_2004_title);
                downloadOptions.add("2004 Report");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNewsParentalAttitudes:
                title = getString(R.string.parental_attitudes_title);
                downloadOptions.add("Parental Attitudes");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNewsMinisterForJustice:
                title = getString(R.string.minister_for_justice_title);
                downloadOptions.add("Press Release");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNewsAdolescentsSurvey:
                title = getString(R.string.adolescents_survey_title);
                downloadOptions.add("Adolescents Survey Results");
                //displayDownloadDialog(title, downloadOptions);
                break;
            case R.id.tvNewsParentSurvey:
                title = getString(R.string.parents_survey_title);
                downloadOptions.add("Parents Survey Results");
                //displayDownloadDialog(title, downloadOptions);
                break;
            default:
                break;
        }
    }
}

