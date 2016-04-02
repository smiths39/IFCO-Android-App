package codeman.ifco.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import codeman.ifco.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btnNewReleases, btnUpcomingReleases;

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTheme(R.style.AppTheme);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initialiseComponents(view);

        return view;
    }

    private void initialiseComponents(View view) {
        btnNewReleases = (Button) view.findViewById(R.id.btnNewReleases);
        btnUpcomingReleases = (Button) view.findViewById(R.id.btnUpcomingReleases);

        btnNewReleases.setOnClickListener(this);
        btnUpcomingReleases.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewReleases:
                NewReleasesFragment newReleasesFragment = new NewReleasesFragment();
                getFragmentManager().beginTransaction()
                                    .replace(((ViewGroup)getView().getParent()).getId(), newReleasesFragment)
                                    .addToBackStack(null)
                                    .commit();
                break;

            case R.id.btnUpcomingReleases:
                UpcomingReleasesFragment upcomingReleasesFragment = new UpcomingReleasesFragment();
                getFragmentManager().beginTransaction()
                                    .replace(((ViewGroup)getView().getParent()).getId(), upcomingReleasesFragment)
                        .addToBackStack(null)
                                    .commit();
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}