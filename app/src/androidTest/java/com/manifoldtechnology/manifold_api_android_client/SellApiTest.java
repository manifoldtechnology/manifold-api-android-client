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

package com.manifoldtechnology.manifold_api_android_client;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.manifoldtechnology.manifold_api_android_client.api.assets.SellApi;
import com.manifoldtechnology.manifold_api_android_client.api.assets.SellApiImpl;
import com.manifoldtechnology.manifold_api_android_client.api.assets.SellApiResponseHandler;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SellApiTest extends ApiTest implements SellApiResponseHandler {

    private SellApi sellApi;

    @Before
    public void beforeTest(){
        sellApi = new SellApiImpl(getContext(), this, getManifoldApiConnector());
    }

    @Test
    public void testSell() throws InterruptedException {
        sellApi.sell("Security", 8.24f, 30000L, 30, 1000, "USD");
        waitForAsyncResponse();
    }

    @Override
    public void handleSellResponse(JSONObject response) {
        Log.d("handleSellResponse", response.toString());

        getAsyncTestSuccessful().set(true);
        getAsyncResponseReceived().set(true);
    }
}
