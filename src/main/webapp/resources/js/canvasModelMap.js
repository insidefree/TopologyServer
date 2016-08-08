function redrawAllModelMap() {
/*$(document).ready(function(){*/
    var nodeList;
    var edgeList;
    $.ajax({
        type : "GET",
        dataType: 'json',
        contentType: 'application/json',
        url : "nodes",
        async: false,
        success : function(data) {
            nodeList = data;
        },
        error : function(e) {
            console.log('Error: ' + e);
        }
    });console.log(nodeList);

    $.ajax({
        type : "GET",
        dataType: 'json',
        contentType: 'application/json',
        url : "edges",
        async: false,
        success : function(data) {
            edgeList = data;
        },
        error : function(e) {
            console.log('Error: ' + e);
        }
    });

    var nodesByLabelMap = new Map();
    var nodesByTypeMap = new Map();
    var nodesByZoneNameMap = new Map();
    var nodesByIDMap = new Map();
    var allNodes;
    createMaps();
    /*console.log("--nodesByLabelMap--");console.log(nodesByLabelMap);
    console.log("--nodesByIDMap--");console.log(nodesByIDMap);*/
    
    
    // create a network
    console.time('create network');
    var nodesDataset = new vis.DataSet();
    var edgesDataset = new vis.DataSet(edgeList);
    var container = document.getElementById('mynetwork');
    var data = {nodes:nodesDataset, edges:edgesDataset};
    var options = {
        nodes: {
            font: {
                size: 16,
                strokeWidth: 0 
            },
            borderWidth: 2,
            shadow:false
        },
        "edges": {
            "smooth": {
                "type": "cubicBezier",
                "roundness": 0.8,
                forceDirection: "horizontal"
            },
//            color: {
//                color: 'black',
//            },
            width: 1
        },
        "physics": false
    };
    var network = new vis.Network(container, data, options);
    console.timeEnd('create network'); 
    
    createSelect();
    
    
    
    function createSelect(){
        var selects = document.querySelectorAll("select");console.log("-selects-");console.log(selects);
        for(var i = 0; i < selects.length; i++){
            if(selects[i].id === "zoneName"){
                var elementZoneName = document.getElementById("zoneName");
                nodesByZoneNameMap.forEach(function(value, key) {
                    var option = document.createElement("option");
                    option.setAttribute("id", key);
                    option.innerHTML = key;
                    elementZoneName.appendChild(option);
                });
            }
            else{
                var nodesBySelectId = nodesByTypeMap.get(selects[i].id);
                for(var j = 0; j < nodesBySelectId.length; j++){
                    var option = document.createElement("option");
                    option.setAttribute("id", nodesBySelectId[j].id);
                    option.innerHTML = nodesBySelectId[j].label;
                    selects[i].appendChild(option);
                }
            }
        }
        //Create multiselect for tag select
        $(function () {
            for(var i = 0; i < selects.length; i++){
                $('#' + selects[i].id).multiselect({
                    includeSelectAllOption: true,
                    enableFiltering: true,
                    maxHeight: 200,
                    buttonWidth: '200px',
                    numberDisplayed: 1,
                    enableCaseInsensitiveFiltering: false
                });
//                $('#' + selects[i].id).dropdownchecklist({
////                    includeSelectAllOption: true
//                    firstItemChecksAll: true
//                });
            }
        });
        
        /* Add object to canvas by ID from selected*/
        $('#btnSelected').click(function () {
            var currentNodesMap = new Map();
//            var currentNodesByTypeMap = new Map();
//            var selectedNodesByTypeMap = new Map();
            var selected = [];
            var allSelectedNodesArr = [];
            var arrayMaps = [];
            for(var i = 0; i < selects.length; i++){
                selected.push($('#' + selects[i].id + ' option:selected'));
            }
            
            for(var select in selected){
                for(var i = 0; i < selected[select].length; i++){
                    var currentNodesByTypeMap = new Map();
                    if(selected[select].selector === "#zoneName option:selected"){
                        var nodes = nodesByZoneNameMap.get(selected[select][i].id);
                        for(var node in nodes){
//                            currentNodesMap.set(nodes[node].id,nodes[node]);
                            if(currentNodesByTypeMap.get(nodes[node].type) === undefined){
                                currentNodesByTypeMap.set(nodes[node].type, [nodes[node]]);
//                                selectedNodesByTypeMap.set(nodes[node].type, [nodes[node]]);
                            }
                            else{
                                var nodesByTypeArr = currentNodesByTypeMap.get(nodes[node].type);
                                if(nodesByTypeArr.indexOf(nodes[node]) === -1){
                                    nodesByTypeArr.push(nodes[node]);
                                    currentNodesByTypeMap.set(nodes[node].type, nodesByTypeArr);
//                                    selectedNodesByTypeMap.set(nodes[node].type, nodesByTypeArr);
                                }
                            }
                        }
                    }
                    else{
                        var obj = (nodesByIDMap.get(parseInt(selected[select][i].id)));
                        var zoneNames = obj.hiddenGroup.split(',');
                        for(var j = 0; j < zoneNames.length; j++){
                            var nodes = nodesByZoneNameMap.get(zoneNames[j]);
                            for(var node in nodes){
//                                currentNodesMap.set(nodes[node].id,nodes[node]);
                                if(currentNodesByTypeMap.get(nodes[node].type) === undefined){
                                    currentNodesByTypeMap.set(nodes[node].type, [nodes[node]]);
//                                    selectedNodesByTypeMap.set(nodes[node].type, [nodes[node]]);
                                }
                                else{
                                    var nodesByTypeArr = currentNodesByTypeMap.get(nodes[node].type);
//                                    var selNodesByTypeArr = selectedNodesByTypeMap.get(nodes[node].type);
                                    if(nodesByTypeArr.indexOf(nodes[node]) === -1){
                                        nodesByTypeArr.push(nodes[node]);
//                                        selNodesByTypeArr.push(nodes[node]);
                                        currentNodesByTypeMap.set(nodes[node].type, nodesByTypeArr);
//                                        selectedNodesByTypeMap.set(nodes[node].type, selNodesByTypeArr);
                                    }
                                }
                            }
                        }
                    }
                    arrayMaps.push(currentNodesByTypeMap);
                }
            }


//            console.log("-currentNodesMap-");console.log(currentNodesMap);
//            console.log("-arrayMaps-");console.log(arrayMaps);
//            console.log("-selectedNodesByTypeMap-");console.log(selectedNodesByTypeMap);
            var yHost = 0;
            var yHbaWWN = 0;
            var ySwitch = 0;
            var yPortH = 0;
            var yPortA = 0;
            var yArrayPort = 0;
            var yArrayID = 0;
            var yArrayIDerror = 0;
            var hbaWWNArr = [];
            var hbaWWNcount;
            var hostArr = [];
            var switchArr = [];
            var portHArr = [];
            var portAArr = [];
            var arrayPortArr = [];
            var arrayPortMap = new Map();
            var arrayIDArr = [];
            for(var map in arrayMaps){
//                var hbaWWNArr = arrayMaps[map].get("HbaWWN");
//                for(var hbaWWN in hbaWWNArr){
//                    yHost+=yHbaWWN;
//                    hbaWWNArr[hbaWWN].x = -450;
//                    hbaWWNArr[hbaWWN].y = yHbaWWN;
//                    hbaWWNArr[hbaWWN].fixed = {x: true, y: true};
//                    hbaWWNArr[hbaWWN].color = "#F2994B";
//                    hbaWWNArr[hbaWWN].shape = "box";
//                    yHbaWWN += 50;
//                    allSelectedNodesArr.push(hbaWWNArr[hbaWWN]);
//                }
//                var hostArr = arrayMaps[map].get("HostName");
//                for(var host in hostArr){
//                    hostArr[host].x = -600;
//                    hostArr[host].y = yHost/hbaWWNArr.length;
//                    hostArr[host].fixed = {x: true, y: true};
//                    hostArr[host].color = "#F24B0F";
//                    hostArr[host].shape = "square";
//                    hostArr[host].size = 20;
//                    allSelectedNodesArr.push(hostArr[host]);
//                }
//                yHost = 0;
                
                var hbaWWNArrTemp = arrayMaps[map].get("HbaWWN");
                for(var hbaWWN in hbaWWNArrTemp){
//                    var temp = hbaWWNArrTemp[hbaWWN].index.split(" ");
                    if(hbaWWNArr.indexOf(hbaWWNArrTemp[hbaWWN]) === -1){
                        hbaWWNArr.push(hbaWWNArrTemp[hbaWWN]);
//                            arrayPortMap.set(arrayID.label, arrayPortArr);
                    }
                }
                hbaWWNcount = hbaWWNArr.length;
                
                var hostArrTemp = arrayMaps[map].get("HostName");
                for(var host in hostArrTemp){
                    if(hostArr.indexOf(hostArrTemp[host]) === -1){
                        hostArr.push(hostArrTemp[host]);
                    }
                }
                
                var switchArrTemp = arrayMaps[map].get("Switch");
                for(var sw in switchArrTemp){
                    if(switchArr.indexOf(switchArrTemp[sw]) === -1){
                        switchArr.push(switchArrTemp[sw]);
                    }
                }
                
                var portHTemp = arrayMaps[map].get("PortH");
                for(var portH in portHTemp){
                    if(portHArr.indexOf(portHTemp[portH]) === -1){
                        portHArr.push(portHTemp[portH]);
                    }
                }
                
                var portATemp = arrayMaps[map].get("PortA");
                for(var portA in portATemp){
                    if(portAArr.indexOf(portATemp[portA]) === -1){
                        portAArr.push(portATemp[portA]);
                    }
                }
                
                var arrayPortArrTemp = arrayMaps[map].get("ArrayPort");
                for(var arrayPort in arrayPortArrTemp){
//                    var temp = arrayPortArrTemp[arrayPort].index.split(" ");
                    if(arrayPortArr.indexOf(arrayPortArrTemp[arrayPort]) === -1){
                        arrayPortArr.push(arrayPortArrTemp[arrayPort]);
//                            arrayPortMap.set(arrayID.label, arrayPortArr);
                    }
                }
                var arrayIDArrTemp = arrayMaps[map].get("ArrayID");
                for(var arrayID in arrayIDArrTemp){
                    if(arrayIDArr.indexOf(arrayIDArrTemp[arrayID]) === -1){
                        arrayIDArr.push(arrayIDArrTemp[arrayID]);
                    }
                }
            }
            console.log("-hbaWWNArr-");console.log(hbaWWNArr);
            var arrMapForHostAndHbaWWN = [];
            for(var host in hostArr){
                var tempMap = new Map();
                var hbas = [];
                for(var hba in hbaWWNArr){
                    var temp = hbaWWNArr[hba].index.split(",");
                    if(hostArr[host].label === temp[1]){
                        hbas.push(hbaWWNArr[hba]);
                    }
                }
                tempMap.set("HostName", hostArr[host]);
                console.log("-tempMap.get(HostName)-");console.log(tempMap.get("HostName"));
                tempMap.set("HbaWWN", hbas);
                console.log("-tempMap.get(HbaWWN)-");console.log(tempMap.get("HbaWWN"));
                arrMapForHostAndHbaWWN.push(tempMap);
                console.log("-tempMap-");console.log(tempMap);
            }
            
            for(var map in arrMapForHostAndHbaWWN){
                var hbaWWNArr = arrMapForHostAndHbaWWN[map].get("HbaWWN");
                for(var hbaWWN in hbaWWNArr){
                    yHost+=yHbaWWN;
                    hbaWWNArr[hbaWWN].x = -450;
                    hbaWWNArr[hbaWWN].y = yHbaWWN;
                    hbaWWNArr[hbaWWN].fixed = {x: true, y: true};
                    hbaWWNArr[hbaWWN].color = "#F2994B";
                    hbaWWNArr[hbaWWN].shape = "box";
                    yHbaWWN += 60;
                    allSelectedNodesArr.push(hbaWWNArr[hbaWWN]);
                }
                var hostArr = [];
                hostArr.push(arrMapForHostAndHbaWWN[map].get("HostName"));
                for(var host in hostArr){
                    hostArr[host].x = -600;
                    hostArr[host].y = yHost/hbaWWNArr.length;
                    hostArr[host].fixed = {x: true, y: true};
                    hostArr[host].color = "#F24B0F";
                    hostArr[host].shape = "square";
                    hostArr[host].size = 20;
                    allSelectedNodesArr.push(hostArr[host]);
                }
                yHost = 0;
            }
            /////////////////////////////////////
//            ySwitch = (yHbaWWN - 50)/(switchArr.length + 1);
//            var ySwitchStep = ySwitch;
//            for(var sw in switchArr){
//                    switchArr[sw].x = 0;
//                    switchArr[sw].y = ySwitch;
//                    switchArr[sw].fixed = {x: true, y: true};
//                    switchArr[sw].color = "#F2CF66";
//                    switchArr[sw].shape = "box";
//                    allSelectedNodesArr.push(switchArr[sw]);
//                    ySwitch+=ySwitchStep;
//            }
            
            var arrMapForSwitchandPortHandPortA = [];
            for(var sw in switchArr){
                var tempMap = new Map();
                var arrayPortH = [];
                var arrayPortA = [];
                for(var portH in portHArr){
                    var temp = portHArr[portH].index.split(" ");
                    if(switchArr[sw].label === temp[1]){
                        arrayPortH.push(portHArr[portH]);
                    }
                }
                for(var portA in portAArr){
                    var temp = portAArr[portA].index.split(" ");
                    if(switchArr[sw].label === temp[1]){
                        arrayPortA.push(portAArr[portA]);
                    }
                }
                tempMap.set("Switch", switchArr[sw]);
//                console.log("-tempMap.get(ArrayID)-");console.log(tempMap.get("ArrayID"));
                tempMap.set("PortH", arrayPortH);
//                console.log("-tempMap.get(ArrayPort)-");console.log(tempMap.get("ArrayPort"));
                tempMap.set("PortA", arrayPortA);
                arrMapForSwitchandPortHandPortA.push(tempMap);
//                console.log("-tempMap-");console.log(tempMap);
            }
            
            var arrMapForArrayIDandArrayPort = [];
            for(var arrayID in arrayIDArr){
                var tempMap = new Map();
                var arrayPorts = [];
                for(var arrayPort in arrayPortArr){
                    var temp = arrayPortArr[arrayPort].index.split(" ");
                    if(arrayIDArr[arrayID].label === temp[1]){
                        arrayPorts.push(arrayPortArr[arrayPort]);
                    }
                }
                tempMap.set("ArrayID", arrayIDArr[arrayID]);
//                console.log("-tempMap.get(ArrayID)-");console.log(tempMap.get("ArrayID"));
                tempMap.set("ArrayPort", arrayPorts);
//                console.log("-tempMap.get(ArrayPort)-");console.log(tempMap.get("ArrayPort"));
                arrMapForArrayIDandArrayPort.push(tempMap);
//                console.log("-tempMap-");console.log(tempMap);
            }
            
            
//            console.log("-arrMapForArrayIDandArrayPort-");console.log(arrMapForArrayIDandArrayPort);
            var yArrayPortStep = (yHbaWWN + 60)/(arrayPortArr.length);
//            var yArrayPortStep = yArrayPort;
            for(var map in arrMapForArrayIDandArrayPort){
                var arrayPortArr = arrMapForArrayIDandArrayPort[map].get("ArrayPort");
                for(var arrayPort in arrayPortArr){
                    yArrayID += yArrayPort;
                    arrayPortArr[arrayPort].x = 350;
                    arrayPortArr[arrayPort].y = yArrayPort;
                    arrayPortArr[arrayPort].fixed = {x: true, y: true};
                    arrayPortArr[arrayPort].color = "#0494D9";
                    arrayPortArr[arrayPort].shape = "ellipse";
                    allSelectedNodesArr.push(arrayPortArr[arrayPort]);
                    yArrayPort+=yArrayPortStep;
                }
                
//                yArrayID = (yHbaWWN - 50)/(arrayIDArr.length + 1);
//                var yArrayIDStep = yArrayID;
                var arrayIDArr = [];
                arrayIDArr.push(arrMapForArrayIDandArrayPort[map].get("ArrayID"));//console.log("-arrayIDArr-");console.log(arrayIDArr);
                for(var arrayID in arrayIDArr){
                    //Catch error in scheme, if arrayPort == 0 than arrayID doesn't connect
                    if(arrayPortArr.length !== 0){
                        arrayIDArr[arrayID].x = 500;
                        arrayIDArr[arrayID].y = yArrayID/arrayPortArr.length;
                        arrayIDArr[arrayID].fixed = {x: true, y: true};
                        arrayIDArr[arrayID].color = "#0387B0";
                        arrayIDArr[arrayID].shape = "database";
                        allSelectedNodesArr.push(arrayIDArr[arrayID]);
                    }else{
                        arrayIDArr[arrayID].x = 700;
                        arrayIDArr[arrayID].y = yArrayIDerror;
                        arrayIDArr[arrayID].fixed = {x: true, y: true};
                        arrayIDArr[arrayID].color = "red";
                        arrayIDArr[arrayID].shape = "database";
                        allSelectedNodesArr.push(arrayIDArr[arrayID]);
                        yArrayIDerror += 300;
                    }
                }
                yArrayID = 0;
            }
            
            for(var map in arrMapForSwitchandPortHandPortA){
                var switchArr = [];
                switchArr.push(arrMapForSwitchandPortHandPortA[map].get("Switch"));
                var portHArr = arrMapForSwitchandPortHandPortA[map].get("PortH");
                var portAArr = arrMapForSwitchandPortHandPortA[map].get("PortA");
                var portHStep = ((yHbaWWN - 60)/2)/(portHArr.length);
                var portAStep = ((yHbaWWN - 60)/2)/(portAArr.length);
                for(var sw in switchArr){
                    var swForChangeLabel = Object.assign({}, switchArr[sw]);
                    allSelectedNodesArr.push(swForChangeLabel);
                    
                    for(var portH in portHArr){
                        ySwitch += yPortH;
                        portHArr[portH].x = -75;
                        portHArr[portH].y = yPortH;
                        portHArr[portH].fixed = {x: true, y: true};
                        portHArr[portH].color = "#1D2956";
                        portHArr[portH].shape = "dot";
                        portHArr[portH].size = 7;
                        allSelectedNodesArr.push(portHArr[portH]);
                        yPortH += portHStep;
                    }
                    for(var portA in portAArr){
                        portAArr[portA].x = 75;
                        portAArr[portA].y = yPortA;
                        portAArr[portA].fixed = {x: true, y: true};
                        portAArr[portA].color = "#1D2956";
                        portAArr[portA].shape = "dot";
                        portAArr[portA].size = 7;
                        allSelectedNodesArr.push(portAArr[portA]);
                        yPortA += portAStep;
                    }
                    yPortH += 60;
                    yPortA += 60;
                    
                    swForChangeLabel.x = 0;
                    swForChangeLabel.y = ySwitch/portHArr.length;
                    swForChangeLabel.fixed = {x: true, y: true};
                    swForChangeLabel.color = "#F2CF66";
                    swForChangeLabel.shape = "box";
                    console.log("swForChangeLabel");console.log(swForChangeLabel);
                    for(var i = 0; i < (hbaWWNcount/2); i++){
                        swForChangeLabel.label += "\n\n\n";
                    }
//                    allSelectedNodesArr.push(swForChangeLabel);
                    console.log("switchArr[sw]");console.log(switchArr[sw]);
                    ySwitch = 0;
                }
            }
            console.log("-allSelectedNodesArr-");console.log(allSelectedNodesArr);
            nodesDataset = new vis.DataSet(allSelectedNodesArr);
            data = {nodes:nodesDataset, edges:edgesDataset};
            network = new vis.Network(container, data, options);
            // get a JSON object for neighbourhoodHighlight
            allNodes = nodesDataset.get({returnType:"Object"});
            console.log("--- allNodes ---");
            console.log(allNodes);
            //network.on("click",neighbourhoodHighlight);
            
        });
        
        $('#resetAll').click(function (){
            if (network !== null) {
                network.destroy();
                network = null;
            }
            nodesDataset = new vis.DataSet();
            nodesDataset.add({id:99999, label:"SELECT HOST my temp ID is 99999!", x: -600, y: 0, fixed: true,  shape: 'box', font:{size:20}});
            data = {nodes: nodesDataset, edges: edgesDataset};
            network = new vis.Network(container, data, options);
        });
    }
    
    function createStartArea(){
//        var zone = nodesByZoneNameMap.get()
    }
    
    function createMaps(){
        console.time("-maps creation-");
        var nodesByhostNameArr = [],
            nodesByhbaWWNArr = [],
            nodesByportHArr = [],
            nodesByswitchArr = [],
            nodesByportAArr = [],
            nodesByarrayPortArr = [],
            nodesByarrayIDArr = [],
            zoneNameArr = [];
    
        for(var node in nodeList){
            //Add node to nodesByZoneNameMap where key is zoneName
            zoneNameArr = nodeList[node].hiddenGroup.split(',');
            for(var i = 0; i < zoneNameArr.length; i++) {
                if(nodesByZoneNameMap.get(zoneNameArr[i]) === undefined){
                    nodesByZoneNameMap.set(zoneNameArr[i], [nodeList[node]]);
                }else{
                    var nodesByZoneNameArr = nodesByZoneNameMap.get(zoneNameArr[i]);
                    nodesByZoneNameArr.push(nodeList[node]);
                    nodesByZoneNameMap.set(zoneNameArr[i], nodesByZoneNameArr);
                }
            }
            //Add node to nodesByLabelMap where key is label.. WE MUST DO CHECK not same label!!!!
            nodesByLabelMap.set(nodeList[node].label, nodeList[node]);
            //Add node to nodesByIDMap where key is id..
            nodesByIDMap.set(nodeList[node].id, nodeList[node]);
            
            //Add node to nodesByTypeMap where key is type
            switch(nodeList[node].type){
                case "HostName":
                    nodesByhostNameArr.push(nodeList[node]);
                    nodesByTypeMap.set("hostName", nodesByhostNameArr);
                    break;
                case "HbaWWN": 
                    nodesByhbaWWNArr.push(nodeList[node]);
                    nodesByTypeMap.set("hbaWWN", nodesByhbaWWNArr); break;
                case "PortH": 
                    nodesByportHArr.push(nodeList[node]);
                    nodesByTypeMap.set("portH", nodesByportHArr); break;
                case "PortA": 
                    nodesByportAArr.push(nodeList[node]);
                    nodesByTypeMap.set("portA", nodesByportAArr); break;
                case "Switch": 
                    nodesByswitchArr.push(nodeList[node]);
                    nodesByTypeMap.set("switch", nodesByswitchArr); break;
                case "ArrayPort": 
                    nodesByarrayPortArr.push(nodeList[node]);
                    nodesByTypeMap.set("arrayPort", nodesByarrayPortArr); break;
                case "ArrayID": 
                    nodesByarrayIDArr.push(nodeList[node]);
                    nodesByTypeMap.set("arrayID", nodesByarrayIDArr); break;
            }
        }
        console.timeEnd("-maps creation-");
    }
    
    function layoutNetwork(){
        
    }
    
    //function neighbourhoodHighlight(params) {
    //    // if something is selected:
    //    console.log(params.nodes.length);
    //    if (params.nodes.length > 0) {
    //        highlightActive = true;
    //        var selectedNode = params.nodes[0]; console.log("--selectedNode--");console.log(selectedNode);
    //        var selectedHiddenGroup = allNodes[params.nodes[0]].hiddenGroup.split(',');console.log("--selectedHiddenGroup--");console.log(selectedHiddenGroup);
    //        var allConnectedNodes = [];
    //
    //        // mark all nodes as hard to read.
    //        for (var nodeId in allNodes) {
    //            allNodes[nodeId].hiddenColor = allNodes[nodeId].color;
    //            allNodes[nodeId].color = 'rgba(255,255,255,0)';
    //            if (allNodes[nodeId].hiddenLabel === undefined) {
    //                allNodes[nodeId].hiddenLabel = allNodes[nodeId].label;
    //                allNodes[nodeId].label = undefined;
    //            }
    //        }
    //        console.log("--- allNodes 111 ---");console.log(allNodes);
    //
    //        //get all nodes from same hiddenGroup
    //        for(var node in allNodes){
    //            var temp = allNodes[node].hiddenGroup.split(',');
    //            for(var i = 0; i < selectedHiddenGroup.length; i++){
    //                for(var j = 0; j < temp.length; j++){
    //                    if(temp[j] === selectedHiddenGroup[i] && allConnectedNodes.indexOf(node) === -1){
    //                        allConnectedNodes.push(node);
    //                    }
    //                }
    //            }
    //        }
    //        console.log("--allConnectedNodes--");console.log(allConnectedNodes);
    //        // all selected nodes get their own color and their label back
    //        for (var i = 0; i < allConnectedNodes.length; i++) {
    //            allNodes[allConnectedNodes[i]].color = allNodes[allConnectedNodes[i]].hiddenColor;
    //            if (allNodes[allConnectedNodes[i]].hiddenLabel !== undefined) {
    //                allNodes[allConnectedNodes[i]].label = allNodes[allConnectedNodes[i]].hiddenLabel;
    //                allNodes[allConnectedNodes[i]].hiddenLabel = undefined;
    //            }
    //        }
    //    }
    //    else if (highlightActive === true) {
    //        // reset all nodes
    //        for (var nodeId in allNodes) {
    //            allNodes[nodeId].color = allNodes[nodeId].hiddenColor;
    //            if (allNodes[nodeId].hiddenLabel !== undefined) {
    //                allNodes[nodeId].label = allNodes[nodeId].hiddenLabel;
    //                allNodes[nodeId].hiddenLabel = undefined;
    //            }
    //        }
    //        highlightActive = false;
    //    }
    //
    //    // transform the object into an array
    //    var updateArray = [];
    //    for (nodeId in allNodes) {
    //        if (allNodes.hasOwnProperty(nodeId)) {
    //            updateArray.push(allNodes[nodeId]);
    //        }
    //    }
    //    nodesDataset.update(updateArray);
    //}
}