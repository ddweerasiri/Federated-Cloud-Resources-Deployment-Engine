package au.edu.unsw.cse.soc.federatedcloud.deployers;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: denis
 * Factory class to generate deployers for the given Deployer
 */
public class DeployerFactory {
    private static final Logger logger = LoggerFactory.getLogger(DeployerFactory.class);

    public static CloudResourceDeployer build(String deployerName) throws Exception {
        try {
            CloudResourceDeployer deployer = (CloudResourceDeployer) Class.forName(deployerName).newInstance();
            return deployer;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }

        /*if ("au.edu.unsw.cse.soc.federatedcloud.deployers.CloudBaseDeployer".equals(deployerName)) {
            return new CloudBaseDeployer();
        } else if ("Pivotal Tracker".equals(deployerName)) {
            return new PivotalTrackerDeployer();
        } else if ("LucidChart".equals(deployerName)) {
            return new LucidChartDeployer();
        } else if ("AWS-S3".equals(deployerName)) {
            return new AWSS3Deployer();
        } else if ("Rackspace".equals(deployerName)) {
            return new RackspaceDeployer();
        } else if ("Google Cloud Storage".equals(deployerName)) {
            return new GoogleCloudDeployer();
        } else if ("Google-Drive".equals(deployerName)) {
            return new GoogleDriveDeployer();
        } else if ("Heroku".equals(deployerName)) {
            return new HerokuDeployer();
        } else {
            throw new Exception("Deployer class is not registered for the deployerName \"" + deployerName + "\" in DeployerFactory.");
        }*/
    }
}
