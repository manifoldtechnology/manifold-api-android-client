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
import com.manifoldtechnology.manifold_api_android_client.api.history.HistoryApi;
import com.manifoldtechnology.manifold_api_android_client.api.history.HistoryApiImpl;
import com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.defaultErrorListener;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getAsyncResponseReceived;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getAsyncTestSuccessful;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getContext;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getManifoldApiConnector;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.handleException;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.waitForAsyncResponse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class HistoryApiTest {

    @ClassRule
    public static SignUpTestRule apiTestRule = new SignUpTestRule();

    private HistoryApi historyApi;
    private DateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");

    @Before
    public void beforeTest(){
        historyApi = new HistoryApiImpl(getContext(), getManifoldApiConnector());
    }

    @Test
    public void testGetHistory() throws InterruptedException {
        Date now = new Date();
        historyApi.getHistory("Broker", "Security", new Date(now.getTime() - (86400 * 2 * 1000)), now, 1, 50,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", "getHistory: " + response.toString());

                        try {
                            for (String attribute : Arrays.asList("total", "pages", "results")) {
                                assertTrue(response.has(attribute));
                            }

                            if (response.getJSONArray("results").length() > 0) {
                                for (String attribute : Arrays.asList("submitted", "accepted", "from", "to", "asset")) {
                                    assertTrue(response.getJSONArray("results").getJSONObject(0).has(attribute));
                                }

                                JSONObject objectZero = response.getJSONArray("results").getJSONObject(0);

                                for (String attribute : Arrays.asList("id", "type", "name", "entityId", "accountId", "userId")) {
                                    if (!objectZero.isNull("to")) {
                                        assertTrue(objectZero.getJSONObject("to").has(attribute));
                                    }
                                    if (!objectZero.isNull("from")) {
                                        assertTrue(objectZero.getJSONObject("from").has(attribute));
                                    }
                                }

                                for (String attribute : Arrays.asList("id", "type", "ticker", "amount", "issuer", "metadata")) {
                                    assertTrue(objectZero.getJSONObject("asset").has(attribute));
                                }
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
