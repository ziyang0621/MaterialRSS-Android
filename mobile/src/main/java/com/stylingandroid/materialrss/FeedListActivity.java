package com.stylingandroid.materialrss;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.stylingandroid.materialrss.adapter.FeedAdapter;
import com.stylingandroid.materialrss.rss.model.Feed;
import com.stylingandroid.materialrss.rss.model.Item;

public class FeedListActivity extends ActionBarActivity implements FeedConsumer, FeedAdapter.ItemClickListener {
    private static final String DATA_FRAGMENT_TAG = DataFragment.class.getCanonicalName();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_list);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        ImageView overlay = (ImageView)findViewById(R.id.overlay);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new DragController(recyclerView, overlay));

        DataFragment dataFragment = (DataFragment) getFragmentManager().findFragmentByTag(DATA_FRAGMENT_TAG);
        if (dataFragment == null) {
            dataFragment = (DataFragment) Fragment.instantiate(this, DataFragment.class.getName());
            dataFragment.setRetainInstance(true);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(dataFragment, DATA_FRAGMENT_TAG);
            transaction.commit();
        }
    }


    public void setFeed(Feed feed) {
        FeedAdapter adapter = new FeedAdapter(feed.getItems(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void itemClicked(Item item) {
        Intent detailIntent = new Intent(FeedListActivity.this, FeedDetailActivity.class);
        detailIntent.putExtra(FeedDetailActivity.ARG_ITEM, item);
        FeedAdapter.ViewHolder viewHolder = (FeedAdapter.ViewHolder) recyclerView.findViewHolderForItemId(item.getPubDate());
        String titleName = getString(R.string.transition_title);
        String dateName = getString(R.string.transition_date);
        String bodyName = getString(R.string.transition_body);
        Pair<View, String> titlePair = Pair.create(viewHolder.getTitleView(), titleName);
        Pair<View, String> datePair = Pair.create(viewHolder.getDateView(), dateName);
        Pair<View, String> bodyPair = Pair.create(viewHolder.getBodyView(), bodyName);
        @SuppressWarnings("unchecked")
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, titlePair, datePair, bodyPair);
        ActivityCompat.startActivity(this, detailIntent, options.toBundle());
    }
}
