
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

            var options=
                {
                    series: {
                        pie: {
                            show: true,
                                highlight: {
                                opacity: 0.1
                            },
                            radius: 1,
                                stroke: {
                                color: '#fff',
                                    width: 2
                            },
                            startAngle: 2,
                                combine: {
                                color: '#353535',
                                    threshold: 0.05
                            },
                            label: {
                                show: true,
                                    radius: 1,
                                    formatter: function(label, series){
                                    return '<div class="label label-inverse">'+label+'&nbsp;'+Math.round(series.percent)+'%</div>';
                                }
                            }
                        },
                        grow: {	active: false}
                    },
                    colors: [],
                        legend:{show:false},
                    grid: {
                        hoverable: true,
                            clickable: true,
                            backgroundColor : { }
                    },
                    tooltip: true,
                        tooltipOpts: {
                    content: "%p.0%, %s",
                        shifts: {
                        x: -30,
                            y: -50
                    },
                    defaultTheme: false
                }
            };

            $.plot($("#"+idElementResult), data,options);
        });
    };


    getStat(null,null,'SEX',null,false,'statSexId');
    getStat(null,null,'AGE_GROUPE',null,false,'statAgeGroupId');
    getStat(null,null,'AGE_GROUP_SEX',null,false,'statAgeGroupSexId');
    getStat(null,null,'CITY',null,false,'statCityId');




});