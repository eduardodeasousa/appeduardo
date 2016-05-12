package br.android.androidhttpclientjson;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    HttpHelper request2 = new HttpHelper();           /* Comunicação HTTP */

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

    protected String getCategorias()
    {
        this.url = "http://200.131.56.212/magento/index.php/rest/V1/categories";
        try {
            this.result = request2.doGet(requests, url, param, "UTF-8");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return this.result;
    }

    public ArrayList<categoria> getAllCategories () {
        ArrayList<categoria> result = new ArrayList<categoria>();
        requisicoes consulta = new requisicoes();
        JSONObject manipulacao = null;
        try {
            manipulacao = new JSONObject(consulta.getCategorias());
            JSONArray resultado_categoria = manipulacao.getJSONArray("children_data");
            Integer i = 0;
            while (i < resultado_categoria.length()){
                JSONObject categoriaJSON = resultado_categoria.getJSONObject(i);
                i++;
                categoria nova = new categoria(categoriaJSON.getString("name"),categoriaJSON.getInt("id"));
                result.add(nova);
            }  } catch (JSONException e) {e.printStackTrace();}
        return result;
    }


    protected String postProduto(String nome,String valor,String sku,String qtd,String categoria)
    {
        this.url = "http://200.131.56.212/magento/index.php/rest/V1/products";
        this.param.clear();
        JSONObject prodFinal = new JSONObject();
        JSONObject product = new JSONObject();
        try {
            SimpleDateFormat dataAtual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String SdataAtual = dataAtual.format(new Date());

            product.put("sku", sku);               //CODIGO SKU
            product.put("name", nome);             //NOME
            product.put("attribute_set_id",4);     //
            product.put("price", valor);           //PRECO
            product.put("status",1);
            product.put("visibility",4);           //VISIBILIDADE: 1-> Nao visilivel, individualizado 2-> Somente catalogo  3-> Somente pesquisa 4 -> Catalogo e Pesquisa
            product.put("type_id","simple");
            product.put("created_at",SdataAtual);  //CRIADO EM
            product.put("updated_at",SdataAtual);  //EDITADO EM
            product.put("weight",2);               //PESO
            JSONObject extensionAttributes = new JSONObject();
           /* JSONObject bundleProductOptions = new JSONObject();x
            bundleProductOptions.put("optionID","0");
            bundleProductOptions.put("title",nome);
            bundleProductOptions.put("required","false");
            bundleProductOptions*/
            JSONObject stockItem = new JSONObject();
            stockItem.put("stockId",1);
            stockItem.put("qty",qtd);
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
            JSONObject custom1 = new JSONObject();
            custom1.put("attributeCode","category_ids");
            custom1.put("value", categoria);
            customAttributes.put(custom1);
            extensionAttributes.put("stockItem",stockItem);
            product.put("extensionAttributes",extensionAttributes);
            product.put("customAttributes",customAttributes);
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
