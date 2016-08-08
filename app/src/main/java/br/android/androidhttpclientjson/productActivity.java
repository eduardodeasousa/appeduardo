package br.android.androidhttpclientjson;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by eduardo on 15/07/16.
 */
public class productActivity extends Activity {

    EditText editNome;
    EditText editSKU;
    EditText editPreco;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        editNome  = (EditText) findViewById(R.id.edtNome);
        editSKU   = (EditText) findViewById(R.id.edtSKU);
        editPreco = (EditText) findViewById(R.id.edtPreco);
        it = getIntent();
        if (it != null)
          {
              String itResponse = it.getStringExtra("Dados");
              String parts[] = itResponse.split(",");
              String parts2[];
              for ( int i=0; i<3 ; i++)
              {
                  parts2   = parts[i].split(":");
                  parts2[1]=parts2[1].trim();
                  parts[i]=parts2[1];
              }
              editNome.setText(parts[0]);
              editSKU.setText(parts[1]);
              editPreco.setText(parts[2]);
          //  Toast.makeText(this,it.getCharSequenceExtra("Dados"),Toast.LENGTH_LONG).show();
          }

    }

   /*   private class atualizaProdutoThread extends AsyncTask<Void, Void, List<String>> {

      @Override
        protected List<String> doInBackground(Void... params) {
            requisicoes newReq = new requisicoes();
            newReq.atualizaProduto((R.id.editNome.getText().toString()),editSKU.getText().toString(),editPreco.getText().toString());
            return null;
        }
    } */


}

