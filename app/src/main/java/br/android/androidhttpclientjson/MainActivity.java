package br.android.androidhttpclientjson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loadButton = (Button) findViewById(R.id.btnLoad);
        loadButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        Main2Activity.class));
            }
        });

        final Button postProduto = (Button) findViewById(R.id.btnAdd);
        postProduto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        DaddProduct.class));
            }
        });

        final Button discountButton = (Button) findViewById(R.id.btnDiscount);
        discountButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        DDiscount.class));
            }
        });

        final Button checkStockBtn = (Button) findViewById(R.id.btnStock);
        checkStockBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        stockAct.class));
            }
        });

        final TextView showMessage = (TextView) findViewById(R.id.textDate);

        SimpleDateFormat formatoData = new SimpleDateFormat("MM-dd");
        SimpleDateFormat formatoData2 = new SimpleDateFormat("dd-MM-yyyy");
        String today = formatoData.format(new Date());
        String natal = "07-11";
        if (today.equals(natal))
        {
            today = formatoData2.format(new Date());
            String message = "Hoje é dia "+ today + ". Que tal criar uma promoção?";
            showMessage.setText(message);
        }

    }
}