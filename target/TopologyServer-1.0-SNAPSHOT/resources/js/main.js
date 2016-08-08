function createMenuAndCallDashBoard() {
    var url = 'http://localhost:8080/fullObject/zoneName/fabricwwn';
           /* initial loading */
           $.ajax({
            type: "GET",
            url: url,
            success: function(serversObj){
                var htmlResult = '<ul>';

                for (var key in serversObj) {
                    var serverName = serversObj[key];
                   if (serversObj[key]){
                        htmlResult+= '<li class=" has-sub"><a href=# attr-name="'+serversObj[key]+'" >'+serversObj[key]+'</a>';
                        htmlResult+= '<ul id="server_'+serverName.replace(/\./g,'_')+'"></ul></li>';
                    }
                }
                $('#serverList').html(htmlResult);
                bindServerEvents();
            }
            });
            
            /* add event for each server in the list */
            function bindServerEvents(){
                $('a[attr-name]').on('click', function(event){
                    var serverName = $(this).attr('attr-name');
                    var cleanServerName = serverName.replace(/\./g,'_');
                    if ($('#server_'+cleanServerName).html()==""){
                   
                    var url = 'http://localhost:8080/fullObject/zoneName/hostNamesByFabricWWN/'+serverName+'/';
                    $.ajax({
                    type: "GET",
                    url: url,
                    success: function(data){
                        var resultHtml = '';
                        for(var key in data){
                            resultHtml+= '<li class="last"><a href=# attr-servername="'+cleanServerName+'" attr-deviceset="'+data[key]+'">'+data[key]+'</a></li>';
                        }
                        $('#server_'+cleanServerName).html(resultHtml);
                        bindDeviceSetEvents(serverName, cleanServerName);
                        }
                    });
                }
                $('#server_'+cleanServerName).toggle();
                slideOtherMenuLinks($(this));
            });
        }
        
        /**
         * bind event for list of device sets
         * @param {type} serverName
         * @param {type} cleanServerName, replace dot with undescore
         * @returns {undefined}
         */
        function bindDeviceSetEvents(serverName, cleanServerName){
            $('[attr-servername="'+cleanServerName+'"]').on('click', function(e){
                var deviceSet = $(this).attr('attr-deviceset');
                
                    var url = 'http://localhost:8080/fullObject/zoneName/fabricwwns/'+serverName+'/'+deviceSet;
                    $.ajax({
                    type: "GET",
                    url: url,
                    success: function(data){
                        $('#imgArea').html(data);
                        }
                    });
            });
        }
}