package edu.appstate.huk.pocketturchin2;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import edu.appstate.huk.pocketturchin2.Art;

import java.util.List;

import static edu.appstate.huk.pocketturchin2.R.drawable.ic_favorite;

/**
 * An activity representing a list of Pieces. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PieceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PieceListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private boolean favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View recyclerView = findViewById(R.id.piece_list);
                assert recyclerView != null;
                if(!favorite) {
                    favorite = true;
                    setupRecyclerView((RecyclerView) recyclerView, favorite);
                }
                else if(favorite)
                {
                    favorite = false;
                    setupRecyclerView((RecyclerView) recyclerView, favorite);
                }
            }
        });

        View recyclerView = findViewById(R.id.piece_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView, favorite);

        if (findViewById(R.id.piece_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        final FloatingActionButton fabSearch = (FloatingActionButton) findViewById(R.id.fabSearch);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText search = (EditText) findViewById(R.id.searchBox);
                search.setVisibility(View.VISIBLE);
                fabSearch.setVisibility(View.GONE);
                search.setOnKeyListener(new View.OnKeyListener()
                {
                    String input;
                    public boolean onKey(View v, int keyCode, KeyEvent event)
                    {
                        if (event.getAction() == KeyEvent.ACTION_DOWN)
                        {
                            switch (keyCode)
                            {
                                case KeyEvent.KEYCODE_ENTER:
                                    search.setVisibility(View.GONE);
                                    fabSearch.setVisibility(View.VISIBLE);
                                    input = search.getText().toString();
                                    Context context = v.getContext();
                                    Intent intent = new Intent(context, PieceDetailActivity.class);
                                    Log.d("k","This is the id inputted = " + input.substring(0,input.length()));
                                    intent.putExtra(PieceDetailFragment.ARG_ITEM_ID,
                                            Art.ITEMS.get(Integer.parseInt(input.substring(0,input.length()))-1).id);
                                    context.startActivity(intent);
                                default:
                                    break;
                            }
                        }
                        return false;
                    }

                });
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, boolean fav) {
        if(fav)
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Art.FAVITEMS));
        else
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Art.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Art.ArtItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<Art.ArtItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.piece_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            //This shows the item positions and descriptors
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mTitleView.setText(mValues.get(position).title);
            holder.mArtistView.setText(mValues.get(position).artist);

            Drawable a = getResources().getDrawable(mValues.get(position).picture);
            BitmapDrawable b = (BitmapDrawable) a;
            Bitmap c = Bitmap.createScaledBitmap(b.getBitmap(),100,100,false);
            holder.mThumbnailView.setImageBitmap(c);

            holder.mFavoriteView.setImageResource(ic_favorite);
            holder.mFavoriteView.setVisibility(mValues.get(position).favorite ? View.VISIBLE : View.GONE);


            //The onClick stuff, we shouldn't have to edit this
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(PieceDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        PieceDetailFragment fragment = new PieceDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.piece_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PieceDetailActivity.class);
                        intent.putExtra(PieceDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        Log.d("k","the id is = " + holder.mItem.id);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mTitleView;
            public final TextView mArtistView;
            public final ImageView mFavoriteView;
            public final ImageView mThumbnailView;
            public Art.ArtItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mTitleView = (TextView) view.findViewById(R.id.title);
                mArtistView = (TextView) view.findViewById(R.id.artist);
                mThumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
                mFavoriteView = (ImageView) view.findViewById(R.id.piece_favorite);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitleView.getText() + "'";
            }
        }
    }
}
