/*
 * manifold-api-android-client
 *
 * MIT License
 *
 * Copyright (C) 2016, Manifold Technology (contact@manifoldtechnology.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.manifoldtechnology.manifold_api_android_client.service.request;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JsonObjectRequestBasicAuth extends JsonObjectRequest{

    /**
     * A response handler should consistently expect to receive a <code>JSONObject</code> but sometimes a
     * <code>JSONArray</code> is received from the request instead.  This <code>Type</code> allows a
     * <code>JSONArray</code> to be wrapped inside a <code>JSONObject</code>.
     */
    public enum Type{
        JSON_OBJECT, JSON_ARRAY, STRING;
    }

    private String username;
    private String password;
    private Type type;

    public JsonObjectRequestBasicAuth(int method, String url, String username, String password, JSONObject requestBody,
                                      JsonObjectRequestBasicAuth.Type type, Response.Listener<JSONObject> listener,
                                      Response.ErrorListener errorListener){

        super(method, url, requestBody, listener, errorListener);

        this.username = username;
        this.password = password;
        this.type = type;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        JSONObject jsonObject = new JSONObject();

        try {
            if(response.data != null) {
                String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                if(type == Type.JSON_ARRAY){
                    jsonObject.put("array", new JSONArray(data));
                }
                else if(type == Type.STRING){
                    jsonObject.put("result", data.replaceAll("^\"|\"$", ""));
                }
                else {
                    jsonObject = new JSONObject(data);
                }
            }

            // TODO: this could be used instead of basic auth
            String jSessionId = response.headers.get("Set-Cookie");

            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
        }
        catch(JSONException e){
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError){
        Log.d("parseNetworkError", volleyError.toString());

        VolleyError error = volleyError;
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            String json = new String(volleyError.networkResponse.data);
            error = new VolleyError(json);
        }

        return error;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();

        if(username != null && password != null) {
            String credentials = username + ":" + password;
            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            headers.put("Authorization", auth);
        }

        return headers;
    }
}
