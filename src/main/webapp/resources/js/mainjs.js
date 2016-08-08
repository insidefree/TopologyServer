function bindActionToLink(){
    var allLinks = document.getElementsByTagName('button');
    $(document).ready (function(){
        for (var i=0; i<allLinks.length; i++) {
            $('button[attr-name]').bind("click", function () {
                var linkName = $(this).attr('attr-name');
                var url = 'http://tel-ran.pogodina.me/' + linkName + '/';
                $.ajax({
                    url: url,
                    type: "GET",
                    dataType: "html",
                    success: function (data) {
                        $('#displayArea').html(data);
                    }
                });
            });
        };
    });
}