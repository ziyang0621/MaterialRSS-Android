package com.stylingandroid.materialrss.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylingandroid.materialrss.R;
import com.stylingandroid.materialrss.rss.model.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());

    private List<Item> items;
    private ItemClickListener itemClickListener;

    public FeedAdapter(List<Item> objects, @NonNull ItemClickListener itemClickListener) {
        this.items = objects;
        this.itemClickListener = itemClickListener;
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final TextView title;
        private final TextView description;
        private final TextView date;

        public static ViewHolder newInstance(View parent) {
            TextView title = (TextView)parent.findViewById(R.id.feed_item_title);
            TextView description = (TextView)parent.findViewById(R.id.feed_item_description);
            TextView date = (TextView)parent.findViewById(R.id.feed_item_date);
            return new ViewHolder(parent, title, description, date);
        }

        private ViewHolder(View parent, TextView title, TextView description, TextView date) {
            super(parent);
            this.parent = parent;
            this.title = title;
            this.description = description;
            this.date = date;
        }

        public void setTitle(CharSequence text) {
            title.setText(text);
        }

        public void setDescription(CharSequence text) {
            description.setText(text);
        }

        public void setDate(CharSequence text) {
            date.setText(text);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.feed_list_item, viewGroup, false);
        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Item item = items.get(position);
        viewHolder.setTitle(item.getTitle());
        viewHolder.setDescription(Html.fromHtml(item.getDescription()));
        viewHolder.setDate(dateFormat.format(new Date(item.getPubDate())));
        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(item);
            }
        });
     }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface ItemClickListener {
        public void itemClicked(Item item);
    }
}
