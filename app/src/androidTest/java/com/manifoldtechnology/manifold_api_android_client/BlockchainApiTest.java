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
import com.manifoldtechnology.manifold_api_android_client.api.blockchain.BlockchainApi;
import com.manifoldtechnology.manifold_api_android_client.api.blockchain.BlockchainApiImpl;
import com.manifoldtechnology.manifold_api_android_client.rules.BlockchainTestRule;
import com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static com.manifoldtechnology.manifold_api_android_client.rules.BlockchainTestRule.getBlockHash;
import static com.manifoldtechnology.manifold_api_android_client.rules.BlockchainTestRule.getChainId;
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
public class BlockchainApiTest {

    /**
     * Defines the ordering of <code>TestRule</code>s, which is similar to @BeforeClass, but the preferred way
     * to share functionality in JUnit.
     */
    @ClassRule
    public static TestRule chain = RuleChain
            .outerRule(new SignUpTestRule())
            .around(new BlockchainTestRule());

    private BlockchainApi blockchainApi;

    @Before
    public void beforeTest(){
        blockchainApi = new BlockchainApiImpl(getContext(), getManifoldApiConnector());
    }

    @Test
    public void testGetAllChains() throws InterruptedException {
        blockchainApi.getAllChains(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleGetAllChains: " + response.toString());

                try {
                    assertTrue(response.has("array"));

                    if (response.getJSONArray("array").length() > 0) {
                        for(String attribute : Arrays.asList("ownerId", "chainId", "lastHash", "blockCount", "eventCount")){
                            assertTrue(response.getJSONArray("array").getJSONObject(0).has(attribute));
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

    @Test
    public void testGetChainsByOwner() throws InterruptedException {
        blockchainApi.getChainsByOwner(getRoleId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleGetChainsByOwner: " + response.toString());

                try {
                    assertTrue(response.has("array"));

                    if (response.getJSONArray("array").length() > 0) {
                        for(String attribute : Arrays.asList("ownerId", "chainId", "lastHash", "blockCount", "eventCount")){
                            assertTrue(response.getJSONArray("array").getJSONObject(0).has(attribute));
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

    @Test
    public void testFindBlockByHash() throws InterruptedException {
        blockchainApi.findBlockByHash(getRoleId(), getBlockHash(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindBlockByHash: " + response.toString());

                for(String attribute : Arrays.asList("ownerId", "chainId", "hash", "previousHash", "created", "events")){
                    assertTrue(response.has(attribute));
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testFindBlockByBlockchain() throws InterruptedException {
        blockchainApi.findBlockByBlockchain(getRoleId(), getChainId(), getBlockHash(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindBlockByBlockchain: " + response.toString());

                for(String attribute : Arrays.asList("ownerId", "chainId", "hash", "previousHash", "created", "events")){
                    assertTrue(response.has(attribute));
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testFindBlockSummaryInPrimaryChain() throws InterruptedException {
        blockchainApi.findBlockSummaryInPrimaryChain(getRoleId(), getBlockHash(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindBlockSummaryInPrimaryChain: " + response.toString());

                try {
                    for (String attribute : Arrays.asList("ownerId", "chainId", "hash", "previousHash", "created", "events")) {
                        assertTrue(response.has(attribute));
                    }

                    if (response.getJSONArray("events").length() > 0) {
                        for (String attribute : Arrays.asList("id", "ownerId", "userId", "type", "data", "created", "hash")) {
                            assertTrue(response.getJSONArray("events").getJSONObject(0).has(attribute));
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

    @Test
    public void testFindBlockSummaryInSpecificChain() throws InterruptedException {
        blockchainApi.findBlockSummaryInSpecificChain(getRoleId(), getChainId(), getBlockHash(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindBlockSummaryInSpecificChain: " + response.toString());

                try {
                    for (String attribute : Arrays.asList("ownerId", "chainId", "hash", "previousHash", "created", "events")) {
                        assertTrue(response.has(attribute));
                    }

                    if (response.getJSONArray("events").length() > 0) {
                        for (String attribute : Arrays.asList("id", "ownerId", "userId", "type", "data", "created", "hash")) {
                            assertTrue(response.getJSONArray("events").getJSONObject(0).has(attribute));
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

    @Test
    public void testFindPrimaryBlockchainByOwner() throws InterruptedException {
        blockchainApi.findPrimaryBlockchainByOwner(getRoleId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindPrimaryBlockchainByOwner: " + response.toString());

                for(String attribute : Arrays.asList("ownerId", "chainId", "lastHash", "blockCount", "eventCount")){
                    assertTrue(response.has(attribute));
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testFindBlockchainByOwner() throws InterruptedException {
        blockchainApi.findBlockchainByOwner(getRoleId(), getChainId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindBlockchainByOwner: " + response.toString());

                for(String attribute : Arrays.asList("ownerId", "chainId", "lastHash", "blockCount", "eventCount")){
                    assertTrue(response.has(attribute));
                }
                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testFindLastBlockByOwner() throws InterruptedException {
        blockchainApi.findLastBlockByOwner(getRoleId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindLastBlockByOwner: " + response.toString());

                for(String attribute : Arrays.asList("chainId", "created", "events", "hash", "ownerId", "previousHash")){
                    assertTrue(response.has(attribute));
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testFindLastBlockByOwnerAndChain() throws InterruptedException {
        blockchainApi.findLastBlockByOwnerAndChain(getRoleId(), getChainId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindLastBlockByOwnerAndChain: " + response.toString());

                for(String attribute : Arrays.asList("chainId", "created", "events", "hash", "ownerId", "previousHash")){
                    assertTrue(response.has(attribute));
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testFindLastBlockSummaryByOwner() throws InterruptedException {
        blockchainApi.findLastBlockSummaryByOwner(getRoleId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindLastBlockSummaryByOwner: " + response.toString());

                try {
                    for (String attribute : Arrays.asList("ownerId", "chainId", "hash", "previousHash", "created", "events")) {
                        assertTrue(response.has(attribute));
                    }

                    if (response.getJSONArray("events").length() > 0) {
                        for (String attribute : Arrays.asList("id", "ownerId", "userId", "type", "data", "created", "hash")) {
                            assertTrue(response.getJSONArray("events").getJSONObject(0).has(attribute));
                        }
                    }
                    getAsyncTestSuccessful().set(true);
                } catch (JSONException e) {
                    handleException(e);
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testFindLastBlockSummaryByOwnerAndChain() throws InterruptedException {
        blockchainApi.findLastBlockSummaryByOwnerAndChain(getRoleId(), getChainId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleFindLastBlockSummaryByOwnerAndChain: " + response.toString());

                try {
                    for (String attribute : Arrays.asList("ownerId", "chainId", "hash", "previousHash", "created", "events")) {
                        assertTrue(response.has(attribute));
                    }

                    if (response.getJSONArray("events").length() > 0) {
                        for (String attribute : Arrays.asList("id", "ownerId", "userId", "type", "data", "created", "hash")) {
                            assertTrue(response.getJSONArray("events").getJSONObject(0).has(attribute));
                        }
                    }
                    getAsyncTestSuccessful().set(true);
                } catch (JSONException e) {
                    handleException(e);
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testValidateBlockchainByOwner() throws InterruptedException {
        blockchainApi.validateBlockchainByOwner(getRoleId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleValidateBlockchainByOwner: " + response.toString());

                for(String attribute : Arrays.asList("ownerId", "chainId", "lastHash", "blockCount", "eventCount")){
                    assertTrue(response.has(attribute));
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }

    @Test
    public void testValidateBlockchainByOwnerAndChain() throws InterruptedException {
        blockchainApi.validateBlockchainByOwnerAndChain(getRoleId(), getChainId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "handleValidateBlockchainByOwnerAndChain: " + response.toString());

                for(String attribute : Arrays.asList("ownerId", "chainId", "lastHash", "blockCount", "eventCount")){
                    assertTrue(response.has(attribute));
                }

                getAsyncTestSuccessful().set(true);
                getAsyncResponseReceived().set(true);
            }
        }, defaultErrorListener());
        waitForAsyncResponse();
    }
}
