package au.edu.unsw.cse.soc.federatedcloud.user;

/*
 * Copyright (c) 2014, Denis Weerasiri All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * This is not the right place for this class
 */
public class DeploymentEngineClient {
    public static void undeploy(int resourceId) {


    }

    public static int deploy(String resourceName, String resourceMetadata) throws UnsupportedEncodingException {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://localhost:8080/deployment-engine-1.0-SNAPSHOT/rest/deploymentengine/deploy");

        ClientResponse response = webResource.queryParam("description_id", URLEncoder.encode(resourceName, "UTF-8"))
                .queryParam("description_json", URLEncoder.encode(resourceMetadata, "UTF-8"))
                .accept(MediaType.APPLICATION_JSON).
                        type(MediaType.APPLICATION_JSON).
                        post(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);

        System.out.println("Output from Server .... \n");
        System.out.println(output);
        return 0;
    }
}
