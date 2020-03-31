package com.example.project_leaderboard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import com.example.project_leaderboard.R;
import androidx.annotation.Nullable;
import java.util.List;

public class ListAdapter <T> extends ArrayAdapter <T> {

    /**
     * This class is used to adapt a List
     * @author Samuel Michellod
     */
    private int mResource;
    private List<T> mData;


    public ListAdapter (@NonNull Context context, @LayoutRes int resource, @NonNull List<T> data){
        super(context, resource,data);
        mResource=resource;
        mData=data;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return getCustomView(position, convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getCustomView(position, convertView,parent);
    }

    public T getItem (int postition){
        return mData.get(postition);
    }

    @SuppressLint("ResourceType")
    private View getCustomView(int position, View convertView, ViewGroup parent) {
        ListAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(mResource, parent, false);

            viewHolder = new ListAdapter.ViewHolder();
            viewHolder.itemView = convertView.findViewById(R.layout.fragment_match);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListAdapter.ViewHolder) convertView.getTag();
        }
        T item = getItem(position);
        if (item != null) {
            viewHolder.itemView.setText(item.toString());
        }
        return convertView;
    }
    private static class ViewHolder {
        TextView itemView;
    }

    public void updateData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

}
