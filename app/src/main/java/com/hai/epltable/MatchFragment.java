package com.hai.epltable;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchFragment extends Fragment {

    Spinner spinner;
    ProgressBar progressBar;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    ArrayList<MatchData> matchList;
    String dateSelect = "All Time";
    String jsonStr;

    public MatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        matchList = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.list);
        spinner = (Spinner)view.findViewById(R.id.spinner);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.adCount++;
                dateSelect = parent.getItemAtPosition(position).toString();
                new GetTable().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private class GetTable extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
            matchList.clear();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!MainActivity.conn || MainActivity.isLoadedMatch) {
                jsonStr = DataManager.getData(getContext(), "match.json");
            } else {
                HttpHandler sh = new HttpHandler();
                String url = "http://api.football-data.org/v1/competitions/445/fixtures";
                jsonStr = sh.makeServiceCall(url);
            }

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray teams = jsonObj.getJSONArray("fixtures");

                    // looping through All Contacts
                    for (int i = 0; i < teams.length(); i++) {
                        JSONObject c = teams.getJSONObject(i);
                        String datetime = c.getString("date");
                        String date = datetime.substring(0, 10);
                        if(date.substring(0, 7).equals(dateSelect) || dateSelect.equals("All Time")){
                            String time = datetime.substring(11, 16);
                            String homeNameTeam = c.getString("homeTeamName");
                            String awayTeamName = c.getString("awayTeamName");
                            JSONObject result = c.getJSONObject("result");
                            String goalsHomeTeam = result.getString("goalsHomeTeam");
                            String goalsAwayTeam = result.getString("goalsAwayTeam");

                            if(goalsAwayTeam.toString().equals("null"))goalsAwayTeam = "-";
                            if(goalsHomeTeam.toString().equals("null"))goalsHomeTeam = "-";
                            MatchData match = new MatchData(date, time, homeNameTeam, awayTeamName, goalsHomeTeam, goalsAwayTeam);
                            matchList.add(match);
                        }
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
                        Toast.makeText(getContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(MainActivity.conn) {
                if (jsonStr != null) {
                    DataManager.saveData(getContext(), jsonStr, "match.json");
//                    Toast.makeText(getContext(), "data saved", Toast.LENGTH_SHORT).show();
                    MainActivity.isLoadedMatch = true;
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else {
                MainActivity.isLoadedMatch = true;
            }
            MatchAdapter adapter = new MatchAdapter(matchList, getContext());
            lv.setAdapter(adapter);
            spinner.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
