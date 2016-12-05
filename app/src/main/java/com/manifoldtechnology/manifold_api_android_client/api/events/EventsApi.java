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

package com.manifoldtechnology.manifold_api_android_client.api.events;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Date;

public interface EventsApi {

    /**
     * Retrieve the list of event types.
     *
     * @param successListener handles a <code>JSONObject</code> in the following format:
     * <pre>{@code
     *
     *     {
     *         "array": [
     *             "addUsersToGroup",
     *             "attachRole",
     *             "bindOffer",
     *             "cancelAsk",
     *             "cancelBid",
     *             "changePassword",
     *             "completeContract",
     *             "completeOffer",
     *             "createAccount",
     *             "createAsk",
     *             "createAsset",
     *             "createBid",
     *             "createEntity",
     *             "createGroup",
     *             "createLink",
     *             "createOffer",
     *             "createPermission",
     *             "createRole",
     *             "createStock",
     *             "createUser",
     *             "deactivateUser",
     *             "deleteAccount",
     *             "deleteAsset",
     *             "deleteEntity",
     *             "deleteGroup",
     *             "deleteLink",
     *             "deletePermission",
     *             "deleteUser",
     *             "grantAccess",
     *             "reactivateUser",
     *             "removeUsersFromGroup",
     *             "revokeAccess",
     *             "signupRole",
     *             "transfer",
     *             "updateAccount",
     *             "updateAsset",
     *             "updateEntity",
     *             "updateGroup",
     *             "updatePrice",
     *             "updateRole",
     *             "updateUser"
     *         ]
     *     }
     *
     * }</pre>
     * @param errorListener handles a possible exception during the request
     */
    void getEventTypes(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener);

    /**
     * Retrieve a summary of a specific event.
     *
     * @param ownerId the owner's UUID
     * @param eventId the event's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     * <pre>{@code
     *
     *     {
     *         "id": String (UUID),
     *         "ownerId": String (UUID),
     *         "userId": String (UUID),
     *         "type": String,
     *         "data": {
     *             <Depends on "type">
     *         },
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "hash": String
     *         "blocks": [{
     *             "ownerId": String (UUID),
     *             "chainId": String (UUID),
     *             "hash": String
     *         }]
     *     }
     *
     * }</pre>
     * @param errorListener handles a possible exception during the request
     */
    void getEventSummary(String ownerId, String eventId, Response.Listener<JSONObject> successListener,
                         Response.ErrorListener errorListener);

    /**
     * Retrieve all of an owner's events.
     *
     * @param ownerId the owner's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     * <pre>{@code
     *
     *     {
     *         "total": integer,
     *         "pages": integer,
     *         "results": [{
     *             "id": String (UUID),
     *             "ownerId": String (UUID),
     *             "userId": String (UUID),
     *             "type": String,
     *             "data": {
     *                 <Depends on "type">
     *             },
     *             "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *             "hash": String
     *         }]
     *     }
     *
     * }</pre>
     * @param errorListener handles a possible exception during the request
     */
    void getAllEventsByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                             Response.ErrorListener errorListener);

    /**
     * Submit an event request.
     *
     * @param ownerId the owner's UUID
     * @param body a <code>JSONObject</code> in the following format:
     * <pre>{@code
     *
     *     {
     *         "type": String,
     *         "data": { }
     *     }
     *
     * }</pre>
     * @param successListener handles a <code>JSONObject</code> in the following format:
     * <pre>{@code
     *
     *     {
     *         "result": String (UUID)
     *     }
     *
     * }</pre>
     * @param errorListener handles a possible exception during the request
     */
    void submitEventRequest(String ownerId, JSONObject body, Response.Listener<JSONObject> successListener,
                            Response.ErrorListener errorListener);

    /**
     * Retrieve all of the events for a specific objectId.
     *
     * @param ownerId the owner's UUID
     * @param objectId the object's UUID
     * @param type the event type
     * @param begin the date of the beginning of the request window; chronologically older than <code>end</code>
     * @param end the date of the end of the request window; chronologically younger than <code>begin</code>
     * @param page the page desired for pagination beginning at page 1
     * @param size the number of elements to return per page
     * @param successListener handles a <code>JSONObject</code> in the following format:
     * <pre>{@code
     *
     *     {
     *         "total": integer,
     *         "pages": integer,
     *         "results": [{
     *             "id": String (UUID),
     *             "ownerId": String (UUID),
     *             "userId": String (UUID),
     *             "type": String,
     *             "data": {
     *                 <Depends on "type">
     *             },
     *             "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *             "hash": String
     *         }]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void getAllEventsByObjectId(String ownerId, String objectId, String type, Date begin, Date end, int page,
                                int size, Response.Listener<JSONObject> successListener,
                                Response.ErrorListener errorListener);
}
