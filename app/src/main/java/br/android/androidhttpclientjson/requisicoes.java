package br.android.androidhttpclientjson;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import br.android.androidhttpclientjson.pegaToken;

/**
 * Created by eduardo on 04/05/16.
 */
public class requisicoes {

    Map<String, String> requests = new HashMap<String, String>();  /* Headers do documento */
    Map<String, String> param = new HashMap<String, String>();     /* Parametros passados via URL */
    String url,result;
    private String tokenUnico;
    HttpHelper request2 = new HttpHelper();                        /* Comunicação HTTP */

    public requisicoes ()
    {
        pegaToken meuToken = new pegaToken();
        this.tokenUnico = meuToken.getTokenHeader();
        this.requests.put("Content-Type", "application/json");
        this.requests.put("Authorization", this.tokenUnico);

    }

    protected String obtemProdutos()
    {

        this.url = "http://200.131.56.212/magento/index.php/rest/V1/products";
        this.param.put("searchCriteria[page_size]","100");
        try {
            this.result = request2.doGet(requests, url, param, "UTF-8");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return this.result;
    }

    protected String postProduto(String nome,String valor,String sku)
    {
        this.url = "http://200.131.56.212/magento/index.php/rest/V1/products";
        this.param.clear();
        JSONObject prodFinal = new JSONObject();

        JSONObject product = new JSONObject();
        try {

            product.put("sku", sku);
            product.put("name", nome);
            product.put("attribute_set_id",4);
            product.put("price", valor);
            product.put("status",1);
            product.put("visibility",4);
            product.put("type_id","simple");
            product.put("created_at","2016-04-11 19:54:56");
            product.put("updated_at","2016-04-11 19:54:56");
            product.put("weight",2);
            JSONObject extensionAttributes = new JSONObject();
           /* JSONObject bundleProductOptions = new JSONObject();
            bundleProductOptions.put("optionID","0");
            bundleProductOptions.put("title",nome);
            bundleProductOptions.put("required","false");
            bundleProductOptions*/
            JSONObject stockItem = new JSONObject();
            stockItem.put("stockId",1);
            stockItem.put("qty",20);
            stockItem.put("isInStock",true);
            stockItem.put("isQtyDecimal",false);
            stockItem.put("useConfigMinQty",true);
            stockItem.put("minQty",0);
            stockItem.put("useConfigMaxSaleQty",true);
            stockItem.put("maxSaleQty",3);
            stockItem.put("useConfigBackorders",false);
            stockItem.put("backorders",0);
            stockItem.put("useConfigNotifyStockQty",true);
            stockItem.put("notifyStockQty",20);
            stockItem.put("useConfigQtyIncrements",false);
            stockItem.put("qtyIncrements",0);
            stockItem.put("useConfigManageStock",true);
            stockItem.put("manageStock",true);
            stockItem.put("lowStockDate","string");
            stockItem.put("isDecimalDivided",true);
            stockItem.put("stockStatusChangedAuto",0);
            JSONArray options = new JSONArray();
            JSONArray tierPrices = new JSONArray();
            JSONArray customAttributes = new JSONArray();
            extensionAttributes.put("stockItem",stockItem);
            product.put("extensionAttributes",extensionAttributes);
            prodFinal.put("product",product);
            prodFinal.put("saveOptions",true);

            Log.d("teste",prodFinal.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte[] data = (prodFinal.toString()).getBytes();
        this.requests.put("Content-Type", "application/json");
        this.requests.put("Content-Length", String.valueOf(data.length));

        try {
            this.result = request2.doPost(requests, url, data, "UTF-8");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return this.result;
    }

}
