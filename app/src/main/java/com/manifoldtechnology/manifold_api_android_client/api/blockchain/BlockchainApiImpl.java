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

package com.manifoldtechnology.manifold_api_android_client.api.blockchain;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.manifoldtechnology.manifold_api_android_client.api.AbstractApi;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;
import com.manifoldtechnology.manifold_api_android_client.service.Utilities;
import com.manifoldtechnology.manifold_api_android_client.service.request.JsonObjectRequestBasicAuth;
import com.manifoldtechnology.manifold_api_android_client.service.request.RequestQueueSingleton;

import org.json.JSONObject;

/**
 * Default implementation of the <code>BlockchainApi</code> using Volley for HTTP requests.
 */
public class BlockchainApiImpl extends AbstractApi implements BlockchainApi {

    public BlockchainApiImpl(Context context, ManifoldApiConnector connector){
        super(context, connector);
    }

    @Override
    public void getAllChains(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        String url = Utilities.getUrlRoot(getConnector())
                .appendPath("chains")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_ARRAY, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void getChainsByOwner(String ownerId, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath("chains")
                .appendPath(ownerId)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_ARRAY, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findBlockByHash(String ownerId, String blockHash, Response.Listener<JSONObject> successListener,
                                Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("blocks")
                .appendPath(blockHash)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findBlockByBlockchain(String ownerId, String chainId, String blockHash,
                                      Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("blocks")
                .appendPath(chainId)
                .appendPath(blockHash)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findBlockSummaryInPrimaryChain(String ownerId, String blockHash,
                                               Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("blocksummary")
                .appendPath(blockHash)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findBlockSummaryInSpecificChain(String ownerId, String chainId, String blockHash,
                                                Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("blocksummary")
                .appendPath(chainId)
                .appendPath(blockHash)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findPrimaryBlockchainByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                                             Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("chain")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findBlockchainByOwner(String ownerId, String chainId, Response.Listener<JSONObject> successListener,
                                      Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("chain")
                .appendPath(chainId)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findLastBlockByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                                     Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("lastblock")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findLastBlockByOwnerAndChain(String ownerId, String chainId, Response.Listener<JSONObject> successListener,
                                             Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("lastblock")
                .appendPath(chainId)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findLastBlockSummaryByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                                            Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("lastblocksummary")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void findLastBlockSummaryByOwnerAndChain(String ownerId, String chainId,
                                                    Response.Listener<JSONObject> successListener,
                                                    Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("lastblocksummary")
                .appendPath(chainId)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void validateBlockchainByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                                          Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("validate")
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void validateBlockchainByOwnerAndChain(String ownerId, String chainId, Response.Listener<JSONObject> successListener,
                                                  Response.ErrorListener errorListener) {

        String url = Utilities.getUrlRoot(getConnector())
                .appendPath(ownerId)
                .appendPath("validate")
                .appendPath(chainId)
                .build().toString();

        JsonObjectRequestBasicAuth request = new JsonObjectRequestBasicAuth(Request.Method.GET, url,
                getConnector().getUsername(), getConnector().getPassword(), null,
                JsonObjectRequestBasicAuth.Type.JSON_OBJECT, successListener, errorListener);

        RequestQueueSingleton.getInstance(getContext()).addToRequestQueue(request);
    }
}
