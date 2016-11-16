package com.example.marcos.mybrotherhoodapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.marcos.mybrotherhoodapp.DetailsGridActivity;
import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.adapters.GridViewAdapter;
import com.example.marcos.mybrotherhoodapp.items.ImageItem;

import java.util.ArrayList;

/**
 * Created by Marcos on 11/11/2016.
 */

public class GalleryFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        gridView = (GridView) getActivity().findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.item_grid, getData());
        gridView.setAdapter(gridAdapter);

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

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image " + i));
        }
        return imageItems;
    }
}
