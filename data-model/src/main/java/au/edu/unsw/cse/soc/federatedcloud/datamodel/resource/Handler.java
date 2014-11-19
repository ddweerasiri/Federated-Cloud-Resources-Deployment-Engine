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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;

/**
 * User: denis
 * Represent the Handler service of a Cloud resource
 */
public class Handler {
    HashMap<String, String> attributes;
    private static final String NAME_ATTRIBUTE = "name";

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    @JsonIgnore
    public String getName() throws Exception {
        String name = attributes.get(NAME_ATTRIBUTE);
        if (name != null) {
            return name;
        } else {
            throw new Exception("Attribute:" + NAME_ATTRIBUTE + " not found in the handler.");
        }
    }
}
