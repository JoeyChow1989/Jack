package com.turing.www.demo.util;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2015/11/25.
 */
public abstract class VolleyInterface
{

    public Context context;
    public static Response.Listener mSuccessListener;
    public static Response.ErrorListener mErrorListener;

    public VolleyInterface(Context context, Response.Listener mSuccessListener,
                           Response.ErrorListener mErrorListener)
    {
        this.context = context;
        this.mSuccessListener = mSuccessListener;
        this.mErrorListener = mErrorListener;
    }


    public abstract void onMySuccess(String result);

    public abstract void onMyError(VolleyError error);


    public Response.Listener<String> loadingListener()
    {

        mSuccessListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String o)
            {
                onMySuccess(o);
            }
        };
        return mSuccessListener;
    }

    public Response.ErrorListener errorListener()
    {
        mErrorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                onMyError(volleyError);
            }
        };
        return mErrorListener;
    }
}
