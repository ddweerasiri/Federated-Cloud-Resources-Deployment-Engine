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

import au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow.Transition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: denis
 * Represents the Orchestrator Engine
 */
public class Engine {
    private static final Logger log = LoggerFactory.getLogger(Engine.class);
    ExecutionContext context = new ExecutionContext();

    public static void main(String[] args) {
        startEventGenerator();

        startOrchestrationEngine();
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
    public OrchestratorWorkflow buildOrchestratorWorkflow(File workflow) {

    }

    /**
     * Deploy a workflow object
     * @param workflow
     * @return
     */
    public boolean deployOrchestratorWorkflow (OrchestratorWorkflow workflow) {

    }

    /**
     * Start a dummy event generator
     */
    private void startEventGenerator() {

    }

    /**
     * Start the event listener
     */
    private void startEventListener() {

    }

    /**
     * Triggered at each event
     * @param event
     */
    private void onEvent(Event event) {
        executeTransition(event);
    }

    /**
     * Invoke during a state transition
     * @param event
     */
    private void executeTransition(Event event) {
        List<Transition> transitions = null;
        for (Transition t : transitions) {
            if (context.getActiveState().getID() == t.getSourceState().getID()) {
                if (event.getID() == t.getECA().getEvent().getID()) {
                    if (t.getECA().getCondition() == true) {
                        triggerTransition(t);
                        break;
                    }
                }
            }
        }
    }
}
