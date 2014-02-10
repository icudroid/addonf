
$(function(){
    var getStat = function(start,end,type,serviceId, global,idElementResult){
        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: addonf.statUrl,
            data: JSON.stringify({
                start : start,
                end : end,
                type : type,
                idAd : addonf.idAd,
                serviceId : serviceId,
                global: global
            })
        }).done(function( data) {
                alert( data );
        });
    };


    getStat(null,null,'SEX',null,false,'statSexId');



});