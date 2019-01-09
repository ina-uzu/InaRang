package com.example.ina_uzu.saewoo.letter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ina_uzu.saewoo.RequestInfo;

import org.json.JSONObject;

public class LetterDeleteRequest {
    private String url  = RequestInfo.base_url+"letter/";

    LetterDeleteRequest(Context context, int id){
        RequestQueue queue = Volley.newRequestQueue((Context) context);
        url+= String.valueOf(id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DELETE_Response: ", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DELETE_Error: ", error.toString());
            }
        });
    }
}
