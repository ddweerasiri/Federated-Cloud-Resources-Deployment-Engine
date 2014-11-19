package au.edu.unsw.cse.soc.federatedcloud.deployers.aws.s3;
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

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

/**
 * User: denis
 * TODO: Include the class description here
 */
public class ClassForPaper {
    private static final Logger log = LoggerFactory.getLogger(ClassForPaper.class);
}

public class AWSS3DeploymentWrapper implements CloudResourceDeployer {

    @Path("/deployResource")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deployResource(@QueryParam("description") CRCD resourceDescription) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3Client client = new AmazonS3Client(credentials);

        String name = resourceDescription.getAttribute("bucket-name");
        Bucket bucket = client.createBucket(name);

        JsonObject json = new JsonObject();
        json.addProperty("resourceID", bucket.getName());
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

    @Path("/undeployResource")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response undeployResource(@QueryParam("resourceID") String resourceID) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3Client client = new AmazonS3Client(credentials);

        client.deleteBucket(resourceID);

        JsonObject json = new JsonObject();
        json.addProperty("result", "Bucket:" + resourceID + "undeployed.");
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }

}