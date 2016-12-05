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

import com.android.volley.Response;
import com.manifoldtechnology.manifold_api_android_client.api.assets.AssetsApi;
import com.manifoldtechnology.manifold_api_android_client.api.assets.AssetsApiImpl;
import com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.defaultErrorListener;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getAsyncResponseReceived;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getAsyncTestSuccessful;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getContext;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getManifoldApiConnector;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getRoleId;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.handleException;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.waitForAsyncResponse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AssetsApiTest {

    @ClassRule
    public static SignUpTestRule apiTestRule = new SignUpTestRule();

    private AssetsApi assetsApi;

    @Before
    public void before(){
        assetsApi = new AssetsApiImpl(getContext(), getManifoldApiConnector());
    }

    @Test
    public void testGetAssets() throws InterruptedException {
        assetsApi.getAssets(getRoleId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "getAssets: " + response.toString());

                try {
                    for(String attribute : Arrays.asList("total", "pages", "results")){
                        assertTrue(response.has(attribute));
                    }
                    assertTrue(response.getJSONArray("results").length() >= 1);

                    for(String attribute : Arrays.asList("id", "type", "ticker", "amount", "issuer", "metadata")){
                        assertTrue(response.getJSONArray("results").getJSONObject(0).has(attribute));
                    }

                    getAsyncTestSuccessful().set(true);
                } catch (JSONException e) {
                    handleException(e);
                }

                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }
}
