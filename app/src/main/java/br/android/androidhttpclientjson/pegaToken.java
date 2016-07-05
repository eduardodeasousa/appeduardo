package br.android.androidhttpclientjson;

        import org.json.JSONException;
        import org.json.JSONObject;
        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by eduardo on 03/05/16.
 * Classe para aquisição de Token e retorno do mesmo.
 */
public class pegaToken {

        HttpHelper request = new HttpHelper();
        Map<String, String> requests = new HashMap<String, String>();
        String url;
        String token;

    protected String getTokenHeader() {
        JSONObject user = new JSONObject();
        try {
            user.put("username", "eduardoadmin");
            user.put("password", "eduardoadmin123");
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
}
