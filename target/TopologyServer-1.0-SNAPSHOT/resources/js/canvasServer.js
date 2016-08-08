function redrawAll(nodeList) {
    // create a network
    console.time('create networkALL');
    console.time('create DataSet');
    var nodesDataset = new vis.DataSet(nodeList);
    var edgesDataset = new vis.DataSet([]);

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
    console.timeEnd('create DataSet');
    console.time('create network');
    var network = new vis.Network(container, data, options);
    console.timeEnd('create network');
    console.timeEnd('create networkALL');

    document.querySelectorAll("select");
}