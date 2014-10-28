package au.edu.unsw.cse.soc.federatedcloud.cloudRessourceBase;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: denis
 * The recommender API
 */
@Path("/recommender")
public class RecommenderAPI {
    private static final Logger log = LoggerFactory.getLogger(RecommenderAPI.class);

    @Path("/getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRecommendations() {
        JsonObject json = returnDummyObject();

        return Response.status(Response.Status.OK).entity(json.toString()).build();

    }

    @Path("/getByName")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendationsByName(String name) {
        JsonObject json = returnDummyObject();

        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    @Path("/getByTaskCategory")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendationsByTaskCategory(String taskCategory) {
        JsonObject json = returnDummyObject();

        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    private JsonObject returnDummyObject() {
        JsonObject json = new JsonObject();
        JsonArray descriptionsJsonArray = new JsonArray();

        JsonObject desciption1 = new JsonObject();
        desciption1.addProperty("Id", "1");
        desciption1.addProperty("Name", "SE-Bucket");
        desciption1.addProperty("TargetEnv", "IaaS");
        desciption1.addProperty("Deployer", "au.edu.unsw.cse.soc.federatedcloud.deployers.aws.AWSS3Deployer");
        descriptionsJsonArray.add(desciption1);

        JsonObject desciption2 = new JsonObject();
        desciption2.addProperty("Id", "2");
        desciption2.addProperty("Name", "Rackspace-VM");
        desciption2.addProperty("TargetEnv", "IaaS");
        desciption2.addProperty("Deployer", "au.edu.unsw.cse.soc.federatedcloud.deployers.rackspace.RackspaceDeployer");
        descriptionsJsonArray.add(desciption2);

        JsonObject desciption3 = new JsonObject();
        desciption3.addProperty("Id", "3");
        desciption3.addProperty("Name", "GoogleDrive");
        desciption3.addProperty("TargetEnv", "SaaS");
        desciption3.addProperty("Deployer", "au.edu.unsw.cse.soc.federatedcloud.deployers.GoogleDriveDeployer");
        descriptionsJsonArray.add(desciption3);

        json.add("CloudResourceDescriptions", descriptionsJsonArray);

        return json;
    }

}
