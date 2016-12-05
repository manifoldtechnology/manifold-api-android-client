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

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.manifoldtechnology.manifold_api_android_client.api.user.UserApi;
import com.manifoldtechnology.manifold_api_android_client.api.user.UserApiImpl;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;
import com.manifoldtechnology.manifold_api_android_client.domain.Role;
import com.manifoldtechnology.manifold_api_android_client.service.Utilities;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@Ignore
public class SignUpTestRule implements TestRule {

    private static UserApi userApi;

    private static Properties properties;
    private static Context context;

    private static Role role = Role.TRADER;
    private static String firstName = "testFirst";
    private static String lastName = "testLast";
    private static String email;
    private static String organization = "testOrganization";
    private static String password = "password";
    private static String phone = "(111) 111-1111";
    private static boolean eulaAccepted = true;
    private static String roleId;

    private static ManifoldApiConnector connector;
    private static DateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
    private static AtomicBoolean asyncTestSuccessful = new AtomicBoolean();
    private static AtomicBoolean asyncResponseReceived = new AtomicBoolean();

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                // Before

                context = InstrumentationRegistry.getInstrumentation().getTargetContext();

                properties = Utilities.getProperties(context);
                String protocol = properties.get("protocol").toString();
                String host = properties.get("host").toString();
                String port = properties.get("port").toString();
                String path = properties.get("path").toString();
                String version = properties.get("version").toString();

                connector = new ManifoldApiConnector(protocol, host, port, path, version, null, null);
                userApi = new UserApiImpl(context, connector);

                String timeModifier = sdf.format(new Date());
                email = "first.last." + timeModifier + "@gmail.com";

                userApi.signUp(role, firstName, lastName, email, organization, password, phone, eulaAccepted,
                        signUpResponseListener(), defaultErrorListener());
                waitForAsyncResponse();

                try{
                    base.evaluate();
                } finally{
                    getAsyncResponseReceived().set(false);
                    getAsyncTestSuccessful().set(false);
                    // After
                    // userApi.deleteUser();
                }
            }
        };
    }

    private static Response.Listener<JSONObject> signUpResponseListener(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "signUp: " + response.toString());
                try {
                    connector = new ManifoldApiConnector(connector.getProtocol(), connector.getHost(),
                            connector.getPort(), connector.getPath(), connector.getVersion(), email, password);

                    userApi.requestProfile(email, password, requestProfileResponseListener(), defaultErrorListener());
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private static Response.Listener<JSONObject> requestProfileResponseListener(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "requestProfile: " + response.toString());
                try {
                    userApi.requestRole(email, password, requestRoleResponseListener(), defaultErrorListener());
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    handleException(e);
                }
            }
        };
    }

    private static Response.Listener<JSONObject> requestRoleResponseListener(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", "requestRole: " + response.toString());
                try {
                    roleId = response.getString("id");
                    asyncResponseReceived.set(true);
                    asyncTestSuccessful.set(true);
                } catch (JSONException e) {
                    handleException(e);
                }
            }
        };
    }

    public static Response.ErrorListener defaultErrorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleException(new Exception(Utilities.unpackVolleyError(error, getContext()), error));
            }
        };
    }

    public static void waitForAsyncResponse() throws InterruptedException {
        while(!asyncResponseReceived.get()){
            Thread.sleep(10);
        }

        assertTrue(asyncTestSuccessful.get());

        getAsyncResponseReceived().set(false);
        getAsyncTestSuccessful().set(false);
    }

    public static void handleException(Exception e) {
        e.printStackTrace();

        asyncTestSuccessful.set(false);
        asyncResponseReceived.set(true);
    }

    public static Context getContext(){
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    public static ManifoldApiConnector getManifoldApiConnector(){
        return connector;
    }

    public static String getRoleId(){
        return roleId.toString();
    }

    public static AtomicBoolean getAsyncTestSuccessful() {
        return asyncTestSuccessful;
    }

    public static AtomicBoolean getAsyncResponseReceived() {
        return asyncResponseReceived;
    }

}
