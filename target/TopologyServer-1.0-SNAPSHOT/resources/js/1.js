"use strict"
var startNodes = [
    {id: 1, label: 'HOSTS', x: 0, y:0, fixed: true, position: '', shape: 'box',font:{size:30}},
/*
    {id: 2, label: 'HBAs', x: 250, y:0, fixed: true, position: ''},
*/
    {id: 3, label: 'SWITCH01', x: 500, y:100, fixed: true, position: '', shape: 'box', font:{size:30}},
    {id: 4, label: 'SWITCH01', x: 500, y:-100, fixed: true, position: '', shape: 'box', font:{size:30}},
    {id: 5, label: 'ArrayID01', x: 1000, y:0, fixed: true, position: '', shape: 'database', font:{size:30}},
    {id: 6, label: 'ArrayID02', x: 1000, y:200, fixed: true, position: '', shape: 'database', font:{size:30}},
    {id: 7, label: 'ArrayID03', x: 1000, y:-200, fixed: true, position: '', shape: 'database', font:{size:30}}
];

startEdges = [];