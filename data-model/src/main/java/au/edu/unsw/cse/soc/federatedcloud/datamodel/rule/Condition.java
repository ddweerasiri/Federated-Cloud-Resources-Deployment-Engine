package au.edu.unsw.cse.soc.federatedcloud.datamodel.rule;
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

/**
* User: denis
* The condition of the rule
*/
public class Condition {
    public List<String> getTaskCategories() {
        return taskCategories;
    }

    public void setTaskCategories(List<String> taskCategories) {
        this.taskCategories = taskCategories;
    }

    private List<String> taskCategories;
}
