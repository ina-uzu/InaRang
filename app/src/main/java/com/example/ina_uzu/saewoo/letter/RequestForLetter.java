package com.example.ina_uzu.saewoo.letter;

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
import com.example.ina_uzu.saewoo.login.LoginInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestForLetter {
    private static String url = RequestInfo.base_url + "letter/";

    public static List<LetterListItem> GetRequest(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        final List<LetterListItem> list= new ArrayList<LetterListItem>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, (JSONArray)null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject obj = response.getJSONObject(i);
                        int sender = obj.getInt("sender");
                        if(sender==LoginInfo.getWho())
                            continue;

                        int id = obj.getInt("id");
                        String date = obj.getString("date");
                        String title = obj.getString("title");
                        String content= obj.getString("content");
                        list.add(new LetterListItem(id,sender,Integer.parseInt(date),title,content));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

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

    public static void PostRequest(Context context,int sender, String date, String title , String content){
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

    public static void DeleteRequest(Context context, int id){
        RequestQueue queue = Volley.newRequestQueue((Context) context);
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
        });
        queue.add(jsonObjectRequest);
    }

 }
