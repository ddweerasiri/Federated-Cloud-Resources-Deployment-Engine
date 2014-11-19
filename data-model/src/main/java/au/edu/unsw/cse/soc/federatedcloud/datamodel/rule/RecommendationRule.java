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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: denis
 * Represents an Recommendation Rule
 */
public class RecommendationRule {
    private static final Logger log = LoggerFactory.getLogger(RecommendationRule.class);

    private String id;
    private Condition condition;
    private Conclusion conclusion;
    private String exceptNodeId;
    private String falseNodeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }

    public void setConclusion(Conclusion conclusion) {
        this.conclusion = conclusion;
    }

    public String getExceptNodeId() {
        return exceptNodeId;
    }

    public void setExceptNodeId(String exceptNodeId) {
        this.exceptNodeId = exceptNodeId;
    }

    public String getFalseNodeId() {
        return falseNodeId;
    }

    public void setFalseNodeId(String falseNodeId) {
        this.falseNodeId = falseNodeId;
    }

}
