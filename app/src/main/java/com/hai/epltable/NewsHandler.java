package com.hai.epltable;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by pluto on 6/13/2017.
 */

public class NewsHandler extends DefaultHandler {
    Boolean currentElement = false;
    String currentValue = "";
    NewsData news = null;
    private ArrayList<NewsData> NewsList = new ArrayList<NewsData>();

    public ArrayList<NewsData> getNewsList() {
        return NewsList;
    }

    // Called when tag starts
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;
        currentValue = "";
        if (localName.equals("item")) {
            news = new NewsData();
        }

    }

    // Called when tag closing
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;

        /** set value */
        if (localName.equalsIgnoreCase("Date")){
            news.setDate(currentValue);
        } else if (localName.equalsIgnoreCase("Time"))
            news.setTime(currentValue);
        else if (localName.equalsIgnoreCase("Headline"))
            news.setHeadline(currentValue);
        else if (localName.equalsIgnoreCase("url"))
            news.setUrl(currentValue);
        else if (localName.equalsIgnoreCase("body"))
            news.setBody(currentValue);
        else if (localName.equalsIgnoreCase("SummaryText"))
            news.setSummary(currentValue);
        else if (localName.equalsIgnoreCase("ImageURL"))
            news.setImgURL(currentValue);
        else if (localName.equalsIgnoreCase("item"))
            NewsList.add(news);
    }

    // Called to get tag characters
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);

        }

    }
}
