
$(function(){

    var currentSample = null;

    var stopMusic = function(){
        $("a.btn > i.fa-pause").removeClass("fa-pause").addClass("fa-play");
    };

    $(".playMusic").click(function(e){

        if($(this).find("i").hasClass("fa-pause") && currentSample!=null){
            $("a.btn > i.fa-pause").removeClass("fa-pause").addClass("fa-play");
            currentSample.pause();
        }else{
            if(currentSample!=null){
                currentSample.removeEventListener('ended',stopMusic);
                $("a.btn > i.fa-pause").removeClass("fa-pause").addClass("fa-play");
                currentSample.pause();
            }
            var music = $(this).data("music");
            var url;
            $(this).find("i").removeClass("fa-play").addClass("fa-pause");
            url=addonf.static+'sample/'+music+'.mp3';
            currentSample = new Audio();
            currentSample.src = url;
            currentSample.play();
            currentSample.addEventListener('ended', stopMusic);
        }

        return false;
    });






});




