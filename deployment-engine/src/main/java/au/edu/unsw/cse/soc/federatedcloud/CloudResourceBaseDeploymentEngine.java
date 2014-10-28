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
import au.edu.unsw.cse.soc.federatedcloud.datamodel.resource.Handler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * User: denis
 * Interpret a workflow xml and execute actions
 */
@Path("/deploymentengine")
public class CloudResourceBaseDeploymentEngine {
    private static final Logger log = LoggerFactory.getLogger(CloudResourceBaseDeploymentEngine.class);
    @Path("/deploy")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deployCloudResourceDescription(@QueryParam("description_id") String description_id, @QueryParam("description_json") @DefaultValue("{}") String description) {
        log.info("Deployment Request Received for id: " + description_id);
        try {
            log.info("Input Resource description: " + URLDecoder.decode(description, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }

        try {
            CloudResourceDescription desc = DataModelUtil.buildCouldResourceDescriptionFromJSON(description_id);
            CloudResourceBaseDeploymentEngine engine = new CloudResourceBaseDeploymentEngine();
            String response = engine.deployCloudResourceDescription(desc);
            log.info("Deployed Resource:" + desc.toString());

            JsonObject json = new JsonObject();
            json.addProperty("result", response);
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.status(Response.Status.OK).entity(e.getMessage()).build();
        }
    }

    public static void main(String[] args) throws Exception {
        //File file = new File("cloud-resource-base/SENG1031.json");   // cloud resource to be deployed as the input
        //File file = new File("cloud-resource-base/computing-server.json");   // cloud resource to be deployed as the input
        //File file = new File("/Users/denis/Dropbox/Documents/UNSW/Projects/github/Federated-Cloud-Resources-Deployment-Engine/cloud-resource-base/cloud-resource-descriptions/key-value-storage-service.json");   // cloud resource to be deployed as the input
    }

    /**
     * Deploy a cloud resources configuration for a given JSON file which specifies a cloud resource description
     *
     * @param file JSON file which specifies a cloud resource description
     * @throws Exception
     */
    public void deployCloudResourceDescription(File file) throws Exception {
        CloudResourceDescription description = DataModelUtil.buildCouldResourceDescriptionFromJSON(file);

        deployCloudResourceDescription(description);
    }

    /**
     * Deploy a cloud resources configuration for a given {@code CloudResourceDescription}
     *
     *
     * @param description object of {@code CloudResourceDescription}
     * @throws Exception
     */
    public String deployCloudResourceDescription(CloudResourceDescription description) throws Exception {
        Handler handler = description.getHandler();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(description);

        return ServiceBusClient.invoke(handler.getName(), "deploy", json);
    }

    /**
     * Deploy a cloud resource for a given id
     *
     * @param resourceID ID of a cloud resource description
     */
    public void deployCloudResourceDescription(String resourceID) throws Exception {
        CloudResourceDescription description = DataModelUtil.buildCouldResourceDescriptionFromJSON(resourceID);

        deployCloudResourceDescription(description);
    }
}
