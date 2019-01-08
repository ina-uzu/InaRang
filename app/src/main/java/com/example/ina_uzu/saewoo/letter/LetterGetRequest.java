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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LetterGetRequest {
    private String url = RequestInfo.base_url + "letter/";

    LetterGetRequest(Context context, int id){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, (JSONArray) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("GET_Response", response.toString());

                LetterInfo.letterList = new ArrayList<>();
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject obj = response.getJSONObject(i);

                        int id = obj.getInt("id");
                        int sender = obj.getInt("sender");
                        String date = obj.getString("date");
                        String title = obj.getString("title");
                        String content= obj.getString("content");
                        LetterInfo.letterList.add(new LetterListItem(id,sender,Integer.parseInt(date),title,content));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.d("GET_Error", error.toString());
                }
            });

        queue.add(jsonArrayRequest);
    }

}
