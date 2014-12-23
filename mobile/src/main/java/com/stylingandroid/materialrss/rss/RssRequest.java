package com.stylingandroid.materialrss.rss;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.stylingandroid.materialrss.rss.model.Feed;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RssRequest extends Request<Feed> {
    private final Response.Listener<Feed> feedListener;

    public RssRequest(int method, String url, Response.Listener<Feed> feedListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.feedListener = feedListener;
    }

    @Override
    protected Response<Feed> parseNetworkResponse(NetworkResponse response) {
        InputStream inputStream = new ByteArrayInputStream(response.data);
        SaRssParser parser = SaRssParser.newInstance(inputStream);
        Feed feed;
        try {
            feed = parser.parse();
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
        return Response.success(feed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Feed response) {
        feedListener.onResponse(response);
    }
}
