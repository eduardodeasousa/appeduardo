package br.android.androidhttpclientjson;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
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

            JSONArray customAttributes = new JSONArray();
            JSONObject custom1 = new JSONObject();
            custom1.put("attributeCode","category_ids");
            custom1.put("value", categoria);
            customAttributes.put(custom1);
            extensionAttributes.put("stockItem",stockItem);

            JSONArray mediaGalleryEntries = new JSONArray();
            JSONObject imagem = new JSONObject();
            imagem.put("id",1);
            imagem.put("mediaType","image");
            imagem.put("label","produto01");
            imagem.put("position",1);
            imagem.put("disabled","false");
            JSONArray imgTypes = new JSONArray(); //types
            imagem.put("types",imgTypes);
            JSONObject content = new JSONObject();
            content.put("base64EncodedData","iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAACKFBMVEUAAADeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCbeLCb///+Rhb+iAAAAtnRSTlMAAAw4Y3JgMQcGT7ry/+ygJgMECoPz68S31PnEIBZbBwF2+vSWLwsRGmbnjQ2xTDfk82sEGIfHpihr/9QctKeQiAMFnPqowJkq6ucsuNjO3CELKebDC3udPfPRKtLi5J0DZEDmxhU5Qax8Q/XK1m8Gr0GT+8We0PulHaaNoFwDu5cWeMbWumV6+uI1SEx69X4TCxEPL5z3+nUBCBQavf3Om5a46vSFCgAim+r/879UBSpXa2A5DtanTDkAAAABYktHRLfdADtnAAAAB3RJTUUH3gsaDRwlCT9LXAAAAPNJREFUGNNjYMAEjIxMzCysbOwcjIwQPicXN8+2bbx8/AKMgkKMDIzCIqLbxMQlJKW2ScvIyskzKCgqKauoqqlraGpt09bR1WPQNzA0MjYxNTO3sLSytrG1Y7Df5uDo5Ozi6ubu4enl7ePL4OcfEBgUHBIaFh4RGRUdE8sQF5+QmJSckpqWnrEtMys7hyE3L7+gsKi4pLSsvKKyals1Q01tXX1DY1NzS2tbantHZxdDdw9jb1//hImTJk+ZOm36jJkMs2Yzzpk7b/6ChYsWb1uydBnQpcsZGVesXLV627Y1a9flQ33DuH7Dxk2bt2yF+g4VAACU6UkQwQoNvwAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAxNC0xMS0yNlQxMzoyODozNyswMTowMIsapHcAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMTQtMTEtMjZUMTM6Mjg6MzcrMDE6MDD6RxzLAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAABJRU5ErkJggg==");
            content.put("type","image/png");
            content.put("name","prod01.png");
            imagem.put("content",content);
            mediaGalleryEntries.put(imagem);
            product.put("extensionAttributes",extensionAttributes);
            product.put("customAttributes",customAttributes);
            product.put("mediaGalleryEntries",mediaGalleryEntries);
            prodFinal.put("product",product);
            prodFinal.put("saveOptions",true);

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

    protected String getBaseMediaURL()
    {
        String result = "http://200.131.56.212/magento/pub/media/catalog/product";
        return result;
    }
   // FileInputStream novo = new FileInputStream(

}
