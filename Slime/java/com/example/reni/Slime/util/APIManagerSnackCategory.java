package com.example.reni.Slime.util;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.RequestQueue;
import com.example.reni.Slime.db.SnackCategoryDB;
import com.example.reni.Slime.model.SnackCategory;

import org.json.JSONArray;
import org.json.JSONObject;


public class APIManagerSnackCategory {
    public static String SNACK_CATEGORY_URL = "https://raw.githubusercontent.com/ReniKartika7/proyek-mob-prog-slime-sem8/main/JSON/SnackCategory.json";

    private static RequestQueue queue;

    Context ctx;

    public APIManagerSnackCategory(Context ctx){
        this.ctx = ctx;
        initialize(ctx);
    }

    // inisialisasi queue
    public static void initialize(Context ctx){
        queue = Volley.newRequestQueue(ctx);
    }

    public static void viewSnackCategory(final Context context){
        //API request
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                SNACK_CATEGORY_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            SnackCategoryDB snackCategoryDB;
                            snackCategoryDB = new SnackCategoryDB(context);

                            for(int i = 0; i < response.length(); i++){
                                JSONObject temp = response.getJSONObject(i);

                                //Parsing data
                                SnackCategory snackCategory = new SnackCategory();

                                snackCategory.name = temp.getString("SnackCategoryName");
                                snackCategory.coverUrl = temp.getString("SnackCategoryCoverUrl");

                                snackCategoryDB.insertSnackCategory(snackCategory);
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
