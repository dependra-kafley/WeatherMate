package com.example.weathermate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int Splash_screen = 3200;
    boolean connected = false;

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        txtTitle = findViewById(R.id.tv_title);
        image = findViewById(R.id.logo);
        txtTitle.setAnimation(topAnim);
        image.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,WeatherActivity.class));
                overridePendingTransition(R.anim.slide_up_in,R.anim.slide_up_out);
            }
        }, Splash_screen);

    }
}