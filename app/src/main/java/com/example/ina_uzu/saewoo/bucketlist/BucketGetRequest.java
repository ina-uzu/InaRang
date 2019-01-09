package com.example.ina_uzu.saewoo.bucketlist;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ina_uzu.saewoo.RequestInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BucketGetRequest {

    private String url = RequestInfo.base_url + "bucketlist/";

    public BucketGetRequest(Context context){
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
    }

    void setLetterList(JSONArray response){
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
}
