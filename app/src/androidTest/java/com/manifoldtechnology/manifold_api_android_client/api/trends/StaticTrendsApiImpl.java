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

package com.manifoldtechnology.manifold_api_android_client.api.trends;

import android.content.Context;

import com.manifoldtechnology.manifold_api_android_client.R;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class StaticTrendsApiImpl implements TrendsApi {

    private TrendsApiResponseHandler responseHandler;
    private ManifoldApiConnector connector;
    private Context context;
    private Random random = new Random();
    private DateFormat dateFormat;

    public StaticTrendsApiImpl(Context context, TrendsApiResponseHandler responseHandler, ManifoldApiConnector connector){
        this.context = context;
        this.responseHandler = responseHandler;
        this.connector = connector;

        dateFormat = new SimpleDateFormat(context.getString(R.string.full_timestamp_format));
        dateFormat.setTimeZone(TimeZone.getTimeZone(context.getString(R.string.timezone)));
    }

    @Override
    public void getInterestRates(List<String> assetTypes) {
        responseHandler.handleInterestRatesResponse(getTrendsJsonObject());
    }

    @Override
    public void getMarketVolume(List<String> assetTypes, long duration) {
        responseHandler.handleMarketVolumeResponse(getTrendsJsonObject());
    }

    @Override
    public void getMarketValue(List<String> assetTypes, long duration) {
        responseHandler.handleMarketValueResponse(getTrendsJsonObject());
    }

    @Override
    public TrendsApiResponseHandler getTrendsApiResponseHandler() {
        return responseHandler;
    }

    private JSONObject getTrendsJsonObject(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("averageValueToMaturity", new JSONObject());
            jsonObject.getJSONObject("averageValueToMaturity").put("30", 10000);
            jsonObject.getJSONObject("averageValueToMaturity").put("60", 12000);
            jsonObject.getJSONObject("averageValueToMaturity").put("90", 7000);
            jsonObject.getJSONObject("averageValueToMaturity").put("120", 40000);

            jsonObject.put("transactions", new JSONObject());
            jsonObject.getJSONObject("transactions").put("total", 50);
            jsonObject.getJSONObject("transactions").put("pages", 1);
            jsonObject.getJSONObject("transactions").put("results", new JSONArray());

            Date now = new Date();
            long reference = now.getTime();

            for(int i = 0; i < 50; i++){
                JSONObject result = new JSONObject();
                result.put("transferDate", dateFormat.format(reference));
                result.put("cost", random.nextInt(10000));

                jsonObject.getJSONObject("transactions").getJSONArray("results").put(result);

                reference = reference - (1000 * random.nextInt(600));
            }

            jsonObject.put("bids", new JSONArray());
            jsonObject.put("asks", new JSONArray());

            reference = now.getTime();

            for(int i = 0; i < 100; i++){
                JSONObject bid = new JSONObject();
                JSONObject ask = new JSONObject();

                double randomBid = random.nextDouble() * 0.10 * 100;

                bid.put("rate", randomBid);
                bid.put("time", reference);

                ask.put("rate", randomBid + random.nextDouble() * 0.01);
                ask.put("time", reference);

                jsonObject.getJSONArray("bids").put(bid);
                jsonObject.getJSONArray("asks").put(ask);

                reference = reference - (1000 * random.nextInt(600));
            }

        } catch (JSONException e) {
            responseHandler.handleException(e);
        }

        return jsonObject;
    }
}
