package com.example.Top_10_Downloader;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * User: rowan.massey
 * Date: 19/07/2015
 * Time: 11:25
 */
public class ParseApplication {

    private String data;
    private ArrayList<Application> applicationList;

    public ParseApplication(String data) {
        this.data = data;
        applicationList = new ArrayList<Application>();
    }

    public ArrayList<Application> getApplicationList() {
        return applicationList;
    }

    public boolean process() {
        boolean operationStatus = true;
        Application record = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(this.data));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                if (eventType == XmlPullParser.START_TAG) {
                    if (tagName.equalsIgnoreCase("entry")) {
                        inEntry = true;
                        record = new Application();
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    textValue = parser.getText();
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (inEntry) {
                        if (tagName.equalsIgnoreCase("entry")) {
                            applicationList.add(record);
                            inEntry = false;
                        }
                        if (tagName.equalsIgnoreCase("name")) {
                            record.setName(textValue);
                        } else if (tagName.equalsIgnoreCase("artist")) {
                            record.setArtist(textValue);
                        } else if (tagName.equalsIgnoreCase("releaseDate")) {
                            record.setReleaseDate(textValue);
                        }
                    }
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            operationStatus = false;
        }

        for (Application application : applicationList) {
            Log.d("LOG", "**********");
            Log.d("LOG", application.getName());
            Log.d("LOG", application.getArtist());
            Log.d("LOG", application.getReleaseDate());
        }

        return operationStatus;

    }
}
