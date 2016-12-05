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

package com.manifoldtechnology.manifold_api_android_client.rules;

import android.util.Log;

import com.android.volley.Response;
import com.manifoldtechnology.manifold_api_android_client.api.blockchain.BlockchainApi;
import com.manifoldtechnology.manifold_api_android_client.api.blockchain.BlockchainApiImpl;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.defaultErrorListener;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getAsyncResponseReceived;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getAsyncTestSuccessful;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getContext;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getManifoldApiConnector;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.getRoleId;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.handleException;
import static com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule.waitForAsyncResponse;
import static junit.framework.Assert.assertTrue;

public class BlockchainTestRule implements TestRule {

    private static BlockchainApi blockchainApiFixture;
    private static String blockHash;
    private static String chainId;
    private static String eventId;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                try{
                    blockchainApiFixture = new BlockchainApiImpl(getContext(), getManifoldApiConnector());

                    getAsyncResponseReceived().set(false);
                    getAsyncTestSuccessful().set(false);

                    blockchainApiFixture.getAllChains(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response", "handleGetAllChains: " + response.toString());

                            try {
                                assertTrue(response.has("array"));

                                if (response.getJSONArray("array").length() > 0) {
                                    blockHash = response.getJSONArray("array").getJSONObject(0).getString("lastHash");
                                    chainId = response.getJSONArray("array").getJSONObject(0).getString("chainId");
                                }
                                getAsyncTestSuccessful().set(true);
                            } catch (JSONException e) {
                                handleException(e);
                            }
                            getAsyncResponseReceived().set(true);
                        }
                    }, defaultErrorListener());

                    waitForAsyncResponse();

                    blockchainApiFixture.findLastBlockByOwner(getRoleId(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response", "findLastBlockByOwner: " + response.toString());
                            try {
                                if (response.getJSONArray("events").length() > 0) {
                                    eventId = response.getJSONArray("events").getString(0);
                                }
                                getAsyncTestSuccessful().set(true);
                            } catch (JSONException e) {
                                handleException(e);
                            }
                            getAsyncResponseReceived().set(true);
                        }
                    }, defaultErrorListener());

                    waitForAsyncResponse();

                    base.evaluate();

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                } finally {
                    // After
                }
            }
        };
    }

    public static String getBlockHash(){
        return blockHash;
    }

    public static String getChainId(){
        return chainId;
    }

    public static String getEventId(){
        return eventId;
    }
}
