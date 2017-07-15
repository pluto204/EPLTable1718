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

public class MatchAdapter extends ArrayAdapter<MatchData> {
    private Context context;
    private ArrayList<MatchData> dataSet;

    public MatchAdapter(ArrayList<MatchData> data, Context context){
        super(context, R.layout.match_item, data);
        this.context = context;
        this.dataSet = data;
    }

    private static class ViewHolder {
        TextView dateTV;
        TextView timeTV;
        TextView homeNameTV;
        TextView awayNameTV;
        ImageView homeLogo;
        ImageView awayLogo;
        TextView homeScoreTV;
        TextView awayScoreTV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
        MatchData matchData = getItem(position);

        View rowView = inflater.inflate(R.layout.match_item, parent, false);
        viewHolder.dateTV = (TextView) rowView.findViewById(R.id.dateTV);
        viewHolder.timeTV = (TextView) rowView.findViewById(R.id.timeTV);
        viewHolder.homeNameTV = (TextView) rowView.findViewById(R.id.homename);
        viewHolder.awayNameTV = (TextView) rowView.findViewById(R.id.awayname);
        viewHolder.homeScoreTV = (TextView) rowView.findViewById(R.id.homeScore);
        viewHolder.awayScoreTV = (TextView) rowView.findViewById(R.id.awayScore);
        viewHolder.homeLogo = (ImageView) rowView.findViewById(R.id.homelogo);
        viewHolder.awayLogo = (ImageView) rowView.findViewById(R.id.awaylogo);

        viewHolder.dateTV.setText(matchData.getDate());
        viewHolder.timeTV.setText(matchData.getTime());
        viewHolder.homeNameTV.setText(matchData.getHomeNameTeam());
        viewHolder.awayNameTV.setText(matchData.getAwayTeamName());
        viewHolder.homeScoreTV.setText(matchData.getGoalsHomeTeam());
        viewHolder.awayScoreTV.setText(matchData.getGoalsAwayTeam());
        viewHolder.homeLogo.setImageResource(getImage(matchData.getHomeNameTeam()));
        viewHolder.awayLogo.setImageResource(getImage(matchData.getAwayTeamName()));

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
