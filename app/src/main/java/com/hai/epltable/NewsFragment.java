package com.hai.epltable;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    Button parseBtn;
    String URL = "http://www.goal.com/en-ke/feeds/news?id=4518&fmt=xml&ICID=SP";
    ListView list;
    NewsAdapter adapter;
    ProgressBar progressBar;
    String response = null;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        list = (ListView)v.findViewById(R.id.listView);
        new parseMagic().execute();
        return v;
    }

    class parseMagic extends AsyncTask<String, ArrayList<NewsData>, ArrayList<NewsData>> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected ArrayList<NewsData> doInBackground(String... arg0) {

            response = new XMLRequest().XMLUrlHttpRequest(URL);

            if (!MainActivity.conn || MainActivity.isLoadedNews) {
                response = DataManager.getData(getContext(), "news.xml");
            } else {
                response = new XMLRequest().XMLUrlHttpRequest(URL);
            }

            ArrayList<NewsData> NewsList = new ArrayList<>();
            try {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                NewsHandler XMLHandler = new NewsHandler();
                xr.setContentHandler(XMLHandler);
                InputSource inStream = new InputSource();

                inStream.setCharacterStream(new StringReader(response));

                xr.parse(inStream);
                Log.w("response",response);
                int i=0;
                NewsList = XMLHandler.getNewsList();
            }
            catch (Exception e) {
                Log.w("AndroidParseXMLActivity",e );
            }

            return NewsList;

        }
        protected void onPostExecute(final ArrayList<NewsData> result){
            adapter = new NewsAdapter(result, getContext());
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(MainActivity.conn){
                        NewsData dataModel= result.get(position);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.getUrl()));
                        startActivity(browserIntent);
                    }else {
                        Toast.makeText(getContext(), "Close app and turn on network to open link", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            DataManager.saveData(getContext(), response, "news.xml");
            MainActivity.isLoadedNews = true;
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

}
