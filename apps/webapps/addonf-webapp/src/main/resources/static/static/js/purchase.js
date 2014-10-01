var addonf = addonf || {};

addonf.Purchase = function(current){
    this._init(current);
};

addonf.Purchase.prototype = {
    $gameHistory : null,
    $gameHistoryPagination : null,
    _currentPage : 0,
    _nbByPage : 10,
    _maxPage : null,


    _init : function(current){
        var $this = this;
        this._currentPage = current;

        this.$gameHistory = $("#game_history");
        this.$gameHistoryPagination = $("#game_history_pagination");

        this._getHistories();
    },

    _getHistories : function (){

        var $this = this;



        $.ajax({
            url: addonf.base + "getPurchase" ,
            data : {
                page : this._currentPage,
                size : this._nbByPage
            }
        }).done(function(data) {

            //update pagination
            $this._maxPage =  data.totalPages;

            var $tbody = $this.$gameHistory.find("tbody");


            var rows = [];

            for (var i = 0; i < data.content.length; i++) {
                var history = data.content[i];
                var $row = $("<tr>");

                var day = new Date(history.generated);
                var dayWrapper = moment(day);

                $row.append($("<td>").html(dayWrapper.format("DD-MM-YYYY HH:mm:ss")));
                $row.append($("<td>").html(history.adAmount+ " ad"));
                $row.append($("<td>").html(history.media));
                $row.append($("<td>").html("<a data-toggle='modal' data-target='#modal' class='btn btn-link' href='"+addonf.base+"purchaseDetail/"+history.id+"' >Détail</a>" ));

                rows.push($row);

            }
            $tbody.find("td").remove();
            $tbody.append(rows);

            $this.$gameHistoryPagination.bootpag({
                total: $this._maxPage,          // total pages
                page: $this._currentPage+1,            // default page
                maxVisible: $this._nbByPage,     // visible pagination
                leaps: true         // next/prev leaps through maxVisible
            }).on("page", function(event, num){
                $this._currentPage = num-1;
                $this._getHistories();
            });
        });

    }
};
