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
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow.ECARule;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow.OrchestratorWorkflow;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow.Transition;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.eventprocesser.Event;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.eventprocesser.EventRouter;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.eventprocesser.TemporalEvent;
import au.edu.unsw.cse.soc.federatedcloud.orchestrator.eventprocesser.TemporalEventHandler;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: denis
 * Represents the OrchestratorEngine
 */
public class OrchestratorEngine {
    private static final Logger log = LoggerFactory.getLogger(OrchestratorEngine.class);
    private ExecutionContext executionContext = new ExecutionContext();
    private EventRouter router;

    public OrchestratorEngine() {
    }

    /**
     * Starts the engine
     */
    public void startOrchestrationEngine() {
        startEventListener();
    }

    /**
     * Create a object of the Orchestrator Workflow
     */
    public OrchestratorWorkflow buildOrchestratorWorkflow(File workflow) throws IOException {
        String json = FileUtils.readFileToString(workflow);
        Gson gson = new Gson();
        return gson.fromJson(json, OrchestratorWorkflow.class);
    }

    /**
     * Deploy a workflow object
     * @param workflow
     * @return
     */
    public boolean deployOrchestratorWorkflow (OrchestratorWorkflow workflow) {
        //Persist the workflow in the DB
        log.warn("Orchestration workflow was not persisted. It is run in-memory.");

        //Create a runtime object
        log.warn("GSON Object is used as the runtime object within the orchestration engine. Just a shortcut. Not a good design. ");

        //Add to the execution context
        executionContext.addWorkflow(workflow);

        //Set the fist state of the workflow as the active state
        executionContext.setActiveState(workflow.getComponentStates().get(0));

        return true;
    }

    /**
     * Start a dummy event generator
     */
    public void startEventGenerator() {
        router.sendEvent(new TemporalEvent(1000, null));
        router.sendEvent(new TemporalEvent(1001, null));
        router.sendEvent(new TemporalEvent(1002, null));
        router.sendEvent(new TemporalEvent(1003, null));
        router.sendEvent(new TemporalEvent(1004, null));
    }

   /**
     * Start the event listener
    */
    private void startEventListener() {
        router = new EventRouter();
        router.registerHandler(TemporalEvent.class, new TemporalEventHandler());
    }

    /**
     * Triggered at each event
     * @param event
     */
    public void onEvent(Event event) {
        executeTransition(event);
    }

    /**
     * Invoke during a state transition
     * @param event
    */
    private void executeTransition(Event event) {
        log.warn("In the current prototype, we only consider transition of one workflow. Multiple workflows are not handled concurrently.");
        OrchestratorWorkflow currentWorkflow = executionContext.getWorkflows().get(0);
        List<Transition> transitions = executionContext.getFireableTransitions();
        for (Transition t : transitions) {
            if (executionContext.getActiveState().getId().equals(t.getSourceStateID())) {       //is the machine is in the source state of the transition?
                for (String ruleID : t.geteCARuleIDs()) {
                    ECARule rule = currentWorkflow.getECARuleByID(ruleID);
                    if (event.getId() == rule.getEvent().getId()) {       //is the type of the event occurrence matches the event description attached to the transition?
                        if (rule.getCondition().equals("true")) {       //is the condition of the transition holds?
                            triggerTransition(t);
                            break; // break the loop if even a "single" rule's condition is true
                        }
                    } else {
                        log.info("Event with id:" + event.getId() + " did not trigger any state transitions from state" + executionContext.getActiveState().getId());
                    }
                }
                break;     // once a state transition happen for a particular state, loop breaks, as there's no need to process remaining transitions
            }
        }
    }

    private void triggerTransition(Transition transition) {
        //figure out the target state id
        String targetStateID = transition.getTargetStateID();

        //set the target state as the active state in executionContext
        log.warn("In the current prototype, we only consider one workflow. Multiple workflows are not handled concurrently.");
        OrchestratorWorkflow currentWorkflow = executionContext.getWorkflows().get(0);
        ComponentState targetState = currentWorkflow.getComponentStateByID(targetStateID);
        executionContext.setActiveState(targetState);

        //invoke action specified in the ECA rule
        log.error("Transition logic should be implemented.");
    }
}
