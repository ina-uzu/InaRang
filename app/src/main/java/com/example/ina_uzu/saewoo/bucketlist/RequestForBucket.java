package com.example.ina_uzu.saewoo.bucketlist;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ina_uzu.saewoo.RequestInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestForBucket {
    private static String url = RequestInfo.base_url + "bucketlist/";

    public static List<BucketListItem> GetRequest(Context context){
        List<BucketListItem> list = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, (JSONArray)null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setLetterList(response);
                Log.d("GET_Response", response.toString());
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("GET_Error", error.toString());
                    }
                });
        queue.add(jsonArrayRequest);

        return list;
    }

    static void setLetterList(JSONArray response){
        BucketListInfo.bucketList= new ArrayList<BucketListItem>();

        try{
            for(int i=0;i<response.length();i++){
                JSONObject obj = response.getJSONObject(i);
                int id = obj.getInt("id");
                String content= obj.getString("content");
                int done = obj.getInt("done");
                boolean is_done =false;

                if(done==1)
                    is_done=true;

                BucketListInfo.bucketList.add(new BucketListItem(id,content, is_done));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void PostRequest(Context context, String content, int done){
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

    public static void DeleteRequest(Context context, int id){
        RequestQueue queue = Volley.newRequestQueue(context);
        String del_url= url+String.valueOf(id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, del_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DELETE_Response: ", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DELETE_Error: ", error.toString());
            }
        }){
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(jsonObjectRequest);
    }

    public static void PutRequest(Context context, final BucketListItem listItem){
        RequestQueue queue = Volley.newRequestQueue(context);
        final int id = listItem.getId();
        String put_url= url+String.valueOf(id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("PUT_Response", response.toString());
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PUT_Error", error.toString());
                }
            }){

            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));
                params.put("content", listItem.getCont());

                int done = 0;
                if( listItem.getIsChecked())
                    done=1;

                params.put("done", String.valueOf(done));

                return params;
            }
        };

    }

}
