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

package com.manifoldtechnology.manifold_api_android_client.api.assets;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.manifoldtechnology.manifold_api_android_client.api.AbstractApi;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;
import com.manifoldtechnology.manifold_api_android_client.service.Utilities;
import com.manifoldtechnology.manifold_api_android_client.service.request.JsonObjectRequestBasicAuth;
import com.manifoldtechnology.manifold_api_android_client.service.request.RequestQueueSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Default implementation of the <code>SellApi</code> using Volley for HTTP requests.
 */
public class SellApiImpl extends AbstractApi implements SellApi {

    public SellApiImpl(Context context, ManifoldApiConnector connector){
        super(context, connector);
    }

    @Override
    public void sell(String bidType, float bidRate, long futureValue, int daysToMaturity, float par, String ticker,
                     Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath("Broker")
                .appendPath("request")
                .build().toString();

        JSONObject requestBody = new JSONObject();
        requestBody.put("metadata", new JSONObject());
        requestBody.put("type", bidType);
        requestBody.put("ticker", ticker);
        requestBody.put("amount", 1);
        requestBody.put("issuer", getConnector().getUsername());

        requestBody.getJSONObject("metadata").put("futureValue", futureValue);

        long discount = 0;

        if (bidType.equals("Security")) {
            discount = (long) (futureValue * (bidRate / 100) * (daysToMaturity / 360));
            requestBody.getJSONObject("metadata").put("par", par);
        } else if (bidType.equals("Short-term Loans") || bidType.equals("CDO") || bidType.equals("Mortgage")) {
            discount = (long) (futureValue * bidRate);
        }

        requestBody.getJSONObject("metadata").put("discount", discount);
        requestBody.getJSONObject("metadata").put("cost", futureValue - discount);
        requestBody.getJSONObject("metadata").put("discountRate", bidRate);
        requestBody.getJSONObject("metadata").put("daysToMaturity", daysToMaturity);
        requestBody.getJSONObject("metadata").put("requestDate", new Date().getTime());

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.POST, url,
                getConnector().getUsername(), getConnector().getPassword(), requestBody,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}
