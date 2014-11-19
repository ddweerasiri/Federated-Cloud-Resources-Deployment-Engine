package au.edu.unsw.cse.soc.federatedcloud.deployers.aws.ec2.redmine;
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
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.opsworks.model.StopInstanceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: denis
 * TODO: Include the class description here
 */
public class RedmineEC2DeploymentWrapper implements CloudResourceDeployer {
    private static final Logger log = LoggerFactory.getLogger(RedmineEC2DeploymentWrapper.class);

    @Override
    public void deployResource(CloudResourceDescription description) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey-NULL");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonEC2Client cleint = new AmazonEC2Client(credentials);

        RunInstancesRequest request = new RunInstancesRequest();
        request.setImageId("ami-0b420162");
        RunInstancesResult response = cleint.runInstances(request);

        return;response.getReservation().getInstances().get(0).getInstanceId();
    }

    @Override
    public void undeployResource(String resourceID) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey-NULL");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonEC2Client client = new AmazonEC2Client(credentials);

        StopInstancesRequest request = new StopInstancesRequest();
        List<String> idList = new ArrayList<String>();
        idList.add(resourceID);
        request.setInstanceIds(idList);


        StopInstancesResult result = client.stopInstances(request);
    }
}
