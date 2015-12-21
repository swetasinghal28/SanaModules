package org.sana.android.activity;

/**
 * Created by arpitjaiswal on 11/21/15.
 */
import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sana.R;

import java.io.IOException;
import java.util.ArrayList;

public class EncounterActivity extends Activity {

    ListView listView;
    CityAdapter adapter;
    ArrayList<City> cityArrayList;
    DBHandler handler;
    private static final String TAG_MRS = "results";
    private static final String TAG_VER = "uuid";
    private static final String TAG_display = "display";
    private static final String TAG_encouter = "encounter";
    private static final String TAG_encouterID = "uuid";

    private static String url = "http://52.10.82.9:8080/openmrs197/ws/rest/v1/obs?patient=265e07c2-b263-49eb-90cd-b3034bc79005&v=default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encounterlayout);
        listView = (ListView) findViewById(R.id.listview);
        handler = new DBHandler(this);
        NetworkUtils utils = new NetworkUtils(EncounterActivity.this);

            new DataFetcherTask().execute();

    }

    class DataFetcherTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

// Http Request Code end
// Json Parsing Code Start
            try {
                cityArrayList = new ArrayList<City>();
                JSONParser jParser = new JSONParser();
                // Getting JSON from URL
                JSONObject json = jParser.getJSONFromUrl(url);

                JSONArray jsonArray = json.getJSONArray(TAG_MRS);
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObjectCity = jsonArray.getJSONObject(i);

                    String cityName = jsonObjectCity.getString(TAG_display);
                    String cityDescription = jsonObjectCity.getString(TAG_VER);

                    JSONObject encounter = jsonObjectCity.getJSONObject(TAG_encouter);
                    String  name1= encounter.getString(TAG_encouterID);
                    String cityState = name1.substring(0, 5);
                    int flag = 0;

                  //  String cityState = jsonObjectCity.getString("state");


                    City city = new City();
                    city.setId(flag);
                    city.setName(cityName);
                    city.setState(cityState);
                    city.setDescription(cityDescription);
                    handler.addCity(city);// Inserting into DB
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//Json Parsing code end
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<City> cityList = handler.getAllCity();
            adapter = new CityAdapter(EncounterActivity.this,cityList);
            listView.setAdapter(adapter);
        }
    }
}