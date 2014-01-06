
$(function(){

    var cartMethode = function(what,music){

        //add to cart

        var url;
        switch (what){
            case "add":
                url='rest/addToCart/'+music;
                break;
            case "remove":
                url='rest/removeFromCart/'+music;
                break;

        }
        $.ajax({
            'type': 'GET',
            'url': addonf.base+url,
            'data': {_csrf:addonf.token},
            'success': function(data){
                // refresh cart
                $.ajax({
                    'type': 'GET',
                    'url': addonf.base+'partial/cart.html',
                    'data': {_csrf:addonf.token},
                    'success': function(html){
                        var cart = $(".cart>ul");
                        cart.html(html);
                    }
                });
            }
        });
    };

    $(".addToCart").click(function(e){
        cartMethode("add", $(this).data("music"));
        return false;
    });


    $(".removeFromCart").click(function(e){
        cartMethode("add",$(this).data("music"));
        return false;
    });



});
