package com.abhijit.hubbletesting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {


    private Button liveFeed;
    private Button Images;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Images= findViewById(R.id.hubbleImages);
        liveFeed=findViewById(R.id.live_feed);

        Images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=  new Intent(MainScreen.this,MainActivity.class);
                startActivity(i);

            }
        });
        liveFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainScreen.this,HubbleCurrentFeed.class);
                startActivity(i);

            }
        });
    }


}
