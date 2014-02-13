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

import au.edu.unsw.cse.soc.federatedcloud.orchestrator.ComponentStateNotFoundException;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.ECARuleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: denis
 * Represent the State Machine
 */
public class OrchestratorWorkflow {
    private static final Logger log = LoggerFactory.getLogger(OrchestratorWorkflow.class);

    private String name;
    private List<ComponentState> componentStates;
    private List<Transition> transitions;
    private List<ECARule> eCARules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComponentStates(List<ComponentState> componentStates) {
        this.componentStates = componentStates;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public List<ComponentState> getComponentStates() {
        return componentStates;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<ECARule> geteCARules() {
        return eCARules;
    }

    public void seteCARules(List<ECARule> eCARules) {
        this.eCARules = eCARules;

    }

    public ECARule getECARuleByID(String ruleID) {
        for(ECARule rule : eCARules) {
            if (rule.getId().equals(ruleID)) {
                return rule;
            }
        }
        throw new ECARuleNotFoundException("Rule with id:" + ruleID + " was not found.");
    }

    public ComponentState getComponentStateByID(String targetStateID) {
        for(ComponentState state : componentStates) {
            if (state.getId().equals(targetStateID)) {
                return state;
            }
        }
        throw new ComponentStateNotFoundException("Component State with id:" + targetStateID + " was not found.");
    }
}
