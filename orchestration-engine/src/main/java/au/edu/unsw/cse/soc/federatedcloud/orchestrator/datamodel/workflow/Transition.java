package au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow;
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

import java.util.List;

/**
 * User: denis
 * Represent a Transition in the State Machine
 */
public class Transition {
    private static final Logger log = LoggerFactory.getLogger(Transition.class);

    private int id;
    private String sourceStateID;
    private String targetStateID;
    private List<String> eCARuleIDs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceStateID() {
        return sourceStateID;
    }

    public void setSourceStateID(String sourceStateID) {
        this.sourceStateID = sourceStateID;
    }

    public String getTargetStateID() {
        return targetStateID;
    }

    public void setTargetStateID(String targetStateID) {
        this.targetStateID = targetStateID;
    }

    public List<String> geteCARuleIDs() {
        return eCARuleIDs;
    }

    public void seteCARuleIDs(List<String> eCARuleIDs) {
        this.eCARuleIDs = eCARuleIDs;
    }
}
