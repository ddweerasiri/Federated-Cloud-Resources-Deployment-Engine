package au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.connectors.juju;
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

import java.util.ArrayList;

/**
 * User: denis
 * TODO: Include the class description here
 */
public class JujuCharm {
    private static final Logger log = LoggerFactory.getLogger(JujuCharm.class);
    private String name;
    private String summary;
    private String description;
    private String maintainer;
    private ArrayList<String> categories;
    private String repository;
    private ArrayList<JujuCharmInterface> providedInterface;

    public JujuCharm() {
        categories = new ArrayList<String>();
        providedInterface = new ArrayList<JujuCharmInterface>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public void setrepository(String repository) {
        this.repository = repository;
    }

    public void addProvidedInterface(JujuCharmInterface charmInterface) {
        providedInterface.add(charmInterface);
    }

    public String getName() {
        return name;
    }
}
