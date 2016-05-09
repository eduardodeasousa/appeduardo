package br.android.androidhttpclientjson;

        import android.app.Activity;
        import android.app.ListActivity;
        import android.net.http.AndroidHttpClient;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ArrayAdapter;
        import android.widget.Toast;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.ResponseHandler;
        import org.apache.http.impl.client.BasicResponseHandler;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;


/**
 * Created by eduardo on 03/05/16.
 */
public class pegaToken {

        HttpHelper request = new HttpHelper();
        Map<String, String> requests = new HashMap<String, String>();
        String url;
        String token;

        protected String getToken() {
            JSONObject user = new JSONObject();
            try {
                user.put("username", "");
                user.put("password", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            byte[] data = (user.toString()).getBytes();

            url = "http://200.131.56.212/magento/index.php/rest/V1/integration/admin/token";
            requests.put("Content-Type", "application/json");
            requests.put("Content-Length", String.valueOf(data.length));
            try {
                token = request.doPost(requests, url, data, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            token = token.replace("\"", "");
            return token;
        }

    protected String getTokenHeader() {
        JSONObject user = new JSONObject();
        try {
            user.put("username", "");
            user.put("password", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte[] data = (user.toString()).getBytes();

        url = "http://200.131.56.212/magento/index.php/rest/V1/integration/admin/token";
        requests.put("Content-Type", "application/json");
        requests.put("Content-Length", String.valueOf(data.length));
        try {
            token = request.doPost(requests, url, data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        token = token.replace("\"", "");
        token = "Bearer "+token;
        return token;
    }

    protected String getTokenAtual()
    {
        return this.token;
    }

}
