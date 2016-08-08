package br.android.androidhttpclientjson;

import android.app.ListActivity;
import android.content.Intent;
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

public class Main2Activity extends ListActivity implements ListView.OnItemClickListener {
    ListActivity teste;
    private ListView lista;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teste = this;
        new HttpGetTask().execute();
        lista = this.getListView();
        lista.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(Main2Activity.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        it = new Intent(this, productActivity.class);
        it.putExtra("Dados",parent.getItemAtPosition(position).toString());
        startActivity(it);
        finish();
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

        public List<String> handleResponse(String JSONResponse) throws IOException {
            List<String> result = new ArrayList<String>();
            try {
                JSONObject responseObject = new JSONObject(JSONResponse);
                JSONArray arrayProduto = responseObject.getJSONArray("items");
                Log.d("response",arrayProduto.toString());
                for (int idx = 0; idx < arrayProduto.length(); idx++) {
                    JSONObject produtoSingular = (JSONObject) arrayProduto.get(idx);
                    result.add("Nome: "   + produtoSingular.getString("name") +
                               ",SKU: "   + produtoSingular.get("sku") +
                               ",Preco: " + produtoSingular.get("price"));
                }
            } catch (JSONException e) {e.printStackTrace();}
            return result;
        }
    }
}

