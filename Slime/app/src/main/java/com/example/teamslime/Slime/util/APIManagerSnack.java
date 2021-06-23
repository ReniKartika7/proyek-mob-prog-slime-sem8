package com.example.teamslime.Slime.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamslime.Slime.db.SnacksDB;
import com.example.teamslime.Slime.model.Snack;

import org.json.JSONArray;
import org.json.JSONObject;


public class APIManagerSnack {
    public static String SNACK_URL = "https://raw.githubusercontent.com/ReniKartika7/proyek-mob-prog-slime-sem8/main/JSON/Snack.json";

    private static RequestQueue queue;

    Context ctx;

    public APIManagerSnack(Context ctx){
        this.ctx = ctx;
        initialize(ctx);
    }

    // inisialisasi queue
    public static void initialize(Context ctx){
        queue = Volley.newRequestQueue(ctx);
    }

    public static void viewSnackItem(final Context context){
        //API request
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                SNACK_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            SnacksDB snacksDB;
                            snacksDB = new SnacksDB(context);

                            for(int i = 0; i < response.length(); i++){
                                JSONObject temp = response.getJSONObject(i);

                                //Parsing data
                                Snack snack = new Snack();

                                snack.name = temp.getString("SnackName");
                                snack.price = temp.getInt("SnackPrice");
                                snack.stock = temp.getInt("SnackStock");
                                snack.categoryID = temp.getInt("SnackCategoryID");
                                snack.coverUrl = temp.getString("SnackCoverUrl");
                                snack.detail = temp.getString("SnackDetail");

                                snacksDB.insertSnack(snack);
                            }

                        }catch (Exception e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(request);
    }
}
