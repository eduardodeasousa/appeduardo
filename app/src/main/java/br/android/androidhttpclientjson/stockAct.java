package br.android.androidhttpclientjson;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 11/07/16.
 * Activity para listagem de produtos em baixo estoque - Valor considerado 15
 */

public class stockAct extends ListActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new getLowStockTask().execute();
    }

    private class getLowStockTask extends AsyncTask<Void, Void, List<String>> {

        String result;

        @Override
        protected List<String> doInBackground(Void... params) {
            requisicoes reqNova = new requisicoes();
            JSONHandler json = new JSONHandler();
            result = reqNova.getLowStock();
            try {
                return json.handleResponse(result);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            setListAdapter(new ArrayAdapter<String>(stockAct.this, R.layout.stockcheck, result));
        }

        private class JSONHandler {

            public List<String> handleResponse(String JSONResponse) throws IOException {
                List<String> result = new ArrayList<String>();
                try {
                    JSONObject responseObject = new JSONObject(JSONResponse);
                    JSONArray responseArray = responseObject.getJSONArray("items");
                    for (int idx = 0; idx < responseArray.length(); idx++) {
                        JSONObject responseSingle = (JSONObject) responseArray.get(idx);
                        result.add("ID do Produto: " + responseSingle.get("product_id") +
                                   ", Quantidade: "  + responseSingle.get("qty"));
                    }
                } catch (JSONException e) {e.printStackTrace();
                }
                return result;
            }
        }
    }
}
