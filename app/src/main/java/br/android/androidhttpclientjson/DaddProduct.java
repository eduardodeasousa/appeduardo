package br.android.androidhttpclientjson;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 09/05/16.
 */
public class DaddProduct extends Activity {

    public static DaddProduct instance = null;
    String Snome,Svalor;
    Spinner spinner;
    String[] result;
    String nomeSpinner;
    ArrayList<categoria> arrayCategoria;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.addproduct);
        spinner = (Spinner) findViewById(R.id.spinner1);
        new searchCategories().execute();
    }

    @Override               //Setar instancia como this
    public void onResume()
    {
        super.onResume();
        instance = this;
    }
    @Override             //Remover a instancia da variavel instance
    public void onPause()
    {
        super.onPause();
        instance = null;
    }

    public String[] CategoriesList(ArrayList<categoria> list)
    {
        String[] result = new String[list.size()];
        for (int i=0;i<list.size();i++)
        {
            result[i]=list.get(i).nome;
        }
        return result;
    }

  private EditText nome,valor;

    public void buttonOnClick(View v)
    {
        nome = (EditText) findViewById(R.id.editNome);
        valor = (EditText) findViewById(R.id.editValor);
        new addProductThread().execute();
    }
    private class addProductThread extends AsyncTask<Void, Void, List<String>> {

       @Override
       protected void onPreExecute()
       {
           super.onPreExecute();
           Snome = nome.getText().toString();
           Svalor = valor.getText().toString();
           nomeSpinner = spinner.getSelectedItem().toString();
       }

       @Override
       protected List<String> doInBackground(Void... params) {
           requisicoes reqNova = new requisicoes(getAssets());
           categoria catSelecionada = getObjCategoriaByName(arrayCategoria,nomeSpinner);
           Log.d("teste", reqNova.postProduto(Snome,Svalor,String.valueOf(catSelecionada.id)));
           return null;
       }
   }

    private class searchCategories extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected List<String> doInBackground(Void... params) {
            requisicoes newReq = new requisicoes();
            arrayCategoria = newReq.getAllCategories();
            result = CategoriesList(arrayCategoria);
            return null;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(instance,
                    android.R.layout.simple_spinner_item, result);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }

    protected categoria getObjCategoriaByName(ArrayList<categoria> arrayCat, String nomeSelecionado)
    {
        int i;
        categoria result;
        for (categoria cat:arrayCat)
        {
            if (cat.nome.equals(nomeSelecionado))
                return cat;
        }
        i = arrayCat.lastIndexOf(nomeSelecionado);
        result = arrayCat.get(i);
        return result;
    }
}
