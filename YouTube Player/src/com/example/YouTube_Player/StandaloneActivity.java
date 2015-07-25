package com.example.YouTube_Player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

/**
 * User: rowan.massey
 * Date: 25/07/2015
 */
public class StandaloneActivity extends Activity implements View.OnClickListener {

    //Stored in local.properties. NOTE: NOT checked in.
    public static final String GOOGLE_API_KEY = "";
    public static final String YOUTUBE_VIDEO_ID = "";

    public static final String YOUTUBE_PLAYLIST_ID = "";

    private Button btnStart;
    private Button btnPlaylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standalone);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnPlaylist = (Button) findViewById(R.id.btnPlayList);

        btnStart.setOnClickListener(this);
        btnPlaylist.setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent = null;
        if (view == btnStart) {
            //Play single video
            intent = YouTubeStandalonePlayer.createVideoIntent(this, GOOGLE_API_KEY, YOUTUBE_VIDEO_ID);
        } else if (view == btnPlaylist) {
            //Play the entire playlist.
            intent = YouTubeStandalonePlayer.createPlaylistIntent(this, GOOGLE_API_KEY, YOUTUBE_PLAYLIST_ID);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
