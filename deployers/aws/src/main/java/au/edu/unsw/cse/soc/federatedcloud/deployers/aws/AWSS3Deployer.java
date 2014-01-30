package au.edu.unsw.cse.soc.federatedcloud.deployers.aws;
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

import au.edu.unsw.cse.soc.federatedcloud.datamodel.CloudResourceDescription;
import au.edu.unsw.cse.soc.federatedcloud.deployers.CloudResourceDeployer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * User: denis
 * Deployer for AWS S3
 */
public class AWSS3Deployer implements CloudResourceDeployer {
    private static final Logger log = LoggerFactory.getLogger(AWSS3Deployer.class);

    @Override
    public void deploy(CloudResourceDescription description) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/AwsCredentials.properties"));
        String accessKey = properties.getProperty("accessKey");
        String secretKey = properties.getProperty("secretKey");

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3Client client = new AmazonS3Client(credentials);

        String name = description.getAttributes().get("name");
        client.createBucket(name);

        log.info("Bucket was created with name:" + name);

    }
}
