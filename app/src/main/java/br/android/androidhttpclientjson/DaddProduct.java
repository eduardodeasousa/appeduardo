package br.android.androidhttpclientjson;
//Versao Final

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eduardo on 09/05/16.
 */
public class DaddProduct extends Activity {

    String Snome,Svalor,SSKU;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduct);
    }
  private EditText nome,qtd,sku,valor;

    public void buttonOnClick(View v)
    {
        nome = (EditText) findViewById(R.id.editNome);
        sku = (EditText) findViewById(R.id.editSKU);
        valor = (EditText) findViewById(R.id.editValor);
        qtd = (EditText) findViewById(R.id.editQtd);
        new addProductThread().execute();
    }
    private class addProductThread extends AsyncTask<Void, Void, List<String>> {

       @Override
       protected void onPreExecute()
       {
           super.onPreExecute();
           Snome = nome.getText().toString();
           Svalor = valor.getText().toString();
           SSKU = sku.getText().toString();
       }

       @Override
       protected List<String> doInBackground(Void... params) {
           requisicoes reqNova = new requisicoes();
           reqNova.postProduto(Snome,Svalor,SSKU);
           return null;
       }
   }

}
