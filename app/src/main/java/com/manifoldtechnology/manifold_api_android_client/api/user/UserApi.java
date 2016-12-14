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

import com.android.volley.Response;
import com.android.volley.ServerError;
import com.manifoldtechnology.manifold_api_android_client.domain.Role;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Provides Sign Up and Log In capabilities as well as profile and role data.
 */
public interface UserApi {

    /**
     * Creates a new user account.
     *
     * @param role the type of user account
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email address; will be used as the username
     * @param organization the user's organization
     * @param password the user's password
     * @param phone the user's phone number
     * @param eulaAccepted has the user accepted the license agreement?
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "id": String (UUID),
     *         "type": String,
     *         "name": String,
     *         "entityId": String (UUID),
     *         "accountId": String (UUID),
     *         "userId": String (UUID)
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws ServerError
     */
    void signUp(Role role, String firstName, String lastName, String email, String organization,
                String password, String phone, boolean eulaAccepted, Response.Listener<JSONObject> successListener,
                Response.ErrorListener errorListener)
            throws InterruptedException, ExecutionException, TimeoutException, ServerError;

    /**
     * Requests a user's profile.
     *
     * @param username
     * @param password
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "id": String (UUID),
     *         "username": String,
     *         "email": String,
     *         "firstName": String,
     *         "lastName": String,
     *         "organization": String,
     *         "phone": String,
     *         "admin": boolean,
     *         "status": String,
     *         "eulaAccepted": boolean
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    void requestProfile(String username, String password, Response.Listener<JSONObject> successListener,
                        Response.ErrorListener errorListener)
            throws InterruptedException, ExecutionException, TimeoutException;

    /**
     * Request a user's role.
     *
     * @param username
     * @param password
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "id": String (UUID),
     *         "type": String,
     *         "name": String,
     *         "entityId": String (UUID),
     *         "accountId": String (UUID),
     *         "userId": String (UUID)
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    void requestRole(String username, String password, Response.Listener<JSONObject> successListener,
                     Response.ErrorListener errorListener)
            throws InterruptedException, ExecutionException, TimeoutException;

    /**
     * Delete a user that has been created with the signUp method.
     *
     * @param username
     * @param password
     * @param userId a UUID received from the requestRole method
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "result": String (UUID), event ID
     *     }
     *
     * }</pre>
     * @param errorListener handles a possible exception during the request
     */
    void deleteUser(String username, String password, String userId, Response.Listener<JSONObject> successListener,
                    Response.ErrorListener errorListener);
}

