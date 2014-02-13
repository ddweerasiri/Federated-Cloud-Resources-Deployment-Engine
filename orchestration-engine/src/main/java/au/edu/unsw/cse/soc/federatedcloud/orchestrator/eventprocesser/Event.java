package au.edu.unsw.cse.soc.federatedcloud.orchestrator.eventprocesser;
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

/**
 * User: denis
 * Represent a generic event
 */
public class Event {
    protected int id;
    protected Object context;

    private Event() {
    }

    public Event(int id, Object context) {
        this.id = id;
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public Object getContext() {
        return context;
    }
}
