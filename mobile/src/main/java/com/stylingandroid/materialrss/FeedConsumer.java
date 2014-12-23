package com.stylingandroid.materialrss;

import com.stylingandroid.materialrss.rss.model.Feed;

public interface FeedConsumer {
    void setFeed(Feed feed);

    void handleError(String message);
}
