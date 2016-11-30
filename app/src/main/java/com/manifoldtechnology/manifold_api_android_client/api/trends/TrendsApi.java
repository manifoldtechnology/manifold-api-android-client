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

import java.util.List;

/**
 * Provides trend data including current market interest rates for assets, market volume, and market value.
 */
public interface TrendsApi {

    /**
     * Request the current interest rates for bids and asks for specified assets.
     *
     * @param assetTypes a <code>List</code> of asset categories.
     */
    void getInterestRates(List<String> assetTypes);

    /**
     * Request the current market volume for specified assets.
     *
     * @param assetTypes a <code>List</code> of asset categories.
     * @param duration the length of time in milliseconds to retrieve in the past starting from the present time
     */
    void getMarketVolume(List<String> assetTypes, long duration);

    /**
     * Request the current market value for specified assets.
     *
     * @param assetTypes a <code>List</code> of asset categories.
     * @param duration the length of time in milliseconds to retrieve in the past starting from the present time
     */
    void getMarketValue(List<String> assetTypes, long duration);

    /**
     * Get the listener that handles the request response.
     * @return
     */
    TrendsApiResponseHandler getTrendsApiResponseHandler();
}
