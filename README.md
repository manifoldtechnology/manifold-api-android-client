# manifold-api-android-client
Manifold Technology API Client Library for Android

## Installation

[![](https://jitpack.io/v/manifoldtechnology/manifold-api-android-client.svg)](https://jitpack.io/#manifoldtechnology/manifold-api-android-client)

### gradle

**Step 1**. Add the JitPack repository to your build file

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

**Step 2**. Add the dependency

    dependencies {
        compile 'com.github.manifoldtechnology:manifold-api-android-client:v1.1.1'
    }
    
### maven

**Step 1**. Add the JitPack repository to your build file

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

**Step 2**. Add the dependency

    <dependency>
        <groupId>com.github.manifoldtechnology</groupId>
        <artifactId>manifold-api-android-client</artifactId>
        <version>v1.1.1</version>
    </dependency>
    
## Usage Example

Construct a `ManifoldApiConnector` to define the connection details and target server:

    ManifoldApiConnector connector = new ManifoldApiConnector(properties.getProperty("protocol"),
            properties.getProperty("host"), 
            properties.getProperty("port"), 
            properties.getProperty("path"),
            properties.getProperty("version"), 
            username, 
            password);

Identify the `ownerId`, which is a `UUID` available from `UserApi.requestRole`:

    userApi.requestRole(username, password, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                ownerId = response.getString("id");
                responseReceived = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, defaultErrorListener());

Submit an arbitrary JSON event:

    eventsApi.submitEventRequest(ownerId, json, getEventResponseListener(), defaultErrorListener());

From the success callback, submit another request to view the last block summary in the blockchain:

    private Response.Listener<JSONObject> getEventResponseListener(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                blockchainApi.findLastBlockSummaryByOwner(ownerId, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response.toString(4));
                            responseReceived = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, defaultErrorListener());
            }
        };
    }

The response shows the last block in the blockchain with the most recently submitted event:

    {
         "ownerId": "e37aa970-bbcf-11e6-a450-5fdf90e45bcd",
         "chainId": "e37aa970-bbcf-11e6-a450-5fdf90e45bcd",
         "hash": "1381FC1EE5520955365D211CF8368407B05A363826E356F9893CDD68239CF11C",
         "previousHash": "45B0EEFB4630D329B746B55A97CB1721444684A65C8C0F4989E1D9DE5FFED140",
         "created": "2016-12-06T16:58:40.302Z",
         "events": [
             {
                 "id": "3cbf58a0-bbd5-11e6-a450-5fdf90e45bcd",
                 "ownerId": "e37aa970-bbcf-11e6-a450-5fdf90e45bcd",
                 "userId": "63ff307f-09e0-4afa-a02c-8dea575c7875",
                 "type": "CurrentTime",
                 "data": {
                     "time": 1481043519329
                 },
                 "created": "2016-12-06T16:58:40.298Z",
                 "hash": "4059DFDCE8071A27DAB9338D4E84076DFC9B27ED666D185E687AC327A40ED3E4"
             }
        ]
    }