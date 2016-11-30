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

package com.manifoldtechnology.manifold_api_android_client.api.ticker;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;
import com.manifoldtechnology.manifold_api_android_client.service.Utilities;
import com.manifoldtechnology.manifold_api_android_client.service.request.JsonObjectRequestBasicAuth;
import com.manifoldtechnology.manifold_api_android_client.service.request.RequestQueueSingleton;

import org.json.JSONObject;

import java.util.List;

public class LiveTickerApiImpl implements LiveTickerApi {

    private LiveTickerApiResponseHandler responseHandler;
    private ManifoldApiConnector connector;
    private Context context;

    public LiveTickerApiImpl(Context context, LiveTickerApiResponseHandler responseHandler, ManifoldApiConnector connector){
        this.responseHandler = responseHandler;
        this.connector = connector;
    }

    @Override
    public void getLiveTicker(List<String> tickers){

        StringBuilder sb = new StringBuilder();
        for (String ticker : tickers) {
            sb.append(ticker + ",");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }

        String url = Utilities.getUrlRoot(connector)
                .appendPath("stocks")
                .appendPath("NASDAQ")
                .appendQueryParameter("symbols", sb.toString())
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                connector.getUsername(), connector.getPassword(), null, JsonObjectRequestBasicAuth.Type.JSON_ARRAY,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseHandler.handleLiveTickerResponse(response);
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
    public LiveTickerApiResponseHandler getLiveTickerApiResponseHandler() {
        return responseHandler;
    }
}
