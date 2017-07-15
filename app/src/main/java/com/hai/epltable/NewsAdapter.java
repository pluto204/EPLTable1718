package com.hai.epltable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pluto on 6/13/2017.
 */

public class NewsAdapter extends ArrayAdapter<NewsData>{

    private Context context;
    private ArrayList<NewsData> dataSet;

    private static class ViewHolder {
        TextView headlineTV;
        TextView timeTV;
        TextView dateTV;
        TextView summaryTV;
        ImageView img;
    }

    public NewsAdapter(ArrayList<NewsData> data, Context context){
        super(context, R.layout.news_item, data);
        this.context = context;
        this.dataSet = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder viewHolder = new ViewHolder();
        NewsData newsData = getItem(position);

        View rowView = inflater.inflate(R.layout.news_item, parent, false);
        viewHolder.headlineTV = (TextView) rowView.findViewById(R.id.headlineitem);
        viewHolder.timeTV = (TextView) rowView.findViewById(R.id.timeTV);
        viewHolder.dateTV = (TextView) rowView.findViewById(R.id.dateTV);
        viewHolder.summaryTV = (TextView) rowView.findViewById(R.id.summaryTV);
        viewHolder.img = (ImageView)rowView.findViewById(R.id.itemicon);

        viewHolder.headlineTV.setText(newsData.getHeadline());
        viewHolder.timeTV.setText(newsData.getTime());
        viewHolder.dateTV.setText(newsData.getDate());
        viewHolder.summaryTV.setText(newsData.getSummary());
        final String imgURL = newsData.getImgURL();

        new Thread(new Runnable() {
            public void run() {

                final Bitmap bitmap = loadImageFromNetwork(imgURL);
                viewHolder.img.post(new Runnable() {
                    public void run() {
                        viewHolder.img.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();

        return rowView;
    }

    private Bitmap loadImageFromNetwork(String url) {
        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
