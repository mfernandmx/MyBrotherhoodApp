package com.example.marcos.mybrotherhoodapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marcos.mybrotherhoodapp.items.LatestItem;
import com.example.marcos.mybrotherhoodapp.R;

import java.util.List;

/**
 * Class RecyclerViewAdapter
 * Adapter used for showing the links in the latest section
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter
        <RecyclerViewAdapter.ViewHolder>{

    private List<LatestItem> mDataSet;
    private final OnItemClickListener listener;

    public RecyclerViewAdapter(List<LatestItem> myDataset, OnItemClickListener listener) {
        mDataSet = myDataset;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_latest, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDataSet.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView headline;

        public ViewHolder(View v) {
            super(v);
            headline = (TextView) v.findViewById(R.id.headlineView);
        }

        public void bind(final LatestItem latestItem, final OnItemClickListener listener) {

            headline.setText(latestItem.getHeadline());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(latestItem);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(LatestItem item);     //Type of the element to be returned
    }
}
