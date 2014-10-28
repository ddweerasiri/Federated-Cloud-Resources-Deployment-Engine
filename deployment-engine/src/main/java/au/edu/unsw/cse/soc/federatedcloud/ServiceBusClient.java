package au.edu.unsw.cse.soc.federatedcloud;
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

import au.edu.unsw.cse.soc.federatedcloud.datamodel.resource.CloudResourceDescription;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * User: denis
 * Client to access service bus
 */
public class ServiceBusClient {
    private static final Logger log = LoggerFactory.getLogger(ServiceBusClient.class);

    public static String invoke(String serviceName, String operationName, String resourceDescription) throws UnsupportedEncodingException {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://localhost:8080/servicebus-1.0/servicebus/invoke_by_name");

        String encodingScheme = "UTF-8";
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("service_name", URLEncoder.encode(serviceName, encodingScheme));
        formData.add("operation_name", URLEncoder.encode(operationName, encodingScheme));
        formData.add("params", resourceDescription);
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON).
                        type(MediaType.APPLICATION_FORM_URLENCODED).
                        post(ClientResponse.class, formData);

        String output = response.getEntity(String.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus() + ". Error details: " + output + ".");
        } else {
            System.out.println("Output from Server .... \n");
            System.out.println(output);
        }
        return output;
    }
}
