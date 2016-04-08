package com.turing.www.demo.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.turing.www.demo.appliaction.MyApplication;

import java.util.Map;

/**
 * Created by Administrator on 2015/11/25.
 */
public class VolleyRequest
{

    public static StringRequest mRequest;
    public static Context context;

    public static void RequestGet(Context context, String url, String tag, VolleyInterface volleyInterface)
    {

        MyApplication.getHttpQueues().cancelAll(tag);
        mRequest = new StringRequest(Request.Method.GET, url, volleyInterface.loadingListener(),
                volleyInterface.errorListener());
        MyApplication.getHttpQueues().add(mRequest);
        MyApplication.getHttpQueues().start();
    }

    public static void RequestPost(Context context, String url, String tag, final Map<String, String> params,
                                   VolleyInterface volleyInterface)
    {
        MyApplication.getHttpQueues().cancelAll(tag);
        mRequest = new StringRequest(Request.Method.POST, url, volleyInterface.loadingListener(),
                volleyInterface.errorListener())
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                return params;
            }
        };
        MyApplication.getHttpQueues().add(mRequest);
        MyApplication.getHttpQueues().start();
    }

}
