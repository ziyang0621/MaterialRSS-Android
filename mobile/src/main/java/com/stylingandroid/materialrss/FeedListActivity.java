package com.stylingandroid.materialrss;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.stylingandroid.materialrss.adapter.FeedAdapter;
import com.stylingandroid.materialrss.rss.model.Feed;
import com.stylingandroid.materialrss.rss.model.Item;

public class FeedListActivity extends ActionBarActivity implements FeedConsumer, FeedAdapter.ItemClickListener {
    private static final String DATA_FRAGMENT_TAG = DataFragment.class.getCanonicalName();

    private ListView listView;
    private FeedAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_list);

//        listView = (ListView)findViewById(android.R.id.list);
//        listView.setOnItemClickListener(new ItemClickListener());

        recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

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
        startActivity(detailIntent);
    }

//    private class ItemClickListener implements AdapterView.OnItemClickListener {
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent detailIntent = new Intent(FeedListActivity.this, FeedDetailActivity.class);
//            Item item = adapter.getItem(position);
//            detailIntent.putExtra(FeedDetailActivity.ARG_ITEM, item);
//            startActivity(detailIntent);
//        }
//    }
}
