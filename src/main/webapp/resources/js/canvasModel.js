function redrawAllModel(nodeList, edgeList) {
    
    var network;
    var allNodes;
    var highlightActive = false;
    var startNodes = [];
    var arraysID = [];
    font = {};
    var flagArrayID = true;
    var flagSwitch = true;
    var arrayIDy = 150;
    var switchY = 300;
    var switches = [];
    for(var node in nodeList){
        if(nodeList[node].type === "ArrayID"){
            nodeList[node].shape = "database";
            nodeList[node].x = 700;
            nodeList[node].color = "green";
            if(flagArrayID){
                nodeList[node].y = arrayIDy;
                flagArrayID = false;
            }else{
                nodeList[node].y = arrayIDy * (-1);
                arrayIDy += 300;
                flagArrayID = true;
            }
            nodeList[node].fixed = {x:true, y:true};
            font.size = 10;
            nodeList[node].font = font;
            startNodes.push(nodeList[node]);
            arraysID.push(nodeList[node].id);
            
        }
        if(nodeList[node].type === "Switch"){
            nodeList[node].shape = "box";
            nodeList[node].fixed = {x:true, y:true};
            nodeList[node].x = 0;
            if(flagSwitch){
                nodeList[node].y = switchY;
                flagSwitch = false;
            }else{
                nodeList[node].y = switchY * (-1);
                switchY += 1000;
                flagSwitch = true;
            }
            switches.push(nodeList[node]);
            startNodes.push(nodeList[node]);
        }
        
    }
    var nodesDataset = new vis.DataSet(startNodes);
    var edgesDataset = new vis.DataSet();
    var allNodesDataset = new vis.DataSet(nodeList);
    var allEdgesDataset = new vis.DataSet(edgeList);
    nodesDataset.add({id:99999, label:"SELECT HOST my temp ID is 99999!", x: -600, y: 0, fixed: true,  shape: 'box', font:{size:20}});
    
    console.log(nodesDataset);
    console.log(edgesDataset);
    
    // create a network
    var container = document.getElementById('mynetwork');
    console.time('create data');
    var data = {nodes:nodesDataset, edges:edgesDataset};
    console.timeEnd('create data'); 
    
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

    console.time('create graph');
    network = new vis.Network(container, data, options);
    console.timeEnd('create graph');
    
    createSelect();
    
    
    function createSelect(){
        var elementHost = document.getElementById("lstHost");
        var elementHbaWWN = document.getElementById("lstHbaWWN");
        var elementSwitchPortH = document.getElementById("lstSwitchPortH");
        var elementSwitchPortA = document.getElementById("lstSwitchPortA");
        var elementArrayPort = document.getElementById("lstArrayPort");
        var elementArrayID = document.getElementById("lstArrayID");
        for(var i = 0; i < nodeList.length; i++){
            if(nodeList[i].type == 'HostName'){
                var option = document.createElement("option");
                option.setAttribute("id", nodeList[i].id.toString());
                option.innerHTML = nodeList[i].label; //console.log(option);
                elementHost.appendChild(option);
            }
            if(nodeList[i].type == 'HbaWWN'){
                var option = document.createElement("option");
                option.setAttribute("id", nodeList[i].id.toString());
                option.innerHTML = nodeList[i].label; //console.log(option);
                elementHbaWWN.appendChild(option);
            }
            if(nodeList[i].type == 'PortH'){
                var option = document.createElement("option");
                option.setAttribute("id", nodeList[i].id.toString());
                option.innerHTML = nodeList[i].label; //console.log(option);
                elementSwitchPortH.appendChild(option);
            }
            if(nodeList[i].type == 'PortA'){
                var option = document.createElement("option");
                option.setAttribute("id", nodeList[i].id.toString());
                option.innerHTML = nodeList[i].label; //console.log(option);
                elementSwitchPortA.appendChild(option);
            }
            if(nodeList[i].type == 'ArrayPort'){
                var option = document.createElement("option");
                option.setAttribute("id", nodeList[i].id.toString());
                option.innerHTML = nodeList[i].label; //console.log(option);
                elementArrayPort.appendChild(option);
            }
            if(nodeList[i].type == 'ArrayID'){
                var option = document.createElement("option");
                option.setAttribute("id", nodeList[i].id.toString());
                option.innerHTML = nodeList[i].label; //console.log(option);
                elementArrayID.appendChild(option);
            }
        }
        $(function () {
            $('#lstHost').multiselect({
                includeSelectAllOption: true
            });
            $('#lstHbaWWN').multiselect({
                includeSelectAllOption: true
            });
            $('#lstSwitchPortH').multiselect({
                includeSelectAllOption: true
            });
            $('#lstSwitchPortA').multiselect({
                includeSelectAllOption: true
            });
            $('#lstArrayPort').multiselect({
                includeSelectAllOption: true
            });
            $('#lstArrayID').multiselect({
                includeSelectAllOption: true
            });
        });

        $('#btnSelected').click(function () {
            nodesDataset.remove(99999);
            var selected = [];
            selected.push($("#lstHost option:selected")); 
            selected.push($("#lstHbaWWN option:selected"));
            selected.push($("#lstSwitchPortH option:selected"));
            selected.push($("#lstSwitchPortA option:selected"));
            selected.push($("#lstArrayPort option:selected"));
            selected.push($("#lstArrayID option:selected"));
//            selected.addBack($("#lstHbaWWNs option:selected"));
//            console.log("--selected--");console.log(selected);
            var ids = [];
            var hiddenGroup = [];
//            var tempNodesDataset = new vis.DataSet();
            //var selected = $("#lstHbaWWNs option:selected");console.log("--selected--");console.log(selected);
            
            /* Get IDs from selected */
            for(var select in selected){
                for(var i = 0; i < selected[select].length; i++){
                    ids.push(selected[select][i].id);
                }   
            }
            console.log("--IDS--");
            console.log(ids);
            
            /*Get all hiddenGroups in order from nodeList*/
            for(var i = 0; i < ids.length; i++){
                hiddenGroup = hiddenGroup.concat(nodeList[ids[i]-1].hiddenGroup.split(','));
//                hiddenGroup.push(nodeList[ids[i]-1].hiddenGroup.split(','));
                
            }
            console.log("--hiddenGroup--");
            console.log(hiddenGroup);
            /*Add nodes by hiddenGroup*/
            for(var node in nodeList){
                var temp = nodeList[node].hiddenGroup.split(',');
                for(var i = 0; i < hiddenGroup.length; i++){
                    for(var j = 0; j < temp.length; j++){
                        if(hiddenGroup[i] == temp[j]){
                            try{
//                                tempNodesDataset.add(nodeList[node]);
                                  nodesDataset.add(nodeList[node]);
                            }catch(error){}
                            break;
                        }
                    }
                }
            }
            
            console.log(nodesDataset);
            
            /*Layout model*/
            var hostY = 0;
            var portHy = 25;
            var portAy = 25;
            var flagHost = true;
            var zoneName = [];
            for(var node in nodesDataset._data){
                if(nodesDataset._data[node].type === "HostName"){
                    nodesDataset._data[node].shape = 'square';
                    nodesDataset._data[node].fixed = {x:true, y:true};
                    nodesDataset._data[node].x = -700;
                    nodesDataset._data[node].size = 20;
                    nodesDataset._data[node].color = "red";
                    zoneName = nodesDataset._data[node].hiddenGroup.split(',');
                    
                    if(flagHost){
                        var flagHBA = true;
                        nodesDataset._data[node].y = hostY;
                        var hbaY = 15;
                        for(var nodeHBA in nodesDataset._data){
                            if(nodesDataset._data[nodeHBA].type === "HbaWWN"){
                                for(var i = 0; i < zoneName.length; i++){
                                    if(nodesDataset._data[nodeHBA].hiddenGroup === zoneName[i]){
                                        nodesDataset._data[nodeHBA].shape = "box";
                                        nodesDataset._data[nodeHBA].fixed = {x:true, y:true};
                                        nodesDataset._data[nodeHBA].x = -550;
                                        nodesDataset._data[nodeHBA].color = "blue";
                                        if(flagHBA){
                                            nodesDataset._data[nodeHBA].y = nodesDataset._data[node].y + hbaY;
                                            flagHBA = false;
                                        }else{
                                            nodesDataset._data[nodeHBA].y = nodesDataset._data[node].y + hbaY * (-1);
                                            hbaY += 30;
                                            flagHBA = true;
                                        }
                                    }
                                }
                            }
                        }
                        flagHost = false;
                    }else{
                        hostY += 300;
                        nodesDataset._data[node].y = hostY * (-1);
                        var flagHBA = true;
                        var hbaY = 15;
                        for(var nodeHBA in nodesDataset._data){
                            if(nodesDataset._data[nodeHBA].type === "HbaWWN"){
                                for(var i = 0; i < zoneName.length; i++){
                                    if(nodesDataset._data[nodeHBA].hiddenGroup === zoneName[i]){
                                        nodesDataset._data[nodeHBA].shape = "box";
                                        nodesDataset._data[nodeHBA].fixed = {x:true, y:true};
                                        nodesDataset._data[nodeHBA].x = -550;
                                        nodesDataset._data[nodeHBA].color = "blue";
                                        if(flagHBA){
                                            nodesDataset._data[nodeHBA].y = nodesDataset._data[node].y + hbaY;
                                            flagHBA = false;
                                        }else{
                                            nodesDataset._data[nodeHBA].y = nodesDataset._data[node].y + hbaY * (-1);
                                            hbaY += 30;
                                            flagHBA = true;
                                        }
                                    }
                                }
                            }
                        }
                        flagHost = true;
                    }
                }
                if(nodesDataset._data[node].type === "ArrayID"){
                    var flagArrayPort = true;
                    var arrayPortY = 30;
                    zoneName = nodesDataset._data[node].hiddenGroup.split(',');
                    for(var nodeArrayPort in nodesDataset._data){
                        if(nodesDataset._data[nodeArrayPort].type === "ArrayPort"){
                            for(var i = 0; i < zoneName.length; i++){
                                if(nodesDataset._data[nodeArrayPort].hiddenGroup === zoneName[i]){
                                    nodesDataset._data[nodeArrayPort].fixed = {x:true, y:true};
                                    nodesDataset._data[nodeArrayPort].x = 600;
                                    nodesDataset._data[nodeArrayPort].color = "orange";
                                    if(flagArrayPort){
                                        nodesDataset._data[nodeArrayPort].y = nodesDataset._data[node].y + arrayPortY;
                                        flagArrayPort = false;
                                    }else{
                                        nodesDataset._data[nodeArrayPort].y = nodesDataset._data[node].y + arrayPortY * (-1);
                                        arrayPortY += 60;
                                        flagArrayPort = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Setup coordinates for ports
            var flagPortH = true;
            var flagPortA = true;
            for(var i = 0; i < switches.length; i++){
                for(var node in nodesDataset._data){
                    var portBySwitch = nodesDataset._data[node].index.split(' ');
                    if(portBySwitch[1] === switches[i].label){
                        if(nodesDataset._data[node].type === "PortH"){
                            nodesDataset._data[switches[i].id].label += '\n\n';
                            nodesDataset._data[node].shape = "dot";
                            nodesDataset._data[node].size = 10;
                            nodesDataset._data[node].fixed = {x:true, y:true};
                            nodesDataset._data[node].x = -75;
                            nodesDataset._data[node].color = "yellow";
                            if(flagPortH){
                                nodesDataset._data[node].y = switches[i].y + portHy;
                                flagPortH = false;
                            }else{
                                nodesDataset._data[node].y = switches[i].y + portHy * (-1);
                                portHy += 50;
                                flagPortH = true;
                            }
                        }
                        if(nodesDataset._data[node].type === "PortA"){
                            nodesDataset._data[switches[i].id].label += '\n\n';
                            nodesDataset._data[node].shape = "dot";
                            nodesDataset._data[node].size = 10;
                            nodesDataset._data[node].fixed = {x:true, y:true};
                            nodesDataset._data[node].x = 75;
                            nodesDataset._data[node].color = "yellow";
                            
                            if(flagPortA){
                                nodesDataset._data[node].y = switches[i].y + portAy;
                                flagPortA = false;
                            }else{
                                nodesDataset._data[node].y = switches[i].y + portAy * (-1);
                                portAy += 50;
                                flagPortA = true;
                            }
                        }
                    }
                }
                portHy = 25;
                portAy = 25;
            }

            data = {nodes: nodesDataset, edges: allEdgesDataset};
            network = new vis.Network(container, data, options);
            
            // get a JSON object for neighbourhoodHighlight
            allNodes = nodesDataset.get({returnType:"Object"});
            console.log("--- allNodes ---");
            console.log(allNodes);
            network.on("click",neighbourhoodHighlight);
            
        });
        
        $('#resetAll').click(function (){
            if (network !== null) {
                network.destroy();
                network = null;
            }
            nodesDataset = new vis.DataSet(startNodes);
            nodesDataset.add({id:99999, label:"SELECT HOST my temp ID is 99999!", x: -600, y: 0, fixed: true,  shape: 'box', font:{size:20}});
            data = {nodes: nodesDataset, edges: edgesDataset};
            network = new vis.Network(container, data, options);
            hostY = 0;
            portHy = 25;
            portAy = 25;
            flagHost = true;
            flagPortH = true;
            flagPortA = true;
        });
    }
    
    function neighbourhoodHighlight(params) {
        // if something is selected:
        console.log(params.nodes.length);
        if (params.nodes.length > 0) {
            highlightActive = true;
            var selectedNode = params.nodes[0]; console.log("--selectedNode--");console.log(selectedNode);
            var selectedHiddenGroup = allNodes[params.nodes[0]].hiddenGroup.split(',');console.log("--selectedHiddenGroup--");console.log(selectedHiddenGroup);
            var allConnectedNodes = [];
            
            // mark all nodes as hard to read.
            for (var nodeId in allNodes) {
                allNodes[nodeId].hiddenColor = allNodes[nodeId].color;
                allNodes[nodeId].color = 'rgba(255,255,255,0)';
                if (allNodes[nodeId].hiddenLabel === undefined) {
                    allNodes[nodeId].hiddenLabel = allNodes[nodeId].label;
                    allNodes[nodeId].label = undefined;
                }
            }
            console.log("--- allNodes 111 ---");console.log(allNodes);
            
            //get all nodes from same hiddenGroup
            for(var node in allNodes){
                var temp = allNodes[node].hiddenGroup.split(',');
                for(var i = 0; i < selectedHiddenGroup.length; i++){
                    for(var j = 0; j < temp.length; j++){
                        if(temp[j] === selectedHiddenGroup[i] && allConnectedNodes.indexOf(node) === -1){
                            allConnectedNodes.push(node);
                        }
                    }
                }
            }
            console.log("--allConnectedNodes--");console.log(allConnectedNodes);
            // all selected nodes get their own color and their label back
            for (var i = 0; i < allConnectedNodes.length; i++) {
                allNodes[allConnectedNodes[i]].color = allNodes[allConnectedNodes[i]].hiddenColor;
                if (allNodes[allConnectedNodes[i]].hiddenLabel !== undefined) {
                    allNodes[allConnectedNodes[i]].label = allNodes[allConnectedNodes[i]].hiddenLabel;
                    allNodes[allConnectedNodes[i]].hiddenLabel = undefined;
                }
            }
        }
        else if (highlightActive === true) {
            // reset all nodes
            for (var nodeId in allNodes) {
                allNodes[nodeId].color = allNodes[nodeId].hiddenColor;
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
    }
}