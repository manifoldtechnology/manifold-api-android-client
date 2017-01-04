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
import com.manifoldtechnology.manifold_api_android_client.api.events.EventsApi;
import com.manifoldtechnology.manifold_api_android_client.api.events.EventsApiImpl;
import com.manifoldtechnology.manifold_api_android_client.rules.BlockchainTestRule;
import com.manifoldtechnology.manifold_api_android_client.rules.SignUpTestRule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.manifoldtechnology.manifold_api_android_client.rules.BlockchainTestRule.getEventId;
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
public class EventsApiTest {

    /**
     * Defines the ordering of <code>TestRule</code>s, which is similar to @BeforeClass, but the preferred way
     * to share functionality in JUnit.
     */
    @ClassRule
    public static TestRule chain = RuleChain
            .outerRule(new SignUpTestRule())
            .around(new BlockchainTestRule());

    private EventsApi eventsApi;

    @Before
    public void before(){
        eventsApi = new EventsApiImpl(getContext(), getManifoldApiConnector());
    }

    @Test
    public void testGetEventTypes() throws InterruptedException {
        eventsApi.getEventTypes(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "getEventTypes: " + response.toString());

                assertTrue(response.has("array"));

                List<String> items = Arrays.asList("addUsersToGroup", "attachRole", "bindOffer", "buyAsset",
                        "cancelAsk", "cancelBid", "changePassword", "completeContract", "completeOffer",
                        "createAccount", "createAsk", "createAsset", "createBid", "createEntity", "createGroup",
                        "createLink", "createNotification", "createOffer", "createPermission", "createRole",
                        "createStock","createUser", "deactivateUser", "deleteAccount", "deleteAllNotifications",
                        "deleteAsset", "deleteEntity", "deleteGroup", "deleteLink", "deleteNotification",
                        "deletePermission", "deleteUser", "grantAccess", "offerAsset", "reactivateUser",
                        "removeUsersFromGroup", "retractAsset", "revokeAccess", "signupRole", "transfer",
                        "updateAccount", "updateAsset", "updateEntity", "updateGroup", "updatePrice",
                        "updateRole", "updateUser");

                try {
                    JSONArray array = response.getJSONArray("array");

                    List<String> received = new ArrayList<>();

                    for(int i = 0; i < array.length(); i++){
                        received.add(array.getString(i));
                    }

                    assertTrue(items.equals(received));
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
    public void testGetEventSummary() throws InterruptedException {
        eventsApi.getEventSummary(getRoleId(), getEventId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "getEventSummary: " + response.toString());

                try{
                    for(String attribute : Arrays.asList("id", "ownerId", "userId", "type", "data", "created", "hash", "blocks")){
                        assertTrue(response.has(attribute));
                    }

                    assertTrue(response.getJSONArray("blocks").length() > 0);
                    for(String attribute : Arrays.asList("ownerId", "chainId", "hash")){
                        assertTrue(response.getJSONArray("blocks").getJSONObject(0).has(attribute));
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
    public void testGetAllEventsByOwner() throws InterruptedException {
        eventsApi.getAllEventsByOwner(getRoleId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "getAllEventsByOwner: " + response.toString());

                try{
                    for(String attribute : Arrays.asList("total", "pages", "results")){
                        assertTrue(response.has(attribute));
                    }

                    assertTrue(response.getJSONArray("results").length() > 0);
                    JSONObject result = response.getJSONArray("results").getJSONObject(0);

                    for(String attribute : Arrays.asList("id", "ownerId", "userId", "type", "data", "created", "hash")){
                        assertTrue(result.has(attribute));
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
    public void testSubmitEventRequest() throws InterruptedException, JSONException {
        JSONObject body = new JSONObject();
        body.put("type", "testEvent");
        body.put("data", new JSONObject());
        body.getJSONObject("data").put("time", new Date().getTime());

        eventsApi.submitEventRequest(getRoleId(), body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "submitEventRequest: " + response.toString());

                assertTrue(response.has("result"));

                getAsyncResponseReceived().set(true);
                getAsyncTestSuccessful().set(true);
            }
        }, defaultErrorListener());

        waitForAsyncResponse();
    }

    @Test
    public void testGetAllEventsByObjectId() throws InterruptedException, JSONException {
        testSubmitEventRequest();

        Date begin = new Date(new Date().getTime() - 86400 * 2 * 1000);
        Date end = new Date();

        eventsApi.getAllEventsByObjectId(getRoleId(), UUID.randomUUID().toString(), null, begin, end, 1, 50,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "getAllEventsByObjectId: " + response.toString());

                try{
                    for(String attribute : Arrays.asList("total", "pages", "results")){
                        assertTrue(response.has(attribute));
                    }

                    if (response.getJSONArray("results").length() > 0) {
                        JSONObject result = response.getJSONArray("results").getJSONObject(0);

                        for (String attribute : Arrays.asList("id", "ownerId", "userId", "type", "data", "created", "hash")) {
                            assertTrue(result.has(attribute));
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
