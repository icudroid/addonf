
$(function(){




    function createRowOpen(o, data) {
        var row = '<td>' + ((o.ageGroup!=null)?o.ageGroup:'inconnu') + '</td>' + '<td>' + ((o.sex!=null)?o.sex:'inconnue') + '</td>';

        for (var j = 0; j < data.responses.length; j++) {

            if (o.response == data.responses[j]) {
                row += '<td>X</td>'
            } else {
                row += '<td></td>';
            }
        }

        row += "<td>" + o.count + "</td>"
        return row;
    }


    function createRowMulti(o, data) {
        var row = '<td>' + ((o.ageGroup!=null)?o.ageGroup:'inconnu') + '</td>' + '<td>' + ((o.sex!=null)?o.sex:'inconnue') + '</td>';

        for (var j = 0; j < data.responses.length; j++) {

            if(o.responses[j]==data.responses[j]){
                row+='<td>X</td>'
            }else{
                row+='<td></td>';
            }
        }

        row += "<td>" + o.count + "</td>"
        return row;
    }


    function createRowBrand(o, data) {
        var row = '<td>' + ((o.ageGroup!=null)?o.ageGroup:'inconnu') + '</td>' + '<td>' + ((o.sex!=null)?o.sex:'inconnue') + '</td>';
        row+='<td><img src="'+addonf.static+'logo/'+o.logo+'" style="max-height:50px;max-width:50px;" /></td>'
        row+="<td>"+o.count+"</td>"
        return row;
    }


    function createTable(data,type) {
        var $table = $("<table>").addClass("table");
        var $headers = $("<tr>")
        $table.append($headers);

        var columnsAgeSex = '<td>Age</td><td>Civilit&eacute;</td>';
        var columnCount = '<td>nb</nb>';
        var columnsResponses = '';


        for (var i = 0; i < data.responses.length; i++) {
            var o = data.responses[i];
            columnsResponses += '<td>' + o + '</td>'
        }

        $headers.append(columnsAgeSex + columnsResponses + columnCount);


        for (var i = 0; i < data.statistics.length; i++) {
            var o = data.statistics[i];
            var $bodyTable = $("<tr>")
            $table.append($bodyTable);
            switch (type){
                case 'open':
                    $bodyTable.append(createRowOpen(o, data));
                    break;
                case 'multi':
                    $bodyTable.append(createRowMulti(o, data));
                    break;
                case 'brand':
                    $bodyTable.append(createRowBrand(o, data));
                    break;

            }

        }


        $("#rulesStat").append($table);
    }


    var createTableOpen = function(data){
        createTable(data,'open');
    };


    var createTableBrand = function(data){
        createTable(data,'brand');
    };

    var createTableMulti = function(data){
        createTable(data,'multi');
    };





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
            $("#bidAvg").html(data.averageBid);

            //create tables for services
            if(data.open){
                createTableOpen(data.open);
            }

            if(data.brand){
                createTableBrand(data.brand);
            }

            if(data.multi){
                createTableMulti(data.multi);
            }


        });
    };


    $("#ruleSelection").change(function(){
        var selected = $(this).val();
        $("#rulesStat").html("");
        if(selected){
            getStat(selected);
            $("#response").show();
        }else{
            $("#response").hide();
        }
    });




});