package com.example.athini.picturesapi.Model;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by athini on 2018/04/23.
 */

public class Connection {
    private HttpURLConnection connection = null;
    public JSONArray doPost(String town, String key) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("https://api.cognitive.microsoft.com/bing/v7.0/images/search" + "?q=" +  URLEncoder.encode(town, "UTF-8"));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", key);
            InputStream stream = connection.getInputStream();
            JSONObject jsonObject = new JSONObject(new Scanner(stream).useDelimiter("\\A").next());
            return jsonObject.getJSONArray("value");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
