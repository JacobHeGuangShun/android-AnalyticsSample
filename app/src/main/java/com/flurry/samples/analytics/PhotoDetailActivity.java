/*
 *  Copyright 2015 Yahoo Inc.
 *  Licensed under the terms of the zLib license. Please see LICENSE file for terms.
 */

package com.flurry.samples.analytics;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;


/**
 * Class that manages the photo detail screen.
 */
public class PhotoDetailActivity extends Activity {

    private Photo photo;
    private ImageView photoImage;
    private TextView owner;
    private TextView title;
    private TextView dateTaken;
    private FlickrClient flickrClient;
    public static final String LOG_TAG = PhotoDetailActivity.class.getSimpleName();

    /**
     * OnCreate Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        photoImage = (ImageView) findViewById(R.id.detail_page_photo);
        title = (TextView) findViewById(R.id.title);
        owner = (TextView) findViewById(R.id.owner);
        dateTaken = (TextView) findViewById(R.id.date_taken);

        photo = (Photo) getIntent().getSerializableExtra(PhotoFeedActivity.PHOTO_DETAIL_KEY);
        loadPhotoDetails(photo);

    }

    /**
     * OnResume Activity
     */
    @Override
    protected void onResume() {
        super.onResume();

        AnalyticsHelper.logPageViews();
        Log.i(LOG_TAG, "Logging page views");
    }

    /**
     * Load Photo Details into View. Load photo from Picasso into View.
     *
     * @param photo    Photo object
     */
    public void loadPhotoDetails(final Photo photo) {

        flickrClient = new FlickrClient();

        HashMap<String, String> fetchPhotoStatEventParams = new HashMap<>(2);
        fetchPhotoStatEventParams.put(AnalyticsHelper.PARAM_FETCH_PHOTO_ID, String.valueOf(photo.getPhotoId()));
        fetchPhotoStatEventParams.put(AnalyticsHelper.PARAM_FETCH_PHOTO_SECRET, String.valueOf(photo.getSecret()));
        AnalyticsHelper.logEvent(AnalyticsHelper.EVENT_FETCH_PHOTO_STATS, fetchPhotoStatEventParams, true);

        Log.i(LOG_TAG, "Logging event: " + AnalyticsHelper.EVENT_FETCH_PHOTO_STATS);

        flickrClient.getPhotoDetailFeed(photo.getPhotoId(), photo.getSecret(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, JSONObject body) {

                AnalyticsHelper.endTimedEvent(AnalyticsHelper.EVENT_FETCH_PHOTO_STATS);

                if (body != null) {
                    try {
                        photo.setOwner(body.getJSONObject("photo").getJSONObject("owner").getString("realname"));
                        photo.setDateTaken(body.getJSONObject("photo").getJSONObject("dates").getString("taken"));

                        long datePosted = Long.parseLong(body.getJSONObject("photo").getJSONObject("dates").getString("posted"));
                        Date date = new Date(datePosted * 1000L);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT-8"));

                    } catch (JSONException e) {
                        AnalyticsHelper.logError(LOG_TAG, "Deserialize photo detail JSONObject error.", e);
                    }

                } else {
                    AnalyticsHelper.logError(LOG_TAG, "Response body is null", null);
                }

                if(photo.getTitle() != null && !photo.getTitle().trim().equals("")){
                    title.setText(photo.getTitle());
                }
                if (photo.getOwner() != null && !photo.getOwner().trim().equals("")) {
                    owner.setText(Html.fromHtml("<b>By </b> " + photo.getOwner()));
                }

                if (photo.getDateTaken() != null && !photo.getDateTaken().trim().equals("")) {
                    dateTaken.setText(Html.fromHtml("<b>Taken On </b> " + photo.getDateTaken().split(" ")[0]));
                }


                Picasso.with(PhotoDetailActivity.this)
                        .load(photo.getPhotoUrl())
                        .error(R.drawable.noimage)
                        .placeholder(R.drawable.noimage)
                        .into(photoImage);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AnalyticsHelper.logError(LOG_TAG, "Failure in fetching photo details", null);
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
