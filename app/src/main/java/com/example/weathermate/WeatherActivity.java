package com.example.weathermate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {
    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView tvState,tvDate,tvTemp,tvhumid;
    LinearLayout llBig,llSmall;
    RecyclerView rvView;
    LinearLayoutManager layoutManagerGroup;
    ImageView ivClouds;
    ArrayList<WeatherModel> modelsList;
    CustomItemAdapter customItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        volleyCall();
        rvView=findViewById(R.id.rv_view);
        ivClouds=findViewById(R.id.iv_clouds);
        tvState=findViewById(R.id.tv_state);
        tvDate=findViewById(R.id.tv_date);
        tvTemp=findViewById(R.id.tv_temp);
        tvhumid=findViewById(R.id.tv_humid);
        modelsList=new ArrayList<>();
        ivClouds.startAnimation(AnimationUtils.loadAnimation(this,R.anim.pumping_anim));
        //hide the default actionbar

        View bottomSheet = findViewById(R.id.bottomSheet);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        CustomItemAdapter customItemAdapter;
        layoutManagerGroup = new LinearLayoutManager(WeatherActivity.this, LinearLayoutManager.VERTICAL, false);
        rvView.setLayoutManager(layoutManagerGroup);

        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //  llSmall.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:

                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        // llBig.setVisibility(View.GONE);
                        break;

                    case BottomSheetBehavior.STATE_SETTLING:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    public void volleyCall(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://api.weatherapi.com/v1/forecast.json?key=91450a8099c74a9c883145906212708&q=07112&days=7", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "onResponse: "+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String name=jsonObject.getJSONObject("location").getString("name");
                    tvState.setText(name);
                    String time=jsonObject.getJSONObject("location").getString("localtime");
                    tvDate.setText(time.substring(0,11));
                    String temp_c=jsonObject.getJSONObject("current").getString("temp_c");

                    Log.d("TAG", "Temp: "+temp_c);
                    tvTemp.setText(temp_c+" C");
                    String humid=jsonObject.getJSONObject("current").getJSONObject("condition").getString("text");

                    tvhumid.setText(humid);
                    JSONArray forecast=jsonObject.getJSONObject("forecast").getJSONArray("forecastday");
                    for(int i=0;i<forecast.length();i++){
                        Log.d("TAG", "onResponse: "+forecast);
                        JSONObject days=forecast.getJSONObject(i);
                        JSONArray hours=days.getJSONArray("hour");
                        Log.d("TAG", "onResponse: "+hours);
                        {
                            String date=days.getString("date");
                            String temp=days.getJSONObject("day").getString("maxtemp_c")+days.getJSONObject("day").getString("mintemp_c");
                            String condition=days.getJSONObject("day").getJSONObject("condition").getString("text");
                            String url=days.getJSONObject("day").getJSONObject("condition").getString("icon");
                            WeatherModel weatherModel=new WeatherModel(temp,date,condition,url);
                            modelsList.add(weatherModel);
                        }

                    }

                    customItemAdapter=new CustomItemAdapter(WeatherActivity.this,modelsList);
                    rvView.setAdapter(customItemAdapter);
                    customItemAdapter.notifyDataSetChanged();

                    //WeatherModel weatherModel=new WeatherModel(temp,date,condition);
                   // modelsList.add(weatherModel);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.getMessage());
            }
        });
        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }
}