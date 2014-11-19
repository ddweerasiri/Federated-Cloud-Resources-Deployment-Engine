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
public class DockerImageDescription {
    private static final Logger log = LoggerFactory.getLogger(DockerImageDescription.class);
    private String name;
    private String script;
    private DockerImageDescription baseImage;
    private String version;

    public boolean hasScript() {
        return script!= null && !script.isEmpty();
    }

    public String getName() {
        return name;
    }

    public String getScript() {
        return script;
    }

    public DockerImageDescription getBaseImage() {
        return baseImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setBaseImage(String name, String version) {
        if (this.baseImage == null) {
            this.baseImage  = new DockerImageDescription();
            this.baseImage.setName(name);
            this.baseImage.setVersion(version);
        }
        else {
            log.debug("Base image of image:" + getName() + " is already created.");
        }

    }
}
