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
$(document).ready(function () {
    $("#searchServiceBaseButton").click(function() {
        alert("should pop up a browseing interface for services, registered in servicebus");
    });

    $("#composeDeploymentButton").click(function() {
        var url = "http://localhost:8080/activiti-explorer/#processmodel/50";
        var win=window.open(url, '_blank');
        win.focus();
    });

    $("#putButton").click(function () {
        createNewResource();
    });
});

var serverURL = "http://localhost:8080/curator-interface-1.0-SNAPSHOT/rest/";
function createNewResource() {
    $.ajax({
        url: serverURL + "curatorAPI/putResource",
        type: "POST",
        dataType: "json",
        async:false,
        contentType: "application/json",
        data: prepareResource(),
        success: function (output) {
            cloudResourceDescriptionId = output.cloudResourceDescriptionId;
            createNewRule(cloudResourceDescriptionId);
            alert("New cloud resource description created.");
        },
        error: function (output) {
            //alert("fail:" + output.statusText);
            console.log("fail:" + output.statusText);
        }
    });
}

function createNewRule(cloudResourceDescriptionId) {
    $.ajax({
        url: serverURL + 'curatorAPI/putRule',
        type: 'POST',
        async:false,
        dataType: 'json',
        contentType: "application/json",
        data: prepareRule(cloudResourceDescriptionId),
        success: function (output) {
            console.log("success");
        },
        error: function (output) {
            //alert("fail:" + output.statusText);
            console.log("fail:" + output.statusText);
        }
    });
}

function prepareRule(cloudResourceDescriptionId) {
    var taskCategories = [];
    $("#taskCategoryList option:selected").each(
        function (index, element) {
            var category = [element.text];
            $.merge(taskCategories, category);
        }
    );
    var condition = {};
    condition.taskCategories = [];

    $(taskCategories).each(function (index) {
        condition.taskCategories.push(taskCategories[index]);
    });

    var conclusion = {};
    conclusion.cloudResourceDescriptionId = cloudResourceDescriptionId;
    var newRule = {};
    newRule.condition = condition;
    newRule.conclusion = conclusion;



    return JSON.stringify(newRule);
}

function prepareResource() {
    var attributes = prepareAttributes();

    console.warn("Adding handlers are still not implemented.");
    var handlers = [];

    var newResource = {};
    newResource.attributes = attributes;
    newResource.handlers = handlers;

    return JSON.stringify(newResource);
}

function prepareAttributes() {
    var attributeString = $("#AttributeArrayTextArea").val();
    //attributeString = '{"attributes":' + attributeString + '}';
    var attributeJson = $.parseJSON(attributeString);

    var resourceName = $("#resourceNameText").val();
    attributeJson.name = resourceName;

    return attributeJson;
}