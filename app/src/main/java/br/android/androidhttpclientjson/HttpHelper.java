package br.android.androidhttpclientjson;

/**
 * Created by romualdocosta on 26/02/16.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpHelper {
    public final int TIMEOUT_MILLIS = 15000;

    public String doGet(String url) throws IOException {
        return doGet(url, null, "UTF-8");
    }

    public String doGet(String url, Map<String, String> params, String charset) throws IOException {

        String queryString = getQueryString(params);
        if (queryString != null && queryString.trim().length() > 0) {
            url += "?" + queryString;
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;

        try {
            conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);
            conn.connect();

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            byte[] bytes = toBytes(in);
            s = new String(bytes, charset);
            in.close();

        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return s;
    }


    public String doGet(Map<String, String> requests,String url, Map<String, String> params, String charset) throws IOException {

        String queryString = getQueryString(params);
        if (queryString != null && queryString.trim().length() > 0) {
            url += "?" + queryString;
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;

        try {
            conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);

            if (requests != null && requests.size() != 0) {
                for (String chave : requests.keySet()) {
                    conn.setRequestProperty(chave, requests.get(chave));
                }
            }

            conn.connect();

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            byte[] bytes = toBytes(in);
            s = new String(bytes, charset);
            in.close();

        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return s;
    }


    public String doDelete(String url) throws IOException {
        return doDelete(url, null, "UTF-8");
    }

    public String doDelete(String url, Map<String, String> params, String charset) throws IOException {
        String queryString = getQueryString(params);
        if (queryString != null && queryString.trim().length() > 0) {
            url += "?" + queryString;
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;
        try {
            conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod("DELETE");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);

            conn.connect();

            InputStream in = conn.getInputStream();
            byte[] bytes = toBytes(in);
            s = new String(bytes, charset);
            in.close();

        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return s;
    }

    public String doPost(String url, Map<String, String> params, String charset) throws IOException {
        String queryString = getQueryString(params);
        byte[] bytes = params != null ? queryString.getBytes(charset) : null;
        return doPost(url, bytes, charset);
    }

    public String doPost(Map<String, String> requests, String url, byte[] params, String charset) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;
        try {
            conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (requests != null && requests.size() != 0) {
                for (String chave : requests.keySet()) {
                    conn.setRequestProperty(chave, requests.get(chave));
                }
            }

            conn.connect();

            if (params != null) {
                OutputStream out = conn.getOutputStream();
                out.write(params);
                out.flush();
                out.close();
            }
            InputStream in = null;
            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            byte[] bytes = toBytes(in);
            s = new String(bytes, charset);
            in.close();

        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return s;
    }


    public String doPost(String url, byte[] params, String charset) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;
        try {
            conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();

            if (params != null) {
                OutputStream out = conn.getOutputStream();
                out.write(params);
                out.flush();
                out.close();
            }
            InputStream in = null;
            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            byte[] bytes = toBytes(in);
            s = new String(bytes, charset);
            in.close();

        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return s;
    }


    /**
     * Retorna a QueryString para 'GET'
     */
    public String getQueryString(Map<String, String> params) throws IOException {
        if (params == null || params.size() == 0) {
            return null;
        }
        String urlParams = null;
        for (String chave : params.keySet()) {
            Object objValor = params.get(chave);
            if (objValor != null) {
                String valor = objValor.toString();
                urlParams = urlParams == null ? "" : urlParams + "&";
                urlParams += chave + "=" + valor;
            }
        }
        return urlParams;

    }

    private byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                baos.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
