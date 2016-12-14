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
import com.manifoldtechnology.manifold_api_android_client.api.AbstractApi;
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

/**
 * Default implementation of the <code>UserApi</code> using Volley for HTTP requests.
 */
public class UserApiImpl extends AbstractApi implements UserApi {

    public UserApiImpl(Context context, ManifoldApiConnector connector){
        super(context, connector);
    }

    @Override
    public void signUp(Role role, String firstName, String lastName, String email, String organization,
                       String password, String phone, boolean eulaAccepted,
                       Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener)
            throws InterruptedException, ExecutionException, TimeoutException, ServerError {

        String url = Utilities.getUrlRoot(getConnector())
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
                successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void requestProfile(String username, String password, Response.Listener<JSONObject> successListener,
                               Response.ErrorListener errorListener)
            throws InterruptedException, ExecutionException, TimeoutException {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath("profile")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                username, password, null, JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void requestRole(String username, String password, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener)
            throws InterruptedException, ExecutionException, TimeoutException {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath("role")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                username, password, null, JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void deleteUser(String username, String password, String userId, Response.Listener<JSONObject> successListener,
                           Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath("users")
                .appendPath(userId)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.DELETE, url,
                username, password, null, JsonObjectRequestBasicAuth.Type.STRING, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

}
