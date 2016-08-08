$(document).ready(function() {
    console.time("ajax");
    /*$.ajax({
        type: "GET",
        url: "http://bench:8090/TopologyServer/zoneList",
        success: function (data) {
            /!*for (var key in data) {

             }*!/
            $('#top').html(data);
        }
    });*/

    $.ajax({
        type: "GET",
        url: "http://bench:8090/TopologyServer/zoneNameList",
        success: function (data) {
            /*for (var key in data) {

             }*/
            $('#top').html(data);
        }
    });
    console.timeEnd("ajax");
});