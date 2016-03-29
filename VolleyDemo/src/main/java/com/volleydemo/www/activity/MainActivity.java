package com.volleydemo.www.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.volleydemo.www.R;
import com.volleydemo.www.appliaction.MyApplication;
import com.volleydemo.www.util.VolleyInterface;
import com.volleydemo.www.util.VolleyRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ImageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //vollyGet();
        //vollyPost();
        volly_Post();
    }

    /**
     * get
     */
    private void vollyGet() {
        String url = "";

        //-----------------------StringRequest-----------------------

//        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                //请求成功回调
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                //请求失败回调
//
//            }
//        });
//
//        mStringRequest.setTag("abc_get");
//        MyApplication.getHttpQueues().add(mStringRequest);


        //----------------------JsonObjectRequest---------------------

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("");
            }
        });

        mRequest.setTag("abc_get");
        MyApplication.getHttpQueues().add(mRequest);
    }


    /**
     * post
     */
    private void vollyPost() {
        String url = "http://117.34.70.91:8080/scenic/ScenicInfo_findScenicTicket.action";
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", "");
                params.put("priceType", "");
                return params;
            }
        };

        mStringRequest.setTag("abcPost");
        MyApplication.getHttpQueues().add(mStringRequest);
    }


    /**
     * 二次封装的回调方法Get
     */
    public void volly_Get() {
        String url = "";
        VolleyRequest.RequestGet(this, url, "abcGet", new VolleyInterface(this, VolleyInterface.mSuccessListener,
                VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 二次封装的回调方法Post
     */
    public void volly_Post() {
        String url = "http://117.34.70.91:8080/scenic/ScenicInfo_findScenicTicket.action";

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "");
        params.put("priceType", "");

        VolleyRequest.RequestPost(this, url, "abcPost", params, new VolleyInterface(this,
                VolleyInterface.mSuccessListener, VolleyInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                textView.setText(result);
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueues().cancelAll("abcPost");
    }
}
