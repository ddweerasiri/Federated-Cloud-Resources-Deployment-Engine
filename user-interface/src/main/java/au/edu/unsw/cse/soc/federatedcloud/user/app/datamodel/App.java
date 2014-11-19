package au.edu.unsw.cse.soc.federatedcloud.user.app.datamodel;

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

import java.util.List;

public class App extends Resource {
    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    private List<Resource> resources;

    public Resource getResourceByName(String resourceName) throws ResourceNotFoundException {
        for (Resource resource : this.getResources()) {
            if (resourceName.equals(resource.getName())) {
                return resource;
            }
        }
        throw new ResourceNotFoundException("Resource:" + resourceName + " was not found.");

    }

    /**
     * /**
     * Remove the resource element from app.
     * @param resourceToBeUndeployed
     */
    public void removeResourceFromApp(Resource resourceToBeUndeployed) {
        //Assumed the ID will be unique among the set of resource of an app
        for (Resource res : this.getResources()) {
            try {
                if (res.getID() == resourceToBeUndeployed.getID()) {
                    this.getResources().remove(res);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
