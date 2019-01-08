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

import org.json.JSONException;
import org.json.JSONObject;

public class LetterPostRequest {
    private int sender;
    private String date;
    private String title;
    private String content;
    private final String url = RequestInfo.base_url+"letter/";

    LetterPostRequest(Context context, int sender, String date, String title, String content){
        this.sender=sender;
        this.date = date;
        this.title  = title;
        this.content=content;

        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject post = new JSONObject();

        try {
            post.put("sender",sender);
            post.put("date", date);
            post.put("title", title);
            post.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, post, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("POST_Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("POST_Error", error.toString());
            }
        });

        queue.add(jsonRequest);
    }
}
