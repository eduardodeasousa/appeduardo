package br.android.androidhttpclientjson;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by eduardo on 11/05/16.
 */
public class categoria {
    String nome;
    int id;

    public categoria (String nome, int id)
    {
        this.nome=nome;
        this.id=id;
    }

}