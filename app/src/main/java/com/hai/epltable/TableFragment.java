package com.hai.epltable;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    String jsonStr;
    ProgressBar progressBar;
    ArrayList<TableData> tableList;
    String url;
    TextView errorText;
    Button button1, button2;

    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_table, container, false);

        url = "http://api.football-data.org/v1/soccerseasons/445/leagueTable";
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        tableList = new ArrayList<>();
        lv = (ListView) v.findViewById(R.id.list);

        button1 = (Button)v.findViewById(R.id.table1Btn);
        button2 = (Button)v.findViewById(R.id.table2Btn);
        button1.setEnabled(false);
        button2.setEnabled(false);
        errorText = (TextView)v.findViewById(R.id.errorText);
        errorText.setVisibility(View.INVISIBLE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.adCount++;
                if(MainActivity.conn){
                    url = "http://api.football-data.org/v1/soccerseasons/426/leagueTable?matchday=38";
                    new GetTable1().execute();
                }else {
                    Toast.makeText(getContext(), "Close app and turn on network to load new data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.adCount++;
                if(MainActivity.conn){
                    url = "http://api.football-data.org/v1/soccerseasons/445/leagueTable";
                    new GetTable1().execute();
                }else {
                    Toast.makeText(getContext(), "Close app and turn on network to load new data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new GetTable().execute();
        return v;
    }

    private class GetTable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tableList.clear();
            progressBar.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.INVISIBLE);
            button1.setEnabled(false);
            button2.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            if (!MainActivity.conn || MainActivity.isLoadedMatch) {
                jsonStr = DataManager.getData(getContext(), "fixture.json");
            } else {
                HttpHandler sh = new HttpHandler();
                jsonStr = sh.makeServiceCall(url);
            }

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray teams = jsonObj.getJSONArray("standing");

                    // looping through All Contacts
                    for (int i = 0; i < teams.length(); i++) {
                        JSONObject c = teams.getJSONObject(i);
                        String position = c.getString("position");
                        String teamName = c.getString("teamName");
                        String playedGames = c.getString("playedGames");
                        String points = c.getString("points");
                        String wins = c.getString("wins");
                        String draws = c.getString("draws");
                        String losses = c.getString("losses");

                        TableData tableData = new TableData(position, teamName, playedGames, wins, draws, losses, points);


                        // adding contact to contact list
                        tableList.add(tableData);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getContext(), "doinbackground error", Toast.LENGTH_LONG).show();
                        errorText.setVisibility(View.VISIBLE);
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(MainActivity.conn){
                if (jsonStr != null) {
                    DataManager.saveData(getContext(), jsonStr, "fixture.json");
//                    Toast.makeText(getContext(), "json saved", Toast.LENGTH_SHORT).show();
                    MainActivity.isLoadedTable = true;
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getContext(), "onpostexcute error", Toast.LENGTH_LONG).show();
                            errorText.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }else {
                MainActivity.isLoadedTable = true;
            }
            TableAdapter adapter = new TableAdapter(tableList, getContext());
            lv.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
            button1.setEnabled(true);
            button2.setEnabled(true);
        }
    }

    private class GetTable1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tableList.clear();
            progressBar.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.INVISIBLE);
            button1.setEnabled(false);
            button2.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray teams = jsonObj.getJSONArray("standing");

                    // looping through All Contacts
                    for (int i = 0; i < teams.length(); i++) {
                        JSONObject c = teams.getJSONObject(i);
                        String position = c.getString("position");
                        String teamName = c.getString("teamName");
                        String playedGames = c.getString("playedGames");
                        String points = c.getString("points");
                        String wins = c.getString("wins");
                        String draws = c.getString("draws");
                        String losses = c.getString("losses");

                        TableData tableData = new TableData(position, teamName, playedGames, wins, draws, losses, points);


                        // adding contact to contact list
                        tableList.add(tableData);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getContext(), "doinbackground error", Toast.LENGTH_LONG).show();
                        errorText.setVisibility(View.VISIBLE);
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(MainActivity.conn){
                if (jsonStr != null) {
                    DataManager.saveData(getContext(), jsonStr, "fixture.json");
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            errorText.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
            TableAdapter adapter = new TableAdapter(tableList, getContext());
            lv.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
            button1.setEnabled(true);
            button2.setEnabled(true);
        }
    }

}
