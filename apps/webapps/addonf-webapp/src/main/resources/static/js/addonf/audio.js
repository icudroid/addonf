
$(function(){

    var currentSample = null;

    $(".playMusic").click(function(e){



        if($(this).find("i").hasClass("fa-pause") && currentSample!=null){
            $("div > a.btn > i.fa-pause").removeClass("fa-pause").addClass("fa-play");
            currentSample.pause();
        }else{
            if(currentSample!=null){
                $("div > a.btn > i.fa-pause").removeClass("fa-pause").addClass("fa-play");
                currentSample.pause();
            }
            var music = $(this).data("music");
            var url;
            $(this).find("i").removeClass("fa-play").addClass("fa-pause");
            url=addonf.static+'sample/'+music+'.mp3';
            currentSample = new Audio();
            currentSample.src = url;
            currentSample.play();
        }







        return false;
    });






});




