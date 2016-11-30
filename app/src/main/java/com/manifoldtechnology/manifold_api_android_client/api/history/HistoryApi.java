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

import java.util.Date;

/**
 * Provides the historical transactions related to a particular broker.
 */
public interface HistoryApi {

    /**
     * Request the transactional history for the given parameters.
     *
     * @param brokerName the name of the broker, typically "Broker"
     * @param typeFilter the label of the asset
     * @param begin the date of the beginning of the request window; chronologically older than <code>end</code>
     * @param end the date of the beginning of the request window; chronologically younger than <code>begin</code>
     * @param page the page desired for pagination beginning at page 1
     * @param size the number of elements to return per page
     */
    void getHistory(String brokerName, String typeFilter, Date begin, Date end, int page, int size);

    /**
     * Get the listener that handles the request response.
     *
     * @return
     */
    HistoryApiResponseHandler getHistoryApiResponseHandler();
}
