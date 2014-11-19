package au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.connectors.docker;
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
 * TODO: Include the class description here
 */
public class DockerContainerDescription {
    private static final Logger log = LoggerFactory.getLogger(DockerContainerDescription.class);
    private DockerImageDescription image;
    private String name;
    private String state;
    private String protBindingRules;
    private DockerVirtualMachineDescription virtualMachine;

    public DockerImageDescription getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public void setState(String state) {
        this.state = state;
    }

    public void setProtBindingRules(String protBindingRules) {
        this.protBindingRules = protBindingRules;
    }

    public void setImage(DockerImageDescription image) {
        this.image = image;
    }


    public void setVirtualMachine(DockerVirtualMachineDescription virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    public DockerVirtualMachineDescription getVirtualMachine() {
        return virtualMachine;
    }
}
