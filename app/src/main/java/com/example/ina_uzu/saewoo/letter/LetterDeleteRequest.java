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

    LetterDeleteRequest(Context context){
        RequestQueue queue = Volley.newRequestQueue((Context) context);

    }
}
