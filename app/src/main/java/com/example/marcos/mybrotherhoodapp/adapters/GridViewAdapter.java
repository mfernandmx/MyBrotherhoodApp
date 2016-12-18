package com.example.marcos.mybrotherhoodapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marcos.mybrotherhoodapp.R;
import com.example.marcos.mybrotherhoodapp.items.ImageItem;

import java.util.ArrayList;

/**
 * Class GridViewAdapter
 * Adapter used for showing the images in the gallery
 */
public class GridViewAdapter extends ArrayAdapter<ImageItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<ImageItem> mDataList = new ArrayList<>();

    private boolean nightMode = false;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> dataList) {
        super(context, layoutResourceId, dataList);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @Nullable ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.gridText);
            holder.image = (ImageView) row.findViewById(R.id.gridImage);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = mDataList.get(position);

        holder.image.setImageBitmap(item.getImage());
        holder.imageTitle.setText(item.getTitle());

        // Change text color if the night mode is active
        int textColor;
        if (!nightMode){
            textColor = Color.BLACK;
        } else{
            textColor = Color.WHITE;
        }

        holder.imageTitle.setTextColor(textColor);

        return row;
    }

    private static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    public void setNightMode(boolean nightMode){
        this.nightMode = nightMode;
    }
}