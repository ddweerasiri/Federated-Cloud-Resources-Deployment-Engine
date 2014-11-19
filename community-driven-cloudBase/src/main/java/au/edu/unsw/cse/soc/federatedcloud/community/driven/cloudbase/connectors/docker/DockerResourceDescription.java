package au.edu.unsw.cse.soc.federatedcloud.community.driven.cloudbase.connectors.docker;
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

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: denis
 * Represent  a docker resource model
 */
public class DockerResourceDescription extends JSONObject {
    private static final Logger log = LoggerFactory.getLogger(DockerResourceDescription.class);
    private DockerContainerDescription container;
    private int testVal = 9;


    public DockerResourceDescription() {
        this.container = new DockerContainerDescription();
        this.container.setName("defaultName");
    }

    public DockerContainerDescription getContainer() {
        return container;
    }

    public void setContainer(DockerContainerDescription container) {
        this.container = container;
    }

    public int getTestVal() {
        return testVal;
    }
}
