package br.android.androidhttpclientjson;

import android.app.Activity;
import android.app.ListActivity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import br.android.androidhttpclientjson.pegaToken;
import br.android.androidhttpclientjson.requisicoes;


public class Main2Activity extends ListActivity {

    ListActivity teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teste = this;
        new HttpGetTask().execute();

    }
    private class HttpGetTask extends AsyncTask<Void, Void, List<String>> {

        String result;
        @Override
        protected List<String> doInBackground(Void... params) {  //    requests.clear();            //     param.clear();
            requisicoes reqNova = new requisicoes();
            JSONResponseHandler2 json = new JSONResponseHandler2();
            result = reqNova.obtemProdutos();
            try { return json.handleResponse(result);}
            catch (IOException e1) {e1.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {

            setListAdapter(new ArrayAdapter<String>(
                    Main2Activity.this,
                    R.layout.activity_main2, result));
        }
    }

    private class JSONResponseHandler2 {

        private static final String LONGITUDE_TAG = "sku";
        private static final String LATITUDE_TAG = "name";
        private static final String MAGNITUDE_TAG = "price";
        private static final String EARTHQUAKE_TAG = "items";

        public List<String> handleResponse(String JSONResponse)
                throws ClientProtocolException, IOException {
            List<String> result = new ArrayList<String>();
            try {


                //JSONObject responseObject = (JSONObject) new JSONTokener(
                //        JSONResponse).nextValue();
                JSONObject responseObject = new JSONObject(JSONResponse);

                // Extract value of "earthquakes" key -- a List
                JSONArray earthquakes = responseObject
                        .getJSONArray(EARTHQUAKE_TAG);

                // Iterate over earthquakes list
                for (int idx = 0; idx < earthquakes.length(); idx++) {

                    // Get single earthquake data - a Map
                    JSONObject earthquake = (JSONObject) earthquakes.get(idx);

                    // Summarize earthquake data as a string and add it to
                    // result
                    result.add(MAGNITUDE_TAG + ":"
                            + earthquake.get(MAGNITUDE_TAG) + ","
                            + LATITUDE_TAG + ":"
                            + earthquake.getString(LATITUDE_TAG) + ","
                            + LONGITUDE_TAG + ":"
                            + earthquake.get(LONGITUDE_TAG));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

  /* private class JSONCategory {

        private static final String SKU = "sku";
        private static final String PARENT = "parent_id";
        private static final String NAME = "name";
        private static final String ACTIVE = "active";
        private static final String POSITION = "position";
        private static final String LEVEL = "level";
        private static final String PRODUCTCOUNT = "product_count";
        private static final String CHILDDATA = "children_data";


       public List<String> handleResponse(String JSONResponse) throws ClientProtocolException, IOException {
            List<String> result = new ArrayList<String>();
            try {

                JSONObject responseObject = new JSONObject(JSONResponse);
                JSONArray earthquakes = responseObject
                        .getJSONArray(EARTHQUAKE_TAG);

                // Iterate over earthquakes list
                for (int idx = 0; idx < earthquakes.length(); idx++) {

                    // Get single earthquake data - a Map
                    JSONObject earthquake = (JSONObject) earthquakes.get(idx);

                    // Summarize earthquake data as a string and add it to
                    // result
                    result.add(MAGNITUDE_TAG + ":"
                            + earthquake.get(MAGNITUDE_TAG) + ","
                            + LATITUDE_TAG + ":"
                            + earthquake.getString(LATITUDE_TAG) + ","
                            + LONGITUDE_TAG + ":"
                            + earthquake.get(LONGITUDE_TAG));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }  */
}

