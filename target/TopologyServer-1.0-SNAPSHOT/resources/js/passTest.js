$(document).ready(function(){
    var myStr = "str to controller with ajax",
        res = "default";
    $.ajax({
        type : "POST",
        dataType: 'json',
        contentType: 'application/json',
        url : "pass",
        data : JSON.stringify(myStr),
        success : function(data, textStatus) {
            //res = response;
            $('#myPass').html(data);
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    });
});