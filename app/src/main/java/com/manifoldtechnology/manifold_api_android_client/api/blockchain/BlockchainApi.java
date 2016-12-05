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

import com.android.volley.Response;
import com.manifoldtechnology.manifold_api_android_client.api.Api;

import org.json.JSONObject;

/**
 * Provides read access to blockchains, blocks, and block summaries.
 */
public interface BlockchainApi extends Api {

    /**
     * Retrieve all chains the current user has access to.
     *
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "array": [{
     *             "ownerId": String (UUID),
     *             "chainId": String (UUID),
     *             "lastHash": String,
     *             "blockCount": integer,
     *             "eventCount": integer
     *         }]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void getAllChains(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener);

    /**
     * Retrieve all blockchains for a specific ownerId.
     *
     * @param ownerId the owner's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "array": [{
     *             "ownerId": String (UUID),
     *             "chainId": String (UUID),
     *             "lastHash": String,
     *             "blockCount": integer,
     *             "eventCount": integer
     *         }]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void getChainsByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                          Response.ErrorListener errorListener);

    /**
     * Retrieve a specific block on an owner's primary blockchain.
     *
     * @param ownerId the owner's UUID
     * @param blockHash the hash of the block
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "hash": String,
     *         "previousHash": String,
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "events": [
     *             String (UUID)
     *         ]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void findBlockByHash(String ownerId, String blockHash, Response.Listener<JSONObject> successListener,
                         Response.ErrorListener errorListener);

    /**
     * Retrieve a specific block on a specific blockchain
     *
     * @param ownerId the owner's UUID
     * @param chainId the chain's UUID
     * @param blockHash the hash of the block
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "hash": String,
     *         "previousHash": String,
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "events": [
     *             String (UUID)
     *         ]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void findBlockByBlockchain(String ownerId, String chainId, String blockHash,
                               Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener);

    /**
     * Retrieve a specific block and associated events from the owner's primary blockchain.
     *
     * @param ownerId the owner's UUID
     * @param blockHash the hash of the block
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "hash": String,
     *         "previousHash": String,
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "events": [{
     *             "id": String (UUID),
     *             "ownerId": String (UUID),
     *             "userId": String (UUID),
     *             "type": String (user-defined),
     *             "data": {
     *                 JSONObject format dependent on "type"
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
    void findBlockSummaryInPrimaryChain(String ownerId, String blockHash, Response.Listener<JSONObject> successListener,
                                        Response.ErrorListener errorListener);

    /**
     * Retrieve a specific block and associated events from a specific blockchain.
     *
     * @param ownerId the owner's UUID
     * @param chainId the chain's UUID
     * @param blockHash the hash of the block
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "hash": String,
     *         "previousHash": String,
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "events": [{
     *             "id": String (UUID),
     *             "ownerId": String (UUID),
     *             "userId": String (UUID),
     *             "type": String (user-defined),
     *             "data": {
     *                 JSONObject format dependent on "type"
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
    void findBlockSummaryInSpecificChain(String ownerId, String chainId, String blockHash,
                                         Response.Listener<JSONObject> successListener,
                                         Response.ErrorListener errorListener);

    /**
     * Retrieve the primary blockchain for an ownerId.
     *
     * @param ownerId the owner's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "lastHash": String,
     *         "blockCount": integer,
     *         "eventCount": integer
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void findPrimaryBlockchainByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                                      Response.ErrorListener errorListener);

    /**
     * Retrieve a specified blockchain.
     *
     * @param ownerId the owner's UUID
     * @param chainId the chain's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "lastHash": String,
     *         "blockCount": integer,
     *         "eventCount": integer
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void findBlockchainByOwner(String ownerId, String chainId, Response.Listener<JSONObject> successListener,
                               Response.ErrorListener errorListener);

    /**
     * Retrieve the last block on the owner's primary blockchain.
     *
     * @param ownerId the owner's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "hash": String,
     *         "previousHash": String
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "events": [
     *             String (UUID)
     *         ]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void findLastBlockByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                              Response.ErrorListener errorListener);

    /**
     * Retrieve the last block on a specific blockchain.
     *
     * @param ownerId the owner's UUID
     * @param chainId the chain's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "hash": String,
     *         "previousHash": String
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "events": [
     *             String (UUID)
     *         ]
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void findLastBlockByOwnerAndChain(String ownerId, String chainId, Response.Listener<JSONObject> successListener,
                                      Response.ErrorListener errorListener);

    /**
     * Retrieve the last block and associated events from the owner's primary blockchain.
     *
     * @param ownerId the owner's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "hash": String,
     *         "previousHash": String,
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "events": [{
     *             "id": String (UUID),
     *             "ownerId": String (UUID),
     *             "userId": String (UUID),
     *             "type": String (user-defined),
     *             "data": {
     *                 JSONObject format dependent on "type"
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
    void findLastBlockSummaryByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                                     Response.ErrorListener errorListener);

    /**
     * Retrieve the last block and associated events from a specific blockchain.
     *
     * @param ownerId the owner's UUID
     * @param chainId the chain's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "hash": String,
     *         "previousHash": String,
     *         "created": String (date string, format: yyyy-MM-dd'T'HH:mm:ss.SSS'Z'),
     *         "events": [{
     *             "id": String (UUID),
     *             "ownerId": String (UUID),
     *             "userId": String (UUID),
     *             "type": String (user-defined),
     *             "data": {
     *                 JSONObject format dependent on "type"
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
    void findLastBlockSummaryByOwnerAndChain(String ownerId, String chainId,
                                             Response.Listener<JSONObject> successListener,
                                             Response.ErrorListener errorListener);

    /**
     * Validates the owner's primary blockchain.
     *
     * @param ownerId the owner's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "lastHash": String,
     *         "blockCount": integer,
     *         "eventCount": integer
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void validateBlockchainByOwner(String ownerId, Response.Listener<JSONObject> successListener,
                                   Response.ErrorListener errorListener);

    /**
     * Validates a specific blockchain.
     *
     * @param ownerId the owner's UUID
     * @param chainId the chain's UUID
     * @param successListener handles a <code>JSONObject</code> in the following format:
     *
     * <pre>{@code
     *
     *     {
     *         "ownerId": String (UUID),
     *         "chainId": String (UUID),
     *         "lastHash": String,
     *         "blockCount": integer,
     *         "eventCount": integer
     *     }
     *
     * }</pre>
     *
     * @param errorListener handles a possible exception during the request
     */
    void validateBlockchainByOwnerAndChain(String ownerId, String chainId,
                                           Response.Listener<JSONObject> successListener,
                                           Response.ErrorListener errorListener);
}
