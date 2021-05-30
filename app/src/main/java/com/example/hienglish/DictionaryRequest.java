package com.example.hienglish;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//add dependencies to your class
public class DictionaryRequest extends AsyncTask<String, Integer, String> {

    String separator = "\n______________________________________________\n";
    Context mContext;
    TextView mDefTextView;

    public DictionaryRequest(Context context,TextView txt){
        this.mContext = context;
        this.mDefTextView = txt;
    }

    @Override
    protected String doInBackground(String... params) {

        //TODO: replace with your own app id and app key
        final String app_id = "d6cc18f8";
        final String app_key = "f2b344ed65aa216f5206211911bdc161";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String def;
        try {
            JSONObject js = new JSONObject(result);
            JSONArray results = js.getJSONArray("results");

            JSONObject fEnterie = results.getJSONObject(0);
            JSONArray laArray = fEnterie.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject jsonObject = e.getJSONObject(0);
            JSONArray sensesArray = jsonObject.getJSONArray("senses");

           // JSONObject jsonObject2 = e.getJSONObject(1);
         //   JSONArray sensesArray2 = jsonObject2.getJSONArray("senses");

            JSONObject de = sensesArray.getJSONObject(0);
            JSONArray d = de.getJSONArray("definitions");

            JSONObject de2 = sensesArray.getJSONObject(1);
            JSONArray d2 = de2.getJSONArray("definitions");

            JSONObject de3 = sensesArray.getJSONObject(2);
            JSONArray d3 = de3.getJSONArray("definitions");
          //  JSONObject o = d.getJSONObject(0);

//            def = o.getString("text");
            def =d.getString(0)+separator+d2.getString(0)+separator+d3.getString(0);
            mDefTextView.setText(def);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.v("Result of dictionary","onPostRequest"+result);
    }
}