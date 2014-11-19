package au.edu.unsw.cse.soc.federatedcloud.datamodel.resource;
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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * User: denis
 * Represent the Data model of a Cloud resource
 */
public class CloudResourceDescription {
    private static final Logger logger = LoggerFactory.getLogger(CloudResourceDescription.class);
    private static final String NAME_ATTRIBUTE = "name";
    private static final String ID_ATTRIBUTE = "id";

    HashMap<String, String> attributes;

    HashSet<Handler> handlers;

    public void setHandlers(HashSet<Handler> handlers) {
        this.handlers = handlers;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public HashSet<Handler> getHandlers() {
        return handlers;
    }

    /**
     * Extract the Handler with a specific handlerName from the cloud resource description
     *
     * @param handlerName
     * @return
     */
    @JsonIgnore
    public Handler getHandlerByName(String handlerName) throws Exception {
        for (Iterator<Handler> it = handlers.iterator(); it.hasNext(); ) {
            Handler handler = it.next();
            try {
                if (handlerName.equals(handler.getName())) {
                    return handler;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        throw new RuntimeException("Handler with name:\"" + handlerName + "\" was no found.");

    }

    /**
     * Extract the Handler from the cloud resource description. (When there are multiple handlers,
     * only the first deployer is returned)
     *
     * @return
     */
    @JsonIgnore
    public Handler getHandler() {
        for (Iterator<Handler> it = handlers.iterator(); it.hasNext(); ) {
            Handler handler = it.next();
            return handler;
        }
        throw new RuntimeException("No Handler found.");
    }


    /**
     * The business logic to compare cloud resource description for a given id
     *
     * @param cloudResourceDescriptionID id to be search for the input cloud resource description object
     * @return whether the ID of the input cloud resource description object is equal to the input {@code cloudResourceDescriptionID}
     */
    @JsonIgnore
    public boolean isCorrectCloudResourceDescription(String cloudResourceDescriptionID) {
        String id = getName();
        return id.equals(cloudResourceDescriptionID);
    }

    @JsonIgnore
    public String getName() {
        String name = attributes.get(NAME_ATTRIBUTE);
        if (name != null) {
            return name;
        } else {
            throw new RuntimeException("Attribute:" + NAME_ATTRIBUTE + " not found in the handler.");
        }
    }

    /**
     * Extract the ID of the cloud resource description
     *
     * @return the ID of the given cloud resource description
     */
    @JsonIgnore
    public String getID() throws Exception {
        String id = attributes.get(ID_ATTRIBUTE);
        if (id != null) {
            return id;
        } else {
            throw new Exception("Attribute:" + ID_ATTRIBUTE + " not found in the cloud resource description.");
        }
    }


    /*@JsonIgnore
    public String getIDOfCloudResourceDescription() {
        String id = attributes.get("id");
        if (id == null || "".equals(id)) {
            String errorMsg = "This cloud description does not have the \"id\" attribute. \n" + this;
            RuntimeException ex = new RuntimeException(errorMsg);
            logger.error(errorMsg, ex);
            throw ex;
        } else {
            return id;
        }

    }*/

}
