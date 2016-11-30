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

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.ServerError;
import com.manifoldtechnology.manifold_api_android_client.api.user.UserApi;
import com.manifoldtechnology.manifold_api_android_client.api.user.UserApiImpl;
import com.manifoldtechnology.manifold_api_android_client.api.user.UserApiResponseHandler;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;
import com.manifoldtechnology.manifold_api_android_client.domain.Role;
import com.manifoldtechnology.manifold_api_android_client.service.Utilities;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.io.IOException;
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
public class ApiTest implements UserApiResponseHandler {

    private UserApi userApi;

    private Properties properties;
    private Context context;

    private Role role = Role.TRADER;
    private String firstName = "testFirst";
    private String lastName = "testLast";
    private String email;
    private String organization = "testOrganization";
    private String password = "password";
    private String phone = "(111) 111-1111";
    private boolean eulaAccepted = true;
    private String roleId;

    private ManifoldApiConnector connector;
    private DateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
    private AtomicBoolean asyncTestSuccessful = new AtomicBoolean();
    private AtomicBoolean asyncResponseReceived = new AtomicBoolean();

    @Before
    public void setup() throws IOException, InterruptedException, ExecutionException, TimeoutException, ServerError, JSONException {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        properties = Utilities.getProperties(context);
        String protocol = properties.get("protocol").toString();
        String host = properties.get("host").toString();
        String port = properties.get("port").toString();
        String path = properties.get("path").toString();
        String version = properties.get("version").toString();

        Log.d(this.getClass().getName(), "URL: " + host + ":" + port + "/" + path + "/" + version);
        Log.d(this.getClass().getName(), "email: " + email);

        connector = new ManifoldApiConnector(protocol, host, port, path, version, null, null);
        userApi = new UserApiImpl(context, connector, this);

        String timeModifier = sdf.format(new Date());
        email = "first.last." + timeModifier + "@gmail.com";

        createUser();

        asyncResponseReceived.set(false);
        asyncTestSuccessful.set(false);
    }

    @After
    public void after(){
        //userApi.deleteUser();
    }

    protected void createUser()
            throws InterruptedException, ExecutionException, TimeoutException, ServerError, JSONException {

        userApi.signUp(role, firstName, lastName, email, organization, password, phone, eulaAccepted);
        waitForAsyncResponse();
    }

    protected void waitForAsyncResponse() throws InterruptedException {
        while(!asyncResponseReceived.get()){
            Thread.sleep(10);
        }

        assertTrue(asyncTestSuccessful.get());
    }

    @Override
    public void handleException(Exception e) {
        e.printStackTrace();

        asyncTestSuccessful.set(false);
        asyncResponseReceived.set(true);
    }

    public Context getContext(){
        return this.context;
    }

    public ManifoldApiConnector getManifoldApiConnector(){
        return connector;
    }

    public String getRoleId(){
        return roleId.toString();
    }

    public AtomicBoolean getAsyncTestSuccessful() {
        return asyncTestSuccessful;
    }

    public AtomicBoolean getAsyncResponseReceived() {
        return asyncResponseReceived;
    }

    @Override
    public void handleSignUpResponse(JSONObject response) {
        Log.d(this.getClass().getName(), "signUpResult: " + response.toString());
        try {
            connector = new ManifoldApiConnector(connector.getProtocol(), connector.getHost(), connector.getPort(),
                    connector.getPath(), connector.getVersion(), email, password);

            userApi.requestProfile(email, password);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            handleException(e);
        }
    }

    @Override
    public void handleRequestProfileResponse(JSONObject response) {
        Log.d(this.getClass().getName(), "profileResult: " + response.toString());
        try {
            userApi.requestRole(email, password);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            handleException(e);
        }
    }

    @Override
    public void handleRequestRoleResponse(JSONObject response) {
        Log.d(this.getClass().getName(), "roleResult: " + response.toString());
        try {
            roleId = response.getString("id");
            asyncResponseReceived.set(true);
            asyncTestSuccessful.set(true);
        } catch (JSONException e) {
            handleException(e);
        }
    }
}
