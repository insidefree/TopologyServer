function createDashBoard(nodeList,edgeList) {
  // create an array with nodes
  var nodes = new vis.DataSet(
        nodeList
        );
  // create an array with edges
  var edges = new vis.DataSet(
        edgeList
        );
  // create a network
  var container = document.getElementById('mynetwork');
  var data = {
    nodes: nodes,
    edges: edges
  };
  var options = {
        nodes: {
            font: {
                size: 16,
                strokeWidth: 0 
                //strokeColor: 'white'
            },
            borderWidth: 2,
            shadow:true
        },
        edges: {
            width: 3,
            shadow:true,
            smooth: {
                type: 'dynamic'
            }
        },
        interaction: {
            navigationButtons: true,
            keyboard: true
            //tooltipDelay: 200
        },
        
    physics :{ 
    enabled :  true , 
    barnesHut :  { 
      gravitationalConstant :  - 2000 , 
      centralGravity :  -0.6,//0.3 , 
      springLength :  95 , 
      springConstant :  0.04 , 
      damping :  0.09 , 
      avoidOverlap :  0 
    }, 
    forceAtlas2Based :  { 
      gravitationalConstant :  - 50 , 
      centralGravity : -0.6,// 0.01 , 
      springConstant :  0.08 , 
      springLength :  100 , 
      damping :  0.4 , 
      avoidOverlap :  0 
    }, 
    repulsion :  { 
      centralGravity :  -1,//0.2 , 
      springLength : 40, //100 , 
      springConstant :  0.05 , 
      nodeDistance :  100,//100 , 
      damping :  0.09 
    }, 
    hierarchicalRepulsion :  { 
      centralGravity :  0.0 , 
      springLength :  100 , 
      springConstant :  0.01 , 
      nodeDistance :  120 , 
      damping :  0.09 
    }, 
    maxVelocity :  50 , 
    minVelocity :  0.1 , 
    solver :  'repulsion' , 
    stabilization :  { 
      enabled :  true , 
      iterations :  1000 , 
      updateInterval :  100 , 
      onlyDynamicEdges :  false , 
      fit :  true 
    }, 
    timestep :  0.5 
  } 
        
    };
  var network = new vis.Network(container, data, options);
 
}
