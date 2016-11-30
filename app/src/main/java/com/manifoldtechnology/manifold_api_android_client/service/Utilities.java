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

package com.manifoldtechnology.manifold_api_android_client.service;

import android.content.Context;
import android.net.Uri;

import com.android.volley.VolleyError;
import com.manifoldtechnology.manifold_api_android_client.R;
import com.manifoldtechnology.manifold_api_android_client.domain.ManifoldApiConnector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Utilities {

    public static String unpackVolleyError(VolleyError error, Context context) {
        if(error.getMessage() != null){
            return error.getMessage();
        }

        if(error.networkResponse != null && error.networkResponse.data != null) {
            try {
                int statusCode = error.networkResponse.statusCode;

                if(statusCode == 404){
                    return context.getString(R.string.requested_resource_not_found);
                }

                return new String(error.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return e.getMessage();
            }
        }
        else{
            return context.getApplicationContext().getString(R.string.network_response_is_null);
        }
    }

    /**
     * Returns the application properties.
     *
     * @param context
     * @return
     * @throws IOException
     */
    public static Properties getProperties(Context context) throws IOException {
        Properties properties = new Properties();
        properties.load(context.getResources().openRawResource(R.raw.application));
        return properties;
    }

    /**
     * Builds the common portion of a request URL.
     *
     * @param connector
     * @return
     */
    public static Uri.Builder getUrlRoot(ManifoldApiConnector connector){
        Uri.Builder uriBuilder = new Uri.Builder();

        return uriBuilder.scheme(connector.getProtocol())
                .encodedAuthority(connector.getHost() + ":" + connector.getPort())
                .appendPath(connector.getPath())
                .appendPath(connector.getVersion());
    }
}
