package codeman.ifco.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import codeman.ifco.R;
import codeman.ifco.URL_base;

public class GuidelinesFragment extends Fragment implements View.OnClickListener {

    private ImageView ivFilmRatingG, ivFilmRatingPG, ivFilmRating12A, ivFilmRating15A, ivFilmRating16, ivFilmRating18,
                      ivDVDRatingG, ivDVDRatingPG, ivDVDRating12, ivDVDRating15, ivDVDRating18;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guidelines, container, false);
        initialiseComponents(view);

        return view;
    }

    private void initialiseComponents(View view) {
        ivFilmRatingG = (ImageView) view.findViewById(R.id.ivFilmGuidelinesG);
        ivFilmRatingPG = (ImageView) view.findViewById(R.id.ivFilmGuidelinesPG);
        ivFilmRating12A = (ImageView) view.findViewById(R.id.ivFilmGuidelines12A);
        ivFilmRating15A = (ImageView) view.findViewById(R.id.ivFilmGuidelines15A);
        ivFilmRating16 = (ImageView) view.findViewById(R.id.ivFilmGuidelines16);
        ivFilmRating18 = (ImageView) view.findViewById(R.id.ivFilmGuidelines18);
        ivDVDRatingG = (ImageView) view.findViewById(R.id.ivDVDGuidelinesG);
        ivDVDRatingPG = (ImageView) view.findViewById(R.id.ivDVDGuidelinesPG);
        ivDVDRating12 = (ImageView) view.findViewById(R.id.ivDVDGuidelines12);
        ivDVDRating15 = (ImageView) view.findViewById(R.id.ivDVDGuidelines15);
        ivDVDRating18 = (ImageView) view.findViewById(R.id.ivDVDGuidelines18);

        ivFilmRatingG.setOnClickListener(this);
        ivFilmRatingPG.setOnClickListener(this);
        ivFilmRating12A.setOnClickListener(this);
        ivFilmRating15A.setOnClickListener(this);
        ivFilmRating16.setOnClickListener(this);
        ivFilmRating18.setOnClickListener(this);
        ivDVDRatingG.setOnClickListener(this);
        ivDVDRatingPG.setOnClickListener(this);
        ivDVDRating12.setOnClickListener(this);
        ivDVDRating15.setOnClickListener(this);
        ivDVDRating18.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment ratingFragment = null;
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (view.getId()) {
            case R.id.ivFilmGuidelinesG:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "FILM_G");
                break;
            case R.id.ivFilmGuidelinesPG:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "FILM_PG");
                break;
            case R.id.ivFilmGuidelines12A:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "FILM_12A");
                break;
            case R.id.ivFilmGuidelines15A:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "FILM_15A");
                break;
            case R.id.ivFilmGuidelines16:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "FILM_16");
                break;
            case R.id.ivFilmGuidelines18:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "FILM_18");
                break;
            case R.id.ivDVDGuidelinesG:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "DVD_G");
                break;
            case R.id.ivDVDGuidelinesPG:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "DVD_PG");
                break;
            case R.id.ivDVDGuidelines12:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "DVD_12");
                break;
            case R.id.ivDVDGuidelines15:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "DVD_15");
                break;
            case R.id.ivDVDGuidelines18:
                ratingFragment = new GuidelineDetailsFragment();
                bundle.putString("TYPE", "DVD_18");
                break;
            default:
                break;
        }

        ratingFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.guidelines_fragment, ratingFragment);
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.addToBackStack(null);
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.commit();
    }
}

