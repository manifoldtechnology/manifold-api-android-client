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

import com.manifoldtechnology.manifold_api_android_client.api.ManifoldApiResponseHandler;

import org.json.JSONObject;

/**
 * Provides a handler for the <code>JSONObject</code> responses associated with the <code>TrendsApi</code>.
 */
public interface TrendsApiResponseHandler extends ManifoldApiResponseHandler {

    /**
     * Receives a <code>JSONObject</code> response and handles it as needed in the view.
     *
     * @param response A <code>JSONObject</code> in the following format:
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
     */
    void handleMarketVolumeResponse(JSONObject response);

    /**
     * Receives a <code>JSONObject</code> response and handles it as needed in the view.
     *
     * @param response a <code>JSONObject</code> in the following format:
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
     */
    void handleMarketValueResponse(JSONObject response);

    /**
     * Receives a <code>JSONObject</code> response and handles it as needed in the view.
     *
     * @param response a <code>JSONObject</code> in the following format:
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
     */
    void handleInterestRatesResponse(JSONObject response);
}
