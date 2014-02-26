$(function(){
    $( ".searchInput" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: addonf.base+"search",
                data: {
                    q: request.term,
                    _csrf:addonf.token
                },
                success: function( data ) {
                    response( $.each( data.content, function( item ) {
                        return {
                            label: item,
                            value: item
                        }
                    }));
                }
            });
        },
        minLength: 3,
        select: function( event, ui ) {
            window.location.href = addonf.base+"search/all?req="+ui.item.value;
        }
    });
});