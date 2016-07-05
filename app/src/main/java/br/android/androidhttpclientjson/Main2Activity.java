package br.android.androidhttpclientjson;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        protected List<String> doInBackground(Void... params) {
            requisicoes reqNova = new requisicoes();
            JSONResponseHandler2 json = new JSONResponseHandler2();
            result = reqNova.obtemProdutos();
            try { return json.handleResponse(result);}
            catch (IOException e1) {e1.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            setListAdapter(new ArrayAdapter<String>(Main2Activity.this, R.layout.activity_main2, result));
        }
    }

    private class JSONResponseHandler2 {

        private static final String LONGITUDE_TAG = "sku";
        private static final String LATITUDE_TAG = "name";
        private static final String MAGNITUDE_TAG = "price";
        private static final String EARTHQUAKE_TAG = "items";

        public List<String> handleResponse(String JSONResponse)
                throws IOException {
            List<String> result = new ArrayList<String>();
            try {
                JSONObject responseObject = new JSONObject(JSONResponse);
                JSONArray earthquakes = responseObject
                        .getJSONArray(EARTHQUAKE_TAG);
                for (int idx = 0; idx < earthquakes.length(); idx++) {
                    JSONObject earthquake = (JSONObject) earthquakes.get(idx);
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
}

