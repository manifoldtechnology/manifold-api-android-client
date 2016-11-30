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

package com.manifoldtechnology.manifold_api_android_client.api.history;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.manifoldtechnology.manifold_api_android_client.R;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;
import com.manifoldtechnology.manifold_api_android_client.service.Utilities;
import com.manifoldtechnology.manifold_api_android_client.service.request.JsonObjectRequestBasicAuth;
import com.manifoldtechnology.manifold_api_android_client.service.request.RequestQueueSingleton;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HistoryApiImpl implements HistoryApi {

    private Context context;
    private HistoryApiResponseHandler responseHandler;
    private ManifoldApiConnector connector;

    public HistoryApiImpl(Context context, HistoryApiResponseHandler responseHandler, ManifoldApiConnector connector){
        this.context = context;
        this.responseHandler = responseHandler;
        this.connector = connector;
    }

    @Override
    public void getHistory(String brokerName, String typeFilter, Date begin, Date end, int page, int size){

        DateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.full_timestamp_format));
        dateFormat.setTimeZone(TimeZone.getTimeZone(context.getString(R.string.timezone)));

        String url = Utilities.getUrlRoot(connector)
                .appendPath(brokerName)
                .appendPath("history")
                .appendQueryParameter("assetType", typeFilter)
                .appendQueryParameter("startTime", dateFormat.format(begin))
                .appendQueryParameter("endTime", dateFormat.format(end))
                .appendQueryParameter("page", Integer.toString(page))
                .appendQueryParameter("size", Integer.toString(size))
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                connector.getUsername(), connector.getPassword(), null, JsonObjectRequestBasicAuth.Type.JSON_OBJECT,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            responseHandler.handleHistoryResponse(response);
                        } catch (Exception e) {
                            responseHandler.handleException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseHandler.handleException(new Exception(Utilities.unpackVolleyError(error, context), error));
                    }
                }
        );

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public HistoryApiResponseHandler getHistoryApiResponseHandler() {
        return responseHandler;
    }
}
