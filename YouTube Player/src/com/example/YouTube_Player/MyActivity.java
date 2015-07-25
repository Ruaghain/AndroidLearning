package com.example.YouTube_Player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {

    private Button btnPlay;
    private Button btnStandalone;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, YouTubeActivity.class);
                //Loads the second screen
                startActivity(intent);
            }
        });

        btnStandalone = (Button) findViewById(R.id.btnSubMenu);
        btnStandalone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, StandaloneActivity.class);
                //Loads the second screen
                startActivity(intent);
            }
        });
    }
}
