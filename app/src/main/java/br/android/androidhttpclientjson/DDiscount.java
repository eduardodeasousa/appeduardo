package br.android.androidhttpclientjson;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by eduardo on 27/06/16.
 */
public class DDiscount extends Activity{

    EditText discount;
    int intDiscount;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddiscount);
        discount = (EditText) findViewById(R.id.discountText);
    }

    public void createCoupon(View v)
    {
        new discountThread().execute();
    }

    private class discountThread extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            String stringDiscount = (discount.getText().toString());
            intDiscount = Integer.parseInt(stringDiscount);
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            requisicoes reqNova = new requisicoes();
            Log.d("teste",reqNova.createCoupon(intDiscount));
            return null;
        }
    }


}
