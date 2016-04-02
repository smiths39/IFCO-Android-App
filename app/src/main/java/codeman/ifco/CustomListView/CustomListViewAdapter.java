package codeman.ifco.CustomListView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.BaseAdapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import codeman.ifco.R;

public class CustomListViewAdapter extends BaseAdapter {

    private Fragment fragment;
    private List<Movie> movieItems;
    private LayoutInflater inflater;

    public CustomListViewAdapter(Fragment fragment, List<Movie> movieItems) {
        this.fragment = fragment;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row, null);
        }
        Movie movie = movieItems.get(position);

        ImageView ivMovieRating = (ImageView) convertView.findViewById(R.id.ivAgeRatingListView);
        TextView tvMovieTitle = (TextView) convertView.findViewById(R.id.tvMovieTitleListView);

        ivMovieRating.setImageBitmap(movie.getAgeRating());
        tvMovieTitle.setText(movie.getMovieTitle());

        return convertView;
    }
}