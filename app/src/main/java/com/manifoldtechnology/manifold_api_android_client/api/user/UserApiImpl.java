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

package com.manifoldtechnology.manifold_api_android_client.api.user;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;
import com.manifoldtechnology.manifold_api_android_client.domain.Role;
import com.manifoldtechnology.manifold_api_android_client.service.Utilities;
import com.manifoldtechnology.manifold_api_android_client.service.request.JsonObjectRequestBasicAuth;
import com.manifoldtechnology.manifold_api_android_client.service.request.RequestQueueSingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class UserApiImpl implements UserApi {

    private Context context;
    private ManifoldApiConnector connector;
    private UserApiResponseHandler responseHandler;

    public UserApiImpl(Context context, ManifoldApiConnector connector, UserApiResponseHandler responseHandler){

        this.connector = connector;
        this.context = context;
        this.responseHandler = responseHandler;
    }

    @Override
    public void signUp(Role role, String firstName, String lastName, String email, String organization,
                       String password, String phone, boolean eulaAccepted)
            throws InterruptedException, ExecutionException, TimeoutException, ServerError {

        String url = Utilities.getUrlRoot(connector)
                .appendPath("roles")
                .appendPath("signup")
                .build().toString();

        Map<String, Object> params = new HashMap<>();
        params.put("type", role.toString());
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("username", email);
        params.put("email", email);
        params.put("organization", organization);
        params.put("phone", phone);
        params.put("eulaAccepted", eulaAccepted);
        params.put("password", password);
        params.put("initialAmount", 50000);

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.POST, url,
                null, null, new JSONObject(params), JsonObjectRequestBasicAuth.Type.JSON_OBJECT,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseHandler.handleSignUpResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseHandler.handleException(new Exception(Utilities.unpackVolleyError(error, context), error));
                    }
                }
        );

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void requestProfile(String username, String password)
            throws InterruptedException, ExecutionException, TimeoutException {

        String url = Utilities.getUrlRoot(connector)
                .appendPath("profile")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                username, password, null, JsonObjectRequestBasicAuth.Type.JSON_OBJECT,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseHandler.handleRequestProfileResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseHandler.handleException(new Exception(Utilities.unpackVolleyError(error, context), error));
                    }
                }
        );

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void requestRole(String username, String password)
            throws InterruptedException, ExecutionException, TimeoutException {

        String url = Utilities.getUrlRoot(connector)
                .appendPath("role")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                username, password, null, JsonObjectRequestBasicAuth.Type.JSON_OBJECT,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseHandler.handleRequestRoleResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseHandler.handleException(new Exception(Utilities.unpackVolleyError(error, context), error));
                    }
                }
        );

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public UserApiResponseHandler getUserApiResponseHandler() {
        return responseHandler;
    }

}
