package com.example.marcos.mybrotherhoodapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.marcos.mybrotherhoodapp.DetailsGridActivity;
import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.adapters.GridViewAdapter;
import com.example.marcos.mybrotherhoodapp.items.ImageItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marcos on 11/11/2016.
 */

public class GalleryFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    private static final String TAG = "GalleryFragment";
    //TODO: Settings
    private static final int maxNumPhotosShown = 10;

    private List<String> titles;
    private List<String> photoLinks;
    private ArrayList<ImageItem> imageItems;

    private boolean nightMode;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery, container, false);

        new LoadImagesFromFlickrTask().execute();

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        gridView = (GridView) getActivity().findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(getActivity(), DetailsGridActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        imageItems = new ArrayList<>();
        for (int i = 0; i < maxNumPhotosShown; i++) {
            Bitmap bitmap = null;

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 4;

            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(photoLinks.get(i)).getContent(), null, options);
                Log.v(TAG,photoLinks.get(i));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageItems.add(new ImageItem(bitmap, titles.get(i)));
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        int backgroundColor;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Set night or day mode
        nightMode = sharedPref.getBoolean(SettingsFragment.KEY_PREF_NIGHTMODE, false);
        if (nightMode){
            backgroundColor = getResources().getColor(R.color.colorPrimaryDark);
        } else{
            backgroundColor = Color.WHITE;
        }
        v.setBackgroundColor(backgroundColor);
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    class LoadImagesFromFlickrTask extends AsyncTask<String, Integer, Integer> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading images from Flickr. Please wait...");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {

            titles = new ArrayList<>();
            photoLinks = new ArrayList<>();

            int numPhotos = 0;

            Log.v(TAG,"doInBackground");
            try {
                URL url = new URL("https://api.flickr.com/services/feeds/photos_public.gne?id=60000239@N02");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                while ((eventType != XmlPullParser.END_DOCUMENT) || numPhotos < maxNumPhotosShown) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("entry")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem)
                                titles.add(xpp.nextText()); //extract the title
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                if (xpp.getAttributeValue(null, "type").equals("image/jpeg")) {
                                    photoLinks.add(xpp.getAttributeValue(null, "href")); //extract the link of the photo
                                    numPhotos++;
                                }
                            }

                        }
                    }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("entry")){
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

            loadData();

            return imageItems.size();
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            progressDialog.dismiss();

            gridAdapter = new GridViewAdapter(getActivity(), R.layout.item_grid, imageItems);
            gridView.setAdapter(gridAdapter);

            if (nightMode){
                gridAdapter.setNightMode(true);
            } else{
                gridAdapter.setNightMode(false);
            }
        }
    }
}
