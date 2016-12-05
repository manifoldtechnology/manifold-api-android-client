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

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.List;

/**
 * Provides trend data including current market interest rates for assets, market volume, and market value.
 */
public interface TrendsApi {

    /**
     * Request the current interest rates for bids and asks for specified assets.
     *
     * @param assetTypes a <code>List</code> of asset categories.
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "averageValueToMaturity": {
     *             "30": integer,
     *             "60": integer,
     *             "90": integer,
     *             "120": integer
     *         },
     *         "transactions": {
     *             "total": integer,
     *             "pages": integer,
     *             "results": [{
     *                 "transferDate": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *                 "cost": String (float)
     *             }]
     *         },
     *         "bids": [{
     *             "rate": decimal,
     *             "time": long (ms since epoch)
     *         }],
     *         "asks": [{
     *             "rate": decimal,
     *             "time": long (ms since epoch)
     *         }]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void getInterestRates(List<String> assetTypes, Response.Listener<JSONObject> successListener,
                          Response.ErrorListener errorListener);

    /**
     * Request the current market volume for specified assets.
     *
     * @param assetTypes a <code>List</code> of asset categories.
     * @param duration the length of time in milliseconds to retrieve in the past starting from the present time
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "averageValueToMaturity": {
     *             "30": integer,
     *             "60": integer,
     *             "90": integer,
     *             "120": integer
     *         },
     *         "transactions": {
     *             "total": integer,
     *             "pages": integer,
     *             "results": [{
     *                 "transferDate": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *                 "cost": String (float)
     *             }]
     *         },
     *         "bids": [{
     *             "rate": decimal,
     *             "time": long (ms since epoch)
     *         }],
     *         "asks": [{
     *             "rate": decimal,
     *             "time": long (ms since epoch)
     *         }]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void getMarketVolume(List<String> assetTypes, long duration, Response.Listener<JSONObject> successListener,
                         Response.ErrorListener errorListener);

    /**
     * Request the current market value for specified assets.
     *
     * @param assetTypes a <code>List</code> of asset categories.
     * @param duration the length of time in milliseconds to retrieve in the past starting from the present time
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "averageValueToMaturity": {
     *             "30": integer,
     *             "60": integer,
     *             "90": integer,
     *             "120": integer
     *         },
     *         "transactions": {
     *             "total": integer,
     *             "pages": integer,
     *             "results": [{
     *                 "transferDate": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *                 "cost": String (float)
     *             }]
     *         },
     *         "bids": [{
     *             "rate": decimal,
     *             "time": long (ms since epoch)
     *         }],
     *         "asks": [{
     *             "rate": decimal,
     *             "time": long (ms since epoch)
     *         }]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void getMarketValue(List<String> assetTypes, long duration, Response.Listener<JSONObject> successListener,
                        Response.ErrorListener errorListener);

}
