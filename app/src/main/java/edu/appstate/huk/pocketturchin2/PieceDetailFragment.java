package edu.appstate.huk.pocketturchin2;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.appstate.huk.pocketturchin2.dummy.DummyContent;

/**
 * A fragment representing a single Piece detail screen.
 * This fragment is either contained in a {@link PieceListActivity}
 * in two-pane mode (on tablets) or a {@link PieceDetailActivity}
 * on handsets.
 */
public class PieceDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Art.ArtItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PieceDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Art.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.title);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.piece_detail, container, false);
        TextView artistView = rootView.findViewById(R.id.piece_artist);
        TextView detailView = rootView.findViewById(R.id.piece_detail);
        ImageView pictureView = rootView.findViewById(R.id.piece_picture);
        CheckBox favoriteBox = rootView.findViewById(R.id.piece_favorite);

        if (mItem != null) {

            artistView.setText("Created By: " + mItem.artist);
            detailView.setText(mItem.description);

            pictureView.setImageResource(mItem.picture);

            favoriteBox.setChecked(mItem.favorite);
            favoriteBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    mItem.favorite = isChecked;
                }
            });
        }
        return rootView;
    }
}
