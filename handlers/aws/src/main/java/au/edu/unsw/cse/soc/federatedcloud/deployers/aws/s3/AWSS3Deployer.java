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

import au.edu.unsw.cse.soc.federatedcloud.datamodel.resource.CloudResourceDescription;
import au.edu.unsw.cse.soc.federatedcloud.deployers.CloudResourceDeployer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

/**
 * User: denis
 * Handler for AWS S3
 */
@Path("/AWSHandler")
public class AWSS3Deployer implements CloudResourceDeployer {
    private static final Logger log = LoggerFactory.getLogger(AWSS3Deployer.class);

    @Path("/deploy")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    /*@Override*/
    public Response deploy(@QueryParam("descriptionid") String descriptionid) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3Client client = new AmazonS3Client(credentials);

        //String name = description.getAttributes().get("name");
        String name = "ddweerasiri-test-bucket";
        //client.createBucket(name);

        log.info("Bucket was created with name:" + name);
        System.out.println("Helooooooo:" + descriptionid);

        JsonObject json = new JsonObject();
        json.addProperty("cloudResourceDescriptionId", descriptionid);
        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }


    @Override
    public void undeployResource(String resourceID) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3Client client = new AmazonS3Client(credentials);
        client.deleteBucket(resourceID);

        Response.status(Response.Status.OK).entity(json.toString()).build();

    }
}
