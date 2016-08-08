$(document).ready(function(){
    var selects = document.querySelectorAll("select");
    var edgesArr = [];

    //for(var i = 0; i < arr.length; i++){
    //    console.log(arr[i].id);
        //str = arr[i].id;console.log(str);
        var element = document.getElementById("showSetHostName");
        $.ajax({
            type : "POST",
            dataType: 'json',
            contentType: 'application/json',
            url : "showSetHostName",
            success : function(data) {
                for(var j = 0; j < data.length; j++){
                    var option = document.createElement("option");
                    option.setAttribute("id", data[j]);
                    option.innerHTML = data[j];
                    element.appendChild(option);
                }
            },
            error : function(e) {
                console.log('Error: ' + e);
            }
        });

    var element02 = document.getElementById("showSetSwitch");console.log(element);
    $.ajax({
        type : "POST",
        dataType: 'json',
        contentType: 'application/json',
        url : "showSetSwitch",
        success : function(data) {
            for(var j = 0; j < data.length; j++){
                var option = document.createElement("option");
                option.setAttribute("id", data[j]);
                option.innerHTML = data[j];
                element02.appendChild(option);
            }
        },
        error : function(e) {
            console.log('Error: ' + e);
        }
    });

    var element03 = document.getElementById("showSetArrayID");console.log(element);
    $.ajax({
        type : "POST",
        dataType: 'json',
        contentType: 'application/json',
        url : "showSetArrayID",
        success : function(data) {
            for(var j = 0; j < data.length; j++){
                var option = document.createElement("option");
                option.setAttribute("id", data[j]);
                option.innerHTML = data[j];
                element03.appendChild(option);
            }
        },
        error : function(e) {
            console.log('Error: ' + e);
        }
    });

    var element04 = document.getElementById("showKeySetNodesByZoneNameMap");console.log(element);
    $.ajax({
        type : "POST",
        dataType: 'json',
        contentType: 'application/json',
        url : "showKeySetNodesByZoneNameMap",
        success : function(data) {
            for(var j = 0; j < data.length; j++){
                var option = document.createElement("option");
                option.setAttribute("id", data[j]);
                option.innerHTML = data[j];
                element04.appendChild(option);
            }
            createMult();
        },
        error : function(e) {
            console.log('Error: ' + e);
        }

    });

    //$.ajax({
    //    type : "POST",
    //    dataType: 'json',
    //    contentType: 'application/json',
    //    url : "edges",
    //    success : function(edges) {
    //        edgesArr = edges;
    //    },
    //    error : function(e) {
    //        console.log('Error: ' + e);
    //    }
    //});

    //}
    //Create multiselect for tag select
    function createMult() {
        for(var i = 0; i < selects.length; i++){
            $('#' + selects[i].id).multiselect({
                //buttonContainer: '<div></div>',
                includeSelectAllOption: true,
                enableFiltering: true,
                maxHeight: 300,
                //nonSelectedText: selects[i].name,
                minHeight:200,
                buttonWidth: '220px',
                numberDisplayed: 1,
                enableCaseInsensitiveFiltering: false,
                //buttonClass: 'btn btn-link',
                buttonText: function(options, select) {
                    return selects[i].name;
                },
                /*templates: {
                    /!*button: '<span>ZoneName</span>'*!/
                    ul: '<ul class="multiselect-container dropdown-menu"></ul>',
                }*/
                /*templates: {
                    //button: '<button type="button" class="btn btn-link multiselect dropdown-toggle"></button>',
                    ul: '<ul class="multiselect-container dropdown-menu"></ul>',
                    filter: '<li class="multiselect-item filter"><div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
                    filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default multiselect-clear-filter" type="button"><i class="glyphicon glyphicon-remove-circle"></i></button></span>',
                    li: '<li><a href="javascript:void(0);"><label></label></a></li>',
                    divider: '<li class="multiselect-item divider"></li>',
                    liGroup: '<li class="multiselect-item group"><label class="multiselect-group"></label></li>'
                }*/
            });
        }
        //document.getElementById("filter").style.visibility = "visible";
        document.getElementById("mynetwork").style.visibility = "visible";
    };

    $('#btnSelected').click(function () {
        var selected = [];
        var idArr = [];
        for(var i = 0; i < selects.length; i++){
            selected.push($('#' + selects[i].id + ' option:selected'));
        }
        for(var select in selected){
            for(var i = 0; i < selected[select].length; i++) {
                idArr.push(selected[select][i].id);
            }
        }
        $.ajax({
            type : "POST",
            dataType: 'json',
            contentType: 'application/json',
            url : "idArr",
            data: JSON.stringify(idArr),
            success : function(data) {
                console.log(data);
                buildNetwork(data.nodes, data.edges);
            },
            error : function(e) {
                console.log('Error: ' + e);
            }
        });
        console.log(idArr);
    });

});

function buildNetwork(nodes, edges){
    var allNodes;
    var nodesDataset = new vis.DataSet(nodes);
    var edgesDataset = new vis.DataSet(edges);
    var container = document.getElementById('mynetwork');
    var data = {nodes:nodesDataset, edges:edgesDataset};
    var options = {
        nodes: {
            font: {
                size: 16,
                strokeWidth: 0
            },
            borderWidth: 2
        },
        /*"edges": {
            "smooth": {
                "type": "cubicBezier",
                "roundness": 0.8,
                forceDirection: "horizontal"
            },
            color: {
                color: 'black'
            },
            width: 1

        },*/
        "physics": false
    };

    var network = new vis.Network(container, data, options);

    allNodes = nodesDataset.get({returnType:"Object"});

    network.on("click", function (params) {
        params.event = "[original event]";
        document.getElementById('event').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);

        /*if (params.nodes.length > 0) {
            console.log('start');
            highlightActive = true;
            var i,j;
            var selectedNode = params.nodes[0];
            var degrees = 2;

            // mark all nodes as hard to read.
            for (var nodeId in allNodes) {
                allNodes[nodeId].color = 'rgba(200,200,200,0.5)';
                if (allNodes[nodeId].hiddenLabel === undefined) {
                    allNodes[nodeId].hiddenLabel = allNodes[nodeId].label;
                    allNodes[nodeId].label = undefined;
                }
            }
            var connectedNodes = network.getConnectedNodes(selectedNode);
            console.log('connectedNodes ' + connectedNodes);
            var allConnectedNodes = [];

            // get the second degree nodes
            for (i = 1; i < degrees; i++) {
                for (j = 0; j < connectedNodes.length; j++) {
                    allConnectedNodes = allConnectedNodes.concat(network.getConnectedNodes(connectedNodes[j]));
                }
            }
            console.log('allConnectedNodes ' + allConnectedNodes);

            // all second degree nodes get a different color and their label back
            for (i = 0; i < allConnectedNodes.length; i++) {
                allNodes[allConnectedNodes[i]].color = /!*'rgba(150,150,150,0.75)'*!/'red';
                if (allNodes[allConnectedNodes[i]].hiddenLabel !== undefined) {
                    allNodes[allConnectedNodes[i]].label = allNodes[allConnectedNodes[i]].hiddenLabel;
                    allNodes[allConnectedNodes[i]].hiddenLabel = undefined;
                }
            }

            // all first degree nodes get their own color and their label back
            for (i = 0; i < connectedNodes.length; i++) {
                allNodes[connectedNodes[i]].color = 'red';
                if (allNodes[connectedNodes[i]].hiddenLabel !== undefined) {
                    allNodes[connectedNodes[i]].label = allNodes[connectedNodes[i]].hiddenLabel;
                    allNodes[connectedNodes[i]].hiddenLabel = undefined;
                }
            }

            // the main node gets its own color and its label back.
            allNodes[selectedNode].color = 'red';
            if (allNodes[selectedNode].hiddenLabel !== undefined) {
                allNodes[selectedNode].label = allNodes[selectedNode].hiddenLabel;
                allNodes[selectedNode].hiddenLabel = undefined;
            }
        }
        else if (highlightActive === true) {
            // reset all nodes
            for (var nodeId in allNodes) {
                allNodes[nodeId].color = 'black';
                if (allNodes[nodeId].hiddenLabel !== undefined) {
                    allNodes[nodeId].label = allNodes[nodeId].hiddenLabel;
                    allNodes[nodeId].hiddenLabel = undefined;
                }
            }
            highlightActive = false
        }
        // transform the object into an array
        var updateArray = [];
        for (nodeId in allNodes) {
            if (allNodes.hasOwnProperty(nodeId)) {
                updateArray.push(allNodes[nodeId]);
            }
        }
        nodesDataset.update(updateArray);*/

        if (params.nodes.length > 0) {
            highlightActive = true;
            var selectedNode = params.nodes[0]; console.log("--params--");console.log(params);
            var selectedHiddenGroup = allNodes[params.nodes[0]].hiddenGroup;console.log("--selectedHiddenGroup--");console.log(selectedHiddenGroup);
            var allConnectedNodes = [];

            // mark all nodes as hard to read.
            for (var nodeId in allNodes) {
                allNodes[nodeId].hiddenColor = allNodes[nodeId].color;
                allNodes[nodeId].color = 'rgba(200,200,200,0.6)';
                if (allNodes[nodeId].hiddenLabel === undefined) {
                    allNodes[nodeId].hiddenLabel = allNodes[nodeId].label;
                    allNodes[nodeId].label = undefined;
                }
            }
            //console.log("--- allNodes 111 ---");console.log(allNodes);

            //get all nodes from same hiddenGroup
            for(var node in allNodes){
                var temp = allNodes[node].hiddenGroup;
                for(var i = 0; i < selectedHiddenGroup.length; i++){
                    for(var j = 0; j < temp.length; j++){
                        if(temp[j] === selectedHiddenGroup[i] && allConnectedNodes.indexOf(node) === -1){
                            allConnectedNodes.push(node);
                        }
                    }
                }
            }
            //console.log("--allConnectedNodes--");console.log(allConnectedNodes);
            // all selected nodes get their own color and their label back
            for (var i = 0; i < allConnectedNodes.length; i++) {
                allNodes[allConnectedNodes[i]].color = /*allNodes[allConnectedNodes[i]].hiddenColor*/"red";
                if (allNodes[allConnectedNodes[i]].hiddenLabel !== undefined) {
                    allNodes[allConnectedNodes[i]].label = allNodes[allConnectedNodes[i]].hiddenLabel;
                    allNodes[allConnectedNodes[i]].hiddenLabel = undefined;
                }
            }
        }
        else if (highlightActive === true) {
            // reset all nodes
            for (var nodeId in allNodes) {
                allNodes[nodeId].color = /*allNodes[nodeId].hiddenColor*/"black";
                if (allNodes[nodeId].hiddenLabel !== undefined) {
                    allNodes[nodeId].label = allNodes[nodeId].hiddenLabel;
                    allNodes[nodeId].hiddenLabel = undefined;
                }
            }
            highlightActive = false;
        }

        // transform the object into an array
        var updateArray = [];
        for (nodeId in allNodes) {
            if (allNodes.hasOwnProperty(nodeId)) {
                updateArray.push(allNodes[nodeId]);
            }
        }
        nodesDataset.update(updateArray);
    });
}

/*
function neighbourhoodHighlight(params) {
    // if something is selected:
    if (params.nodes.length > 0) {
        highlightActive = true;
        var i,j;
        var selectedNode = params.nodes[0];
        var degrees = 2;

        // mark all nodes as hard to read.
        for (var nodeId in allNodes) {
            allNodes[nodeId].color = 'rgba(200,200,200,0.5)';
            if (allNodes[nodeId].hiddenLabel === undefined) {
                allNodes[nodeId].hiddenLabel = allNodes[nodeId].label;
                allNodes[nodeId].label = undefined;
            }
        }
        var connectedNodes = network.getConnectedNodes(selectedNode);
        var allConnectedNodes = [];

        // get the second degree nodes
        for (i = 1; i < degrees; i++) {
            for (j = 0; j < connectedNodes.length; j++) {
                allConnectedNodes = allConnectedNodes.concat(network.getConnectedNodes(connectedNodes[j]));
            }
        }

        // all second degree nodes get a different color and their label back
        for (i = 0; i < allConnectedNodes.length; i++) {
            allNodes[allConnectedNodes[i]].color = 'rgba(150,150,150,0.75)';
            if (allNodes[allConnectedNodes[i]].hiddenLabel !== undefined) {
                allNodes[allConnectedNodes[i]].label = allNodes[allConnectedNodes[i]].hiddenLabel;
                allNodes[allConnectedNodes[i]].hiddenLabel = undefined;
            }
        }

        // all first degree nodes get their own color and their label back
        for (i = 0; i < connectedNodes.length; i++) {
            allNodes[connectedNodes[i]].color = undefined;
            if (allNodes[connectedNodes[i]].hiddenLabel !== undefined) {
                allNodes[connectedNodes[i]].label = allNodes[connectedNodes[i]].hiddenLabel;
                allNodes[connectedNodes[i]].hiddenLabel = undefined;
            }
        }

        // the main node gets its own color and its label back.
        allNodes[selectedNode].color = undefined;
        if (allNodes[selectedNode].hiddenLabel !== undefined) {
            allNodes[selectedNode].label = allNodes[selectedNode].hiddenLabel;
            allNodes[selectedNode].hiddenLabel = undefined;
        }
    }
    else if (highlightActive === true) {
        // reset all nodes
        for (var nodeId in allNodes) {
            allNodes[nodeId].color = undefined;
            if (allNodes[nodeId].hiddenLabel !== undefined) {
                allNodes[nodeId].label = allNodes[nodeId].hiddenLabel;
                allNodes[nodeId].hiddenLabel = undefined;
            }
        }
        highlightActive = false
    }
    // transform the object into an array
    var updateArray = [];
    for (nodeId in allNodes) {
        if (allNodes.hasOwnProperty(nodeId)) {
            updateArray.push(allNodes[nodeId]);
        }
    }
    nodesDataset.update(updateArray);
}*/
