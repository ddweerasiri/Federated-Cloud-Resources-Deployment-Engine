package au.edu.unsw.cse.soc.federatedcloud.orchestrator;
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

import au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow.ComponentState;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow.OrchestratorWorkflow;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * User: denis
 * Maintains the execution context during a orchestration workflow execution
 */
public class ExecutionContext {
    private static final Logger log = LoggerFactory.getLogger(ExecutionContext.class);
    List<OrchestratorWorkflow> workflows;
    ComponentState activeState;

    public ExecutionContext() {
        workflows = new ArrayList<OrchestratorWorkflow>();
    }

    public List<OrchestratorWorkflow> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(List<OrchestratorWorkflow> workflows) {
        this.workflows = workflows;
    }

    public ComponentState getActiveState() {
        return activeState;
    }

    public void setActiveState(ComponentState activeState) {
        if (this.activeState == null) {
            log.info("Initial Active state is set to :" + activeState.getId());
        } else {
            log.info("Active state changed from: " + this.activeState.getId() + " to " + activeState.getId());
        }
        this.activeState = activeState;
    }

    public void addWorkflow(OrchestratorWorkflow workflow) {
        workflows.add(workflow);
    }

    public List<Transition> getFireableTransitions() {
        List<Transition> fireableTransitions = new ArrayList<Transition>();
        //search transitions, which has the sourceState equals to ActiveState
        OrchestratorWorkflow currentWorkflow = workflows.get(0);
        for (Transition t : currentWorkflow.getTransitions()) {
            if (t.getSourceStateID().equals(activeState.getId())) {
                fireableTransitions.add(t);
            }
        }
        //returns the list
        return fireableTransitions;
    }
}
