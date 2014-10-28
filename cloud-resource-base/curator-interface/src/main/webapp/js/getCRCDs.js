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
var serverURL = "http://localhost:8080/curator-interface-1.0-SNAPSHOT/rest/";
$( document ).ready(function() {
    $("#searchButton").click(function(){
        var selectedCategory = $("#taskCategoryList option:selected").text();
        $("#ResultantListTextArea").val(""); //clear the test area
        $.ajax({
            url: serverURL + "curatorAPI/getResource",
            type:'GET',
            dataType: 'json',
            data: "category="+selectedCategory,
            async:false,
            contentType: "application/json",
            success: function(output){
                $("#ResultantListTextArea").val(JSON.stringify(output, undefined, 2));
            },
            error: function(output) {
                alert("fail");
                console.log("fail");
            }
        });

    });
});

