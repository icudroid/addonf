$(function(){

    $("#play").click(function(){
        $(document).fullScreen(true);

        $( "body" ).load( addonf.base+"game.html" );
        window.location.href = "#/start"
    });


    $("#login-4-game-btn").click( function(e) {
        $(document).fullScreen(true);
        $.ajax({url: addonf.base+"login",
            type: "POST",
            dataType : "json",
            data: $("#login-4-game").serialize(),
            success: function(data, status) {
                if (data.success) {
                    //go game
                    $( "body" ).load( addonf.base+"game.html" );
                    window.location.href = "#/start"
                } else {
                    //show errors
                    $("#error-login").show();
                }
            },
            error: function(){
                $('#login-error').show();
            }
        });
        return false;
    });
});