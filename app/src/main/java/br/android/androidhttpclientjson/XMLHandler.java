package br.android.androidhttpclientjson;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.List;

/**
 * Created by eduardo on 18/05/16.
 */
public class XMLHandler {

    Document doc;
    AssetManager assets;
    NodeList list;
    NodeList filhos;
    String idSearched = "3";
    Node actual;

    public XMLHandler (/*AssetManager assets*/)
    {//construtor
       // this.assets = assets;
    }

    public Node getCategoryByID (NodeList nodes)
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


     class XMLThread extends AsyncTask<Void, Void, Template> {

        @Override
        protected Template doInBackground(Void... params) {

            InputStream inputStream = null;
            try {
                Log.d("teste",new File("template.xml").getAbsolutePath());
                FileInputStream fis = new FileInputStream(new File("/src/main/assets/template.xml").getCanonicalPath());
                inputStream = (InputStream) fis;
                Log.d("teste",fis.toString());

                //fis.close();
                //inputStream = assets.open("template.xml");} catch (IOException e) {e.printStackTrace();
            } catch (FileNotFoundException e) { Log.d("teste",e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();} catch (ParserConfigurationException e) {e.printStackTrace();
            }
            try {

                doc = dBuilder.parse(inputStream); } catch (SAXException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();
            }
            doc.getDocumentElement().normalize();
            list = doc.getElementsByTagName("category");

            actual = getCategoryByID(list);
            filhos = actual.getChildNodes();
            Template objTemplate = new Template();

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

            return objTemplate;
        }
    }

}
