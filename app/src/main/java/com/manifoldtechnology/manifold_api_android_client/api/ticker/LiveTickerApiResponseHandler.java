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

import com.manifoldtechnology.manifold_api_android_client.api.ManifoldApiResponseHandler;

import org.json.JSONObject;

/**
 * Provides a handler for the <code>JSONObject</code> responses associated with the <code>LiveTickerApi</code>.
 */
public interface LiveTickerApiResponseHandler extends ManifoldApiResponseHandler {

    /**
     * Receives a <code>JSONObject</code> response and handles it as needed in the view.
     *
     * @param response
     * <pre>{@code
     *
     *     {
     *         "array": [{
     *             "symbol": String,
     *             "price": String (decimal),
     *             "change": String (decimal)
     *         }]
     *     }
     *
     * }</pre>
     */
    void handleLiveTickerResponse(JSONObject response);
}