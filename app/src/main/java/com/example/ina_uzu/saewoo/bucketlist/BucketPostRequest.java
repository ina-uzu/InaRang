package com.example.ina_uzu.saewoo.bucketlist;

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

public class BucketPostRequest {
    private String content;
    private int done;
    private final String url = RequestInfo.base_url+"bucketlist/";

    BucketPostRequest(Context context, String content, int done){
        this.content=content;
        this.done=done;

        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject post = new JSONObject();

        try {
            post.put("content", content);
            post.put("done", done);
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
