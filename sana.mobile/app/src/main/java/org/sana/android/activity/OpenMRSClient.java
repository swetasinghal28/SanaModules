package org.sana.android.activity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.sana.R;

import org.sana.android.activity.JSONParser;

public class OpenMRSClient extends Activity {
    TextView uid;
    TextView name1;
    TextView email1;
    Button Btngetdata;

    //URL to get JSON Array
    private static String url = "http://52.10.82.9:8080/openmrs197/ws/rest/v1/concept";

    //JSON Node Names
    private static final String TAG_USER = "results";
    private static final String TAG_ID = "uuid";
    private static final String TAG_NAME = "display";
    //private static final String TAG_EMAIL = "email";

    JSONArray user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.openmrsget);
       // Btngetdata = (Button)findViewById(R.id.getdata);
        new JSONParse().execute();

    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            uid = (TextView)findViewById(R.id.uid);
            name1 = (TextView)findViewById(R.id.name);
           // email1 = (TextView)findViewById(R.id.email);
            pDialog = new ProgressDialog(OpenMRSClient.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                user = json.getJSONArray(TAG_USER);
                JSONObject c = user.getJSONObject(0);

                // Storing  JSON item in a Variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
               // String email = c.getString(TAG_EMAIL);



                //Set JSON Data in TextView
                uid.setText(id);
                name1.setText(name);
             //   email1.setText(email);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}