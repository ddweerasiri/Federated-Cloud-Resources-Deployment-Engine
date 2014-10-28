package au.edu.unsw.cse.soc.federatedcloud.curator;
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

import au.edu.unsw.cse.soc.federatedcloud.DataModelUtil;
import au.edu.unsw.cse.soc.federatedcloud.datamodel.resource.CloudResourceDescription;
import au.edu.unsw.cse.soc.federatedcloud.datamodel.rule.RecommendationRule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.RandomStringUtils;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: denis
 * API for curator's tasks
 */
@Path("/curatorAPI")
public class CuratorAPI {
    private static final Logger log = LoggerFactory.getLogger(CuratorAPI.class);
    private final String NAME_OF_ID_ATTRIBUTE = "id";
    //private final String KB_LOCATION = "C:\\Users\\user\\Dropbox\\Documents\\UNSW\\Projects\\github\\Federated-Cloud-Resources-Deployment-Engine\\cloud-resource-base\\cloud-resource-descriptions\\";
    private final String KB_LOCATION = "/Users/denis/Dropbox/Documents/UNSW/Projects/github/Federated-Cloud-Resources-Deployment-Engine/cloud-resource-base/cloud-resource-descriptions/";

    @Path("/getResource")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<CloudResourceDescription> getResource(@QueryParam("category")String category) {
        //query the cloud base
        List<CloudResourceDescription> descs = queryKnowledgeBase("category="+ category);

        //return a list of descriptions
        //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Still not implemented").build();
        return descs;
    }

    @Path("/putResource")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putResource(CloudResourceDescription description) {
        persistResource(description);
        log.warn("Composite Resource handler is not yet implemented.");

        JsonObject json = new JsonObject();
        try {
            json.addProperty("cloudResourceDescriptionId", description.getID());
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }



    @Path("/putRule")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response putRule(RecommendationRule rule) {
        persistRecommendationRule(rule);
        log.warn("Composite Resource handler is not yet implemented.");

        JsonObject ruleID = new JsonObject();
        ruleID.addProperty("ruleID", rule.getId());
        return Response.status(Response.Status.OK).entity(ruleID.toString()).build();
    }

    private boolean persistResource(CloudResourceDescription description) {
        //generate a id
        String id = getUniqueID();

        //add the id as an attribute to the description

        description.getAttributes().put(NAME_OF_ID_ATTRIBUTE, id);

        //persist the file in KB
        try {
            persistDescription(description);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return true;
    }
    private String getUniqueID () {
        return RandomStringUtils.random(8, true, true);
    }

    /**
     * This is a temporary method until a proper DB is figured out.
     * @param description
     */
    private void persistDescription(CloudResourceDescription description) throws Exception {
        //convert the description to json object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(description);

        try {
            FileWriter writer = new FileWriter(KB_LOCATION + description.getName() + ".json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    private boolean persistRecommendationRule(RecommendationRule rule) {
        //generate a id
        String id = getUniqueID();

        //add the id as an attribute to the rule
        rule.setId(id);

        log.warn("Rule creating is still not finished");
        //query the rule base and figure out the right place
        //persist it
        return false;
    }

    private List<CloudResourceDescription> queryKnowledgeBase (String query) {
        File folder = new File(KB_LOCATION);
        File[] jsonFiles = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                System.out.println(pathname.getName());
                return pathname.getName().endsWith(".json");
            }
        });

        List<CloudResourceDescription> descs = new ArrayList<CloudResourceDescription>();
        if (jsonFiles == null) {
            try {
                throw new FileNotFoundException("No Resource descriptions found at:" + KB_LOCATION);
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
                return descs;   //Returns a zero length List
            }
        } else {
            for (File file : jsonFiles) {
                if (file.isDirectory()) {
                    continue;
                }
                try {
                    CloudResourceDescription desc = DataModelUtil.buildCouldResourceDescriptionFromJSON(file);
                    descs.add(desc);
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            System.out.println("Size of Descriptions:" + descs.size());
            return descs;
        }

    }

}
