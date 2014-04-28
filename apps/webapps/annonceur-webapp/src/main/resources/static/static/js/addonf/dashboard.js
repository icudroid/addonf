
$(function(){

    var createTableOpen = function(data){
        var $table = $("<table>").addClass("table");
        var $headers = $("<tr>")
        $table.append($headers);

        var columnsAgeSex = '<td>Age</td><td>Civilité</td>';
        var columnCount = '<td>nb</nb>';
        var columnsResponses = '';



        for (var i = 0; i < data.responses.length; i++) {
            var o = data.responses[i];
            columnsResponses+='<td>'+o+'</td>'
        }

        $headers.append(columnsAgeSex+columnsResponses+columnCount);


        for (var i = 0; i < data.statistics.length; i++) {
            var o = data.statistics[i];
            /*
             private AgeGroup ageGroup;
             private Sex sex;
             private Long count;

             */
            var $bodyTable = $("<tr>")
            $table.append($bodyTable);

            var row = '<td>'+ o.ageGroup+'</td>'+'<td>'+ o.sex+'</td>';
            for (var j = 0; j < data.responses.length; j++) {

                if(o.response==data.responses[j]){
                    row+='<td>X</td>'
                }else{
                    row+='<td></td>';
                }



            }
            row+="<td>"+o.count+"</td>"
            $bodyTable.append(row);
       }


        $("#rulesStat").html("").append($table);


    };


    var createTableBrand = function(data){
        var $table = $("<table>").addClass("table");
        var $headers = $("<tr>")
        $table.append($headers);

        var columnsAgeSex = '<td>Age</td><td>Civilité</td>';
        var columnCount = '<td>nb</nb>';
        var columnsResponses = '';



        for (var i = 0; i < data.responses.length; i++) {
            var o = data.responses[i];
            columnsResponses+='<td>'+o+'</td>'
        }

        $headers.append(columnsAgeSex+columnsResponses+columnCount);


        for (var i = 0; i < data.statistics.length; i++) {
            var o = data.statistics[i];
            /*
             private AgeGroup ageGroup;
             private Sex sex;
             private Long count;

             */
            var $bodyTable = $("<tr>")
            $table.append($bodyTable);

            var row = '<td>'+ o.ageGroup+'</td>'+'<td>'+ o.sex+'</td>';
            for (var j = 0; j < data.responses.length; j++) {

                if(o.response==data.responses[j]){
                    row+='<td>X</td>'
                }else{
                    row+='<td></td>';
                }



            }
            row+="<td>"+o.count+"</td>"
            $bodyTable.append(row);
        }


        $("#rulesStat").html("").append($table);


    };



    var createTableMulti = function(data){
        var $table = $("<table>").addClass("table");
        var $headers = $("<tr>")
        $table.append($headers);

        var columnsAgeSex = '<td>Age</td><td>Civilité</td>';
        var columnCount = '<td>nb</nb>';
        var columnsResponses = '';



        for (var i = 0; i < data.responses.length; i++) {
            var o = data.responses[i];
            columnsResponses+='<td>'+o+'</td>'
        }

        $headers.append(columnsAgeSex+columnsResponses+columnCount);


        for (var i = 0; i < data.statistics.length; i++) {
            var o = data.statistics[i];
            /*
             private AgeGroup ageGroup;
             private Sex sex;
             private Long count;

             */
            var $bodyTable = $("<tr>")
            $table.append($bodyTable);

            var row = '<td>'+ o.ageGroup+'</td>'+'<td>'+ o.sex+'</td>';
            for (var j = 0; j < data.responses.length; j++) {

                if(o.responses[j]==data.responses[j]){
                    row+='<td>X</td>'
                }else{
                    row+='<td></td>';
                }



            }
            row+="<td>"+o.count+"</td>"
            $bodyTable.append(row);
        }


        $("#rulesStat").html("").append($table);


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
        if(selected){
            getStat(selected);
            $("#response").show();
        }else{
            $("#response").hide();
        }
    });




});