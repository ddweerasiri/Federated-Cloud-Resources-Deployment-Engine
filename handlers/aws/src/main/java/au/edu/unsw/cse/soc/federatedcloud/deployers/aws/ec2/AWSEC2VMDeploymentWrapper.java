package au.edu.unsw.cse.soc.federatedcloud.deployers.aws.ec2;
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
import com.amazonaws.services.ec2.model.CreateImageRequest;
import com.amazonaws.services.ec2.model.CreateImageResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: denis
 * TODO: Include the class description here
 */
public class AWSEC2VMDeploymentWrapper implements CloudResourceDeployer {
    private static final Logger log = LoggerFactory.getLogger(AWSEC2VMDeploymentWrapper.class);

    @Override
    public void deployResource(CloudResourceDescription description) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey-NULL");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonEC2Client cleint = new AmazonEC2Client(credentials);

        CreateImageRequest request = new CreateImageRequest();
        request.setInstanceId("");
        request.setName("");
        CreateImageResult result = cleint.createImage(request);

        /*will be returned*/ result.getImageId();
    }

    @Override
    public void undeployResource(String resourceID) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey-NULL");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonEC2Client cleint = new AmazonEC2Client(credentials);

        TerminateInstancesRequest request = new TerminateInstancesRequest();
        List<String> idList = new ArrayList<String>();
        idList.add(resourceID);
        request.setInstanceIds(idList);
        TerminateInstancesResult result = cleint.terminateInstances(request);

    }
}
