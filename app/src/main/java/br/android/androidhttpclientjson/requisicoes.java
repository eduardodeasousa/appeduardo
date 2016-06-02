package br.android.androidhttpclientjson;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
    AssetManager contextAsset;
    Template objTemplate = new Template();
    public requisicoes ()
    {
        pegaToken meuToken = new pegaToken();
        this.tokenUnico = meuToken.getTokenHeader();
        this.requests.put("Content-Type", "application/json");
        this.requests.put("Authorization", this.tokenUnico);
        //this.contextActivity = contexto;
    }

    public requisicoes (AssetManager asset)
    {
        pegaToken meuToken = new pegaToken();
        this.tokenUnico = meuToken.getTokenHeader();
        this.requests.put("Content-Type", "application/json");
        this.requests.put("Authorization", this.tokenUnico);
        this.contextAsset = asset;
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


    protected String postProduto(String nome,String valor,String qtd,String categoria)
    {
        this.url = "http://200.131.56.212/magento/index.php/rest/V1/products";
        this.param.clear();
        JSONObject prodFinal = new JSONObject();
        JSONObject product = new JSONObject();
        try {
            objTemplate = readXML(contextAsset);

          //objTemplate = new XMLHandler(contextActivity.getAssets()).new XMLThread().execute().get();

            SimpleDateFormat dataAtual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String SdataAtual = dataAtual.format(new Date());
            SimpleDateFormat dataSKU = new SimpleDateFormat("HH:mm:ss-dd-MM-yyyy");
            String SdataSKU = dataSKU.format(new Date());
            String meuSKU = nome.concat(SdataSKU);


            product.put("sku",meuSKU);               //CODIGO SKU
            product.put("name", nome);             //NOME
            product.put("attribute_set_id",objTemplate.attribute_set_id);     //
            product.put("price", valor);           //PRECO
            product.put("status",objTemplate.status);
            product.put("visibility",objTemplate.visibility);           //VISIBILIDADE: 1-> Nao visilivel, individualizado 2-> Somente catalogo  3-> Somente pesquisa 4 -> Catalogo e Pesquisa
            product.put("type_id",objTemplate.type_id);
            product.put("created_at",SdataAtual);  //CRIADO EM
            product.put("updated_at",SdataAtual);  //EDITADO EM
            product.put("weight",objTemplate.weight);               //PESO
            JSONObject extensionAttributes = new JSONObject();

            JSONObject stockItem = new JSONObject();
            stockItem.put("stockId",objTemplate.stockId);
            stockItem.put("qty",objTemplate.qty);
            stockItem.put("isInStock",objTemplate.isInStock);
            stockItem.put("isQtyDecimal",objTemplate.isQtyDecimal);
            stockItem.put("useConfigMinQty",objTemplate.useConfigMinQty);
            stockItem.put("minQty",objTemplate.minQty);
            stockItem.put("useConfigMaxSaleQty",objTemplate.useConfigMaxSaleQty);
            stockItem.put("maxSaleQty",objTemplate.maxSaleQty);
            stockItem.put("useConfigBackorders",objTemplate.useConfigBackorders);
            stockItem.put("backorders",objTemplate.backorders);
            stockItem.put("useConfigNotifyStockQty",true);
            stockItem.put("notifyStockQty",20);
            stockItem.put("useConfigQtyIncrements",objTemplate.useCOnfigQtyIncrements);
            stockItem.put("qtyIncrements",objTemplate.qtyIncrements);
            stockItem.put("useConfigManageStock",objTemplate.useConfigManageStock);
            stockItem.put("manageStock",objTemplate.manageStock);
            stockItem.put("isDecimalDivided",objTemplate.isDecimalDivided);
            stockItem.put("stockStatusChangedAuto",objTemplate.stockStatusChangedAuto);

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
        } //catch (InterruptedException e) {
        //  e.printStackTrace();
        //} catch (ExecutionException e) {
          //  e.printStackTrace();
    //    }
        byte[] data = (prodFinal.toString()).getBytes();
        this.requests.put("Content-Type", "application/json");
        this.requests.put("Content-Length", String.valueOf(data.length));

        try {
            this.result = request2.doPost(requests, url, data, "UTF-8");
        } catch (IOException e1) { e1.printStackTrace();}

        return this.result;
    }

    protected String getBaseMediaURL()
    {
        String result = "http://200.131.56.212/magento/pub/media/catalog/product";
        return result;
    }

    public Node getCategoryByID (NodeList nodes, String idSearched)
    {
        for (int i = 0; i< nodes.getLength(); i++){
            Log.d("teste","ID de "+i+" : "+nodes.item(i).getAttributes().getNamedItem("id").getNodeValue());
            if ( nodes.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(idSearched) )
            {
                return nodes.item(i);
            }
        }
        return null;
    }

    protected Template readXML(AssetManager assets)
    {
        Document doc;
        NodeList list;
        NodeList filhos;
        String idSearched = "3";
        Node actual;

        InputStream inputStream = null;
        try {
            inputStream = assets.open("template.xml");}
        catch (IOException e) {
            e.printStackTrace();
            Log.d("teste","erro ao ler template: "+e.toString());
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            list = doc.getElementsByTagName("category");
            actual = getCategoryByID(list,idSearched);
            filhos = actual.getChildNodes();
            Template objTemplate = new Template();

            Log.d("teste","Meu teste: "+filhos.item(1).getAttributes().item(1).getNodeValue());
            objTemplate.attribute_set_id = filhos.item(1).getAttributes().item(1).getNodeValue();
            objTemplate.status = filhos.item(3).getAttributes().item(1).getNodeValue();
            objTemplate.visibility = filhos.item(5).getAttributes().item(1).getNodeValue();
            objTemplate.type_id = filhos.item(7).getAttributes().item(1).getNodeValue();
            objTemplate.weight = filhos.item(9).getAttributes().item(1).getNodeValue();
            objTemplate.stockId = filhos.item(11).getChildNodes().item(1).getAttributes().item(1).getNodeValue();
            objTemplate.qty = filhos.item(11).getChildNodes().item(3).getAttributes().item(1).getNodeValue();
            objTemplate.isInStock = filhos.item(11).getChildNodes().item(5).getAttributes().item(1).getNodeValue();
            objTemplate.isQtyDecimal = filhos.item(11).getChildNodes().item(7).getAttributes().item(1).getNodeValue();
            objTemplate.useConfigMinQty = filhos.item(11).getChildNodes().item(9).getAttributes().item(1).getNodeValue();
            objTemplate.minQty = filhos.item(11).getChildNodes().item(11).getAttributes().item(1).getNodeValue();
            objTemplate.useConfigMaxSaleQty = filhos.item(11).getChildNodes().item(13).getAttributes().item(1).getNodeValue();
            objTemplate.maxSaleQty = filhos.item(11).getChildNodes().item(15).getAttributes().item(1).getNodeValue();
            objTemplate.useConfigBackorders = filhos.item(11).getChildNodes().item(17).getAttributes().item(1).getNodeValue();
            objTemplate.backorders = filhos.item(11).getChildNodes().item(19).getAttributes().item(1).getNodeValue();
            objTemplate.useCOnfigQtyIncrements = filhos.item(11).getChildNodes().item(21).getAttributes().item(1).getNodeValue();
            objTemplate.qtyIncrements = filhos.item(11).getChildNodes().item(23).getAttributes().item(1).getNodeValue();
            objTemplate.useConfigManageStock = filhos.item(11).getChildNodes().item(25).getAttributes().item(1).getNodeValue();
            objTemplate.manageStock = filhos.item(11).getChildNodes().item(27).getAttributes().item(1).getNodeValue();
            objTemplate.isDecimalDivided = filhos.item(11).getChildNodes().item(29).getAttributes().item(1).getNodeValue();
            objTemplate.stockStatusChangedAuto = filhos.item(11).getChildNodes().item(31).getAttributes().item(1).getNodeValue();
            objTemplate.saveOptions = filhos.item(13).getAttributes().item(1).getNodeValue();


        } catch (ParserConfigurationException e) {e.printStackTrace();
        } catch (SAXException e) {e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();
        }
        return objTemplate;
    }

}


