package com.example.athini.picturesapi.Model;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.athini.picturesapi.Controller.Feed;
import com.example.athini.picturesapi.Controller.GlobalPositioningSystem;
import com.example.athini.picturesapi.Controller.PictureAdapter;
import com.example.athini.picturesapi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    double latitude,longitude;
    Geocoder geocoder;
    private RecyclerView recyclerView;
    private PictureAdapter adapter;
    private List<Feed> feedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        geocoder = new Geocoder(this, Locale.getDefault());
        recyclerView = findViewById(R.id.recycler_view);
        feedList = new ArrayList<>();
        Connection connection = new Connection();
        GlobalPositioningSystem globalPositioningSystem = new GlobalPositioningSystem(MainActivity.this);
        if (globalPositioningSystem.canGetlocation()) {
            latitude = globalPositioningSystem.getLatitude();
            longitude = globalPositioningSystem.getLongitude();
        } else
            globalPositioningSystem.showSettingAlert();
        getSupportActionBar().setTitle(getInformation());
        String key = getResources().getString(R.string.key);
        JSONArray value = connection.doPost(getInformation(),key);
        adapter = new PictureAdapter(this,feedList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getData(value);
        recyclerView.setAdapter(adapter);
    }

    private void getData(JSONArray value) {
        Feed feed = null;
        for (int i = 0; i < value.length(); i++){
            try {
                JSONObject jsonObject = new JSONObject(value.get(i).toString());
                feed = new Feed((String) jsonObject.get("thumbnailUrl"),(String) jsonObject.get("name"),(String) jsonObject.get("contentUrl"), (String) jsonObject.get("datePublished"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            feedList.add(feed);
        }
        adapter.notifyDataSetChanged();

    }
    private String getInformation() {
        String town = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address values = addresses.get(0);
                town = values.getLocality() + " " + values.getAdminArea();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return town;
    }
}
