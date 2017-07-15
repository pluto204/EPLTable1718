package com.hai.epltable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pluto on 6/16/2017.
 */

public class TableAdapter extends ArrayAdapter<TableData> {
    private Context context;
    private ArrayList<TableData> dataSet;

    public TableAdapter(ArrayList<TableData> data, Context context){
        super(context, R.layout.list_item, data);
        this.context = context;
        this.dataSet = data;
    }

    private static class ViewHolder {
        TextView posTV;
        TextView nameTV;
        TextView playedTV;
        TextView pointTV;
        TextView winTV;
        TextView drawTV;
        TextView loseTV;
        ImageView teamLogo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
        TableData tableData = getItem(position);

        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        viewHolder.posTV = (TextView) rowView.findViewById(R.id.posTV);
        viewHolder.nameTV = (TextView) rowView.findViewById(R.id.teamName);
        viewHolder.playedTV = (TextView) rowView.findViewById(R.id.playedTV);
        viewHolder.winTV = (TextView) rowView.findViewById(R.id.winTV);
        viewHolder.drawTV = (TextView) rowView.findViewById(R.id.drawTV);
        viewHolder.loseTV = (TextView) rowView.findViewById(R.id.loseTV);
        viewHolder.pointTV = (TextView) rowView.findViewById(R.id.pointTV);
        viewHolder.teamLogo = (ImageView) rowView.findViewById(R.id.teamLogo);

        viewHolder.posTV.setText(tableData.getPosition());
        viewHolder.nameTV.setText(tableData.getTeamName());
        viewHolder.playedTV.setText(tableData.getPlayedGames());
        viewHolder.winTV.setText(tableData.getWins());
        viewHolder.drawTV.setText(tableData.getDraws());
        viewHolder.loseTV.setText(tableData.getLosses());
        viewHolder.pointTV.setText(tableData.getPoints());
        viewHolder.teamLogo.setImageResource(getImage(tableData.getTeamName()));

        return rowView;
    }


    public int getImage(String team){
        int imgId;
        switch (team){
            case "Arsenal FC":
                imgId = R.drawable.ic_arsenal;
                break;
            case "Leicester City FC":
                imgId = R.drawable.ic_leicester;
                break;
            case "Watford FC":
                imgId = R.drawable.ic_watford;
                break;
            case "Liverpool FC":
                imgId = R.drawable.ic_liverpool;
                break;
            case "Southampton FC":
                imgId = R.drawable.ic_southampton;
                break;
            case "Swansea City FC":
                imgId = R.drawable.ic_swansea;
                break;
            case "Newcastle United FC":
                imgId = R.drawable.ic_newcastle;
                break;
            case "Tottenham Hotspur FC":
                imgId = R.drawable.ic_tottenham;
                break;
            case "Manchester United FC":
                imgId = R.drawable.ic_mu;
                break;
            case "West Ham United FC":
                imgId = R.drawable.ic_westham;
                break;
            case "Everton FC":
                imgId = R.drawable.ic_everton;
                break;
            case "Stoke City FC":
                imgId = R.drawable.ic_stockcity;
                break;
            case "Crystal Palace FC":
                imgId = R.drawable.ic_rystal_palace;
                break;
            case "Huddersfield Town":
                imgId = R.drawable.ic_huddersfield_town;
                break;
            case "Chelsea FC":
                imgId = R.drawable.ic_chelsea;
                break;
            case "Burnley FC":
                imgId = R.drawable.ic_burnley;
                break;
            case "Brighton & Hove Albion":
                imgId = R.drawable.ic_brighton;
                break;
            case "Manchester City FC":
                imgId = R.drawable.ic_mancity;
                break;
            case "West Bromwich Albion FC":
                imgId = R.drawable.ic_westbrom;
                break;
            case "AFC Bournemouth":
                imgId = R.drawable.ic_bournemouth;
                break;
            case "Sunderland AFC":
                imgId = R.drawable.ic_sunderland;
                break;
            case "Middlesbrough FC":
                imgId = R.drawable.ic_middlebrough;
                break;
            case "Hull City FC":
                imgId = R.drawable.ic_hullcity;
                break;
            default:
                imgId = R.drawable.ic_mu;
                break;
        }
        return imgId;
    }
}
