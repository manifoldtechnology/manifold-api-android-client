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

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Provides the ability to sell assets.
 */
public interface SellApi {

    /**
     * Submits an ask to sell an asset with the specified parameters.
     *
     * @param bidType the type of asset (Security, Short-term Loan, Mortgage, etc)
     * @param bidRate the percentage of interest (e.g. eight percent would be bidrate = 8.0f, not 0.08f)
     * @param futureValue the future value of the asset after it has matured
     * @param daysToMaturity the number of days until the asset has matured
     * @param par the face value of a bond (or other asset)
     * @param ticker a label or abbreviation for the asset
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *     {
     *         "asset": {
     *             "id": String (UUID),
     *             "type": String,
     *             "ticker": String
     *             "amount": Integer,
     *             "issuer": String,
     *             "metadata": {
     *                 "par": String (integer),
     *                 "discountRate": String (decimal),
     *                 "cost": String (integer),
     *                 "daysToMaturity": String (integer),
     *                 "requestDate": String (long ms since epoch),
     *                 "futureValue": String (integer)
     *             }
     *         },
     *         "holder": String,
     *         "approved": boolean,
     *     }
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void sell(String bidType, float bidRate, long futureValue, int daysToMaturity, float par, String ticker,
              Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException;

}
