package com.example.Top_10_Downloader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    Button btnParse;
    ListView listApps;
    String xmlData;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnParse = (Button) findViewById(R.id.btnParse);
        listApps = (ListView) findViewById(R.id.listApps);

        btnParse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseApplication parseApplication = new ParseApplication(xmlData);
                boolean status = parseApplication.process();
                if (status) {
                    ArrayList<Application> allApps = parseApplication.getApplicationList();
                    ArrayAdapter<Application> applicationArrayAdapter = new ArrayAdapter<Application>(MainActivity.this, R.layout.list_item, allApps);

                    listApps.setVisibility(View.VISIBLE);
                    listApps.setAdapter(applicationArrayAdapter);
                } else {
                    Log.d("MainActivity", "Error parsing file");
                }

            }
        });
        new DownloadData().execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        String feedData;

        @Override
        protected String doInBackground(String... params) {
            try {
                feedData = downloadFeed(params[0]);
            } catch (IOException e) {
                return "Unable to download feed from url";
            }
            return "";
        }

        protected void onPostExecute(String result) {
            Log.d("OnPostExecute", feedData);
            xmlData = feedData;
        }

        private String downloadFeed(String url) throws IOException {
            int BUFFER_SIZE = 2000;

            InputStream stream = null;

            String feedContents = "";
            try {
                URL location = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) location.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                int response = connection.getResponseCode();
                Log.d("DownloadFeed", "The response returned is: " + response);
                stream = connection.getInputStream();

                InputStreamReader reader = new InputStreamReader(stream);
                int charRead;
                char[] inputBuffer = new char[BUFFER_SIZE];

                try {
                    while ((charRead = reader.read(inputBuffer)) > 0) {
                        String readString = String.copyValueOf(inputBuffer, 0, charRead);
                        feedContents += readString;
                        inputBuffer = new char[BUFFER_SIZE];
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return feedContents;
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }
    }
}
