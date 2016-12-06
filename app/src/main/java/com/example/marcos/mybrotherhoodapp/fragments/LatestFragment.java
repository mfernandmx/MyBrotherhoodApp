package com.example.marcos.mybrotherhoodapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcos.mybrotherhoodapp.items.LatestItem;
import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.adapters.RecyclerViewAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos on 10/11/2016.
 */

public class LatestFragment extends Fragment {

    private List<String> headlines;
    private List<String> links;
    private List<LatestItem> data;
    private RecyclerView rvLatest;
    public RecyclerViewAdapter adapter;

    private static final String TAG = "LatestFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(TAG, "onCreateView");

        View v = inflater.inflate(R.layout.recycler_view, container, false);

        rvLatest = (RecyclerView) v.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvLatest.setLayoutManager(llm);

        initializeData();

        rvLatest.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

    public void initializeData(){

        Log.v(TAG, "initializeData");

        headlines = new ArrayList<>();
        links = new ArrayList<>();
        data = new ArrayList<>();

        URL url = null;
        try {
            url = new URL("https://jesuscondenado.com/feed/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new ProcessXML().execute(url);
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public void initializeAdapter(){

        Log.v(TAG, "initializeAdapter");

        adapter = new RecyclerViewAdapter(data, new RecyclerViewAdapter.OnItemClickListener(){
            @Override public void onItemClick(LatestItem item) {
                Uri uri = Uri.parse(item.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        rvLatest.setAdapter(adapter);
    }

    private class ProcessXML extends AsyncTask<URL, Void, Integer> {

        protected Integer doInBackground(URL... urls) {
            Log.v(TAG,"doInBackground");
            try {
                URL url = urls[0];

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");

        /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
         * and take in consideration only "<title>" tag which is a child of "<item>"
         *
         * In order to achieve this, we will make use of a boolean variable.
         */
                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem)
                                headlines.add(xpp.nextText()); //extract the headline
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem)
                                links.add(xpp.nextText()); //extract the link of article
                        }
                    }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                        insideItem=false;
                    }

                    eventType = xpp.next(); //move to next element
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return headlines.size();

        }

        protected void onPostExecute(Integer numHeadlines) {
            Log.v(TAG, "Headlines: " + numHeadlines);

            for (int i = 0; i < headlines.size(); i++){
                data.add(i, new LatestItem(headlines.get(i), links.get(i)));
            }
            
            initializeAdapter();
        }
    }
}


