package codeman.ifco.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import codeman.ifco.R;

public class GuidelineDetailsFragment extends Fragment {

    private ImageView ivAgeTitle;
    private RelativeLayout rlGuidelinesContent, rlGuidelinesNotes;
    private TextView tvTitle, tvIntroduction, tvTheme, tvViolence, tvSexualContent, tvLanguage, tvDrugs, tvNotes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_guidelines, container, false);

        initialiseComponents(view);
        displayRatingContent(getBundlesContent());

        return view;
    }

    private void initialiseComponents(View view) {
        ivAgeTitle = (ImageView) view.findViewById(R.id.ivRatingGuideline);
        tvTitle = (TextView) view.findViewById(R.id.tvRatingGuidelinesTitle);
        tvIntroduction = (TextView) view.findViewById(R.id.tvRatingGuidelinesIntroduction);
        tvTheme = (TextView) view.findViewById(R.id.tvRatingGuidelinesTheme);
        tvViolence = (TextView) view.findViewById(R.id.tvRatingGuidelinesViolence);
        tvSexualContent = (TextView) view.findViewById(R.id.tvRatingGuidelinesSexualContent);
        tvLanguage = (TextView) view.findViewById(R.id.tvRatingGuidelinesLanguage);
        tvDrugs = (TextView) view.findViewById(R.id.tvRatingGuidelinesDrugs);
        tvNotes = (TextView) view.findViewById(R.id.tvRatingGuidelinesNote);
        rlGuidelinesContent = (RelativeLayout) view.findViewById(R.id.rlGuidelinesContent);
        rlGuidelinesNotes = (RelativeLayout) view.findViewById(R.id.rlGuidelinesNotes);
    }

    private String getBundlesContent() {
        Bundle bundle = this.getArguments();
        return bundle.getString("TYPE");
    }

    private void displayRatingContent(String ratingType) {
        if (ratingType.equals("FILM_G")) {
            ivAgeTitle.setImageResource(R.drawable.rating_g);
            tvTitle.setText(R.string.certificate_title_G);
            tvIntroduction.setText(R.string.cert_film_G_introduction);
            tvTheme.setText(R.string.cert_film_G_theme);
            tvViolence.setText(R.string.cert_film_G_violence);
            tvSexualContent.setText(R.string.cert_film_G_sexual_content);
            tvLanguage.setText(R.string.cert_film_G_language);
            tvDrugs.setText(R.string.cert_film_G_drugs);

            hideRelativeLayout("NOTES");
        } else if (ratingType.equals("FILM_PG")) {
            ivAgeTitle.setImageResource(R.drawable.rating_pg);
            tvTitle.setText(R.string.certificate_title_PG);
            tvIntroduction.setText(R.string.cert_film_PG_introduction);
            tvTheme.setText(R.string.cert_film_PG_theme);
            tvViolence.setText(R.string.cert_film_PG_violence);
            tvSexualContent.setText(R.string.cert_film_PG_sexual_content);
            tvLanguage.setText(R.string.cert_film_PG_language);
            tvDrugs.setText(R.string.cert_film_PG_drugs);

            hideRelativeLayout("NOTES");
        } else if (ratingType.equals("FILM_12A")) {
            ivAgeTitle.setImageResource(R.drawable.rating_12a);
            tvTitle.setText(R.string.certificate_title_12A);
            tvIntroduction.setText(R.string.cert_film_12A_introduction);
            tvTheme.setText(R.string.cert_film_12A_theme);
            tvViolence.setText(R.string.cert_film_12A_violence);
            tvSexualContent.setText(R.string.cert_film_12A_sexual_content);
            tvLanguage.setText(R.string.cert_film_12A_language);
            tvDrugs.setText(R.string.cert_film_12A_drugs);

            hideRelativeLayout("NOTES");
        } else if (ratingType.equals("FILM_15A")) {
            ivAgeTitle.setImageResource(R.drawable.rating_15a);
            tvTitle.setText(R.string.certificate_title_15A);
            tvIntroduction.setText(R.string.cert_film_15A_introduction);
            tvTheme.setText(R.string.cert_film_15A_theme);
            tvViolence.setText(R.string.cert_film_15A_violence);
            tvSexualContent.setText(R.string.cert_film_15A_sexual_content);
            tvLanguage.setText(R.string.cert_film_15A_language);
            tvDrugs.setText(R.string.cert_film_15A_drugs);
            tvNotes.setText(R.string.cert_film_15A_note);
        } else if (ratingType.equals("FILM_16")) {
            ivAgeTitle.setImageResource(R.drawable.rating_16);
            tvTitle.setText(R.string.certificate_title_16);
            tvIntroduction.setText(R.string.cert_film_16_introduction);
            tvTheme.setText(R.string.cert_film_16_theme);
            tvViolence.setText(R.string.cert_film_16_violence);
            tvSexualContent.setText(R.string.cert_film_16_sexual_content);
            tvLanguage.setText(R.string.cert_film_16_language);
            tvDrugs.setText(R.string.cert_film_16_drugs);
            tvNotes.setText(R.string.cert_film_16_note);
        } else if (ratingType.equals("FILM_18")) {
            ivAgeTitle.setImageResource(R.drawable.rating_18);
            tvTitle.setText(R.string.certificate_title_18);
            tvIntroduction.setText(R.string.cert_film_18_introduction);

            hideRelativeLayout("CONTENT");
        } else if (ratingType.equals("DVD_G")) {
            ivAgeTitle.setImageResource(R.drawable.rating_dvd_g);
            tvTitle.setText(R.string.certificate_title_G);
            tvIntroduction.setText(R.string.cert_dvd_G_introduction);
            tvTheme.setText(R.string.cert_dvd_G_theme);
            tvViolence.setText(R.string.cert_dvd_G_violence);
            tvSexualContent.setText(R.string.cert_dvd_G_sexual_content);
            tvLanguage.setText(R.string.cert_dvd_G_language);
            tvDrugs.setText(R.string.cert_dvd_G_drugs);

            hideRelativeLayout("NOTES");
        } else if (ratingType.equals("DVD_PG")) {
            ivAgeTitle.setImageResource(R.drawable.rating_dvd_pg);
            tvTitle.setText(R.string.certificate_title_PG);
            tvIntroduction.setText(R.string.cert_dvd_PG_introduction);
            tvTheme.setText(R.string.cert_dvd_PG_theme);
            tvViolence.setText(R.string.cert_dvd_PG_violence);
            tvSexualContent.setText(R.string.cert_dvd_PG_sexual_content);
            tvLanguage.setText(R.string.cert_dvd_PG_language);
            tvDrugs.setText(R.string.cert_dvd_PG_drugs);

            hideRelativeLayout("NOTES");
        } else if (ratingType.equals("DVD_12")) {
            ivAgeTitle.setImageResource(R.drawable.rating_dvd_12);
            tvTitle.setText(R.string.certificate_title_12A);
            tvIntroduction.setText(R.string.cert_dvd_12_introduction);
            tvTheme.setText(R.string.cert_dvd_12_theme);
            tvViolence.setText(R.string.cert_dvd_12_violence);
            tvSexualContent.setText(R.string.cert_dvd_12_sexual_content);
            tvLanguage.setText(R.string.cert_dvd_12_language);
            tvDrugs.setText(R.string.cert_dvd_12_drugs);

            hideRelativeLayout("NOTES");
        } else if (ratingType.equals("DVD_15")) {
            ivAgeTitle.setImageResource(R.drawable.rating_dvd_15);
            tvTitle.setText(R.string.certificate_title_15A);
            tvIntroduction.setText(R.string.cert_dvd_15_introduction);
            tvTheme.setText(R.string.cert_dvd_15_theme);
            tvViolence.setText(R.string.cert_dvd_15_violence);
            tvSexualContent.setText(R.string.cert_dvd_15_sexual_content);
            tvLanguage.setText(R.string.cert_dvd_15_language);
            tvDrugs.setText(R.string.cert_dvd_15_drugs);

            hideRelativeLayout("NOTES");
        } else if (ratingType.equals("DVD_18")) {
            ivAgeTitle.setImageResource(R.drawable.rating_dvd_18);
            tvTitle.setText(R.string.certificate_title_18);
            tvIntroduction.setText(R.string.cert_dvd_18_introduction);

            hideRelativeLayout("CONTENT");
        }
    }

    private void hideRelativeLayout(String type) {
        if (type.equals("NOTES")) {
            rlGuidelinesNotes.setVisibility(View.GONE);
        } else if (type.equals("CONTENT")) {
            rlGuidelinesContent.setVisibility(View.GONE);
        }
    }
}

