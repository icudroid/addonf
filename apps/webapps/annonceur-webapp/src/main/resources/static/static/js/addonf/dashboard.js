
$(function(){

    var getStat = function(serviceId){
        $.ajax({
            type: "GET",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: addonf.statUrl+"/"+serviceId
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
            $.plot($("#statOk"), data.ok,options);
            $.plot($("#statKo"), data.ko,options);
            $.plot($("#statNoResponse"), data.noResponse,options);
            $("#global").html(data.count);
            $("#question").html(data.question);



        });
    };


    $("#ruleSelection").change(function(){
        var selected = $(this).val();
        if(selected){
            getStat(selected);
            $("#response").show();
        }else{
            $("#response").hide();
        }
    });




});