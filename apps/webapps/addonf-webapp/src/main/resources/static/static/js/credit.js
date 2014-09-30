var addonf = addonf || {};

addonf.Credit = function(current){
    this._init(current);
};

addonf.Credit.prototype = {
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

        $("#play").click(function(){
            window.location = addonf.base+"credit";
        });

    },

    _getHistories : function (){

        var $this = this;



        $.ajax({
            url: addonf.base + "getCredits" ,
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
