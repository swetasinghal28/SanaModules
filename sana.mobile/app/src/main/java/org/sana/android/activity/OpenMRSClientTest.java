package org.sana.android.activity;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.sana.R;



public class OpenMRSClientTest extends Activity {

    ListView list;
    TextView ver;
    TextView name;
    TextView api;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();



    //URL to get JSON Array

    private static String url = "http://52.10.82.9:8080/openmrs197/ws/rest/v1/obs?patient=265e07c2-b263-49eb-90cd-b3034bc79005&v=default";



    //JSON Node Names

    private static final String TAG_MRS = "results";
    private static final String TAG_VER = "uuid";
    private static final String TAG_display = "display";
    private static final String TAG_encouter = "encounter";
    private static final String TAG_encouterID = "uuid";

    JSONArray result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listtest_layout);
        oslist = new ArrayList<HashMap<String, String>>();
        new JSONParse().execute();

    }



    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // ver = (TextView)findViewById(R.id.id_mrs);
            name = (TextView)findViewById(R.id.name);
            pDialog = new ProgressDialog(OpenMRSClientTest.this);
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
                result = json.getJSONArray(TAG_MRS);
                for(int i = 0; i < result.length(); i++){
                    JSONObject c = result.getJSONObject(i);
                    // Storing  JSON item in a Variable

                  //  String ver = c.getString(TAG_VER);
                    String name = c.getString(TAG_display);
                   // String name1 = name.substring(5);

                    JSONObject encounter = c.getJSONObject(TAG_encouter);
                    String encounterID = encounter.getString(TAG_encouterID);





                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_encouterID, encounterID);
                    map.put(TAG_display, name);








                    oslist.add(map);
                    list=(ListView)findViewById(R.id.list);
                    ListAdapter adapter = new SimpleAdapter(OpenMRSClientTest.this, oslist,R.layout.list_vtest,
                            new String[] {TAG_encouterID,TAG_display }, new int[] {R.id.id_mrs,R.id.display});
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {



                        @Override

                        public void onItemClick(AdapterView<?> parent, View view,

                                                int position, long id) {

                            Toast.makeText(OpenMRSClientTest.this, "You Clicked at "+oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (JSONException e) {

                e.printStackTrace();

            }
        }
    }
}