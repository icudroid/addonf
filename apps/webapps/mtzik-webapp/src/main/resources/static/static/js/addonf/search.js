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
                    response([{label:'res',desc:data,value:'res'}]);
                }
            });
        },
        minLength: 3
    }).data( "ui-autocomplete" )._renderItem = function( ul, item ) {

        var $root = $("<li>");
        var $row = $("<div class='row'></div>");
        var $colArtists = $("<div class='col-md-4'><h4 class='center-text'>Artistes</h4></div>");
        var $colMusics = $("<div class='col-md-4'><h4 class='center-text'>Musiques</h4></div>");
        var $colLabels = $("<div class='col-md-4'><h4 class='center-text'>Labels</h4></div>");
        $row.append($colArtists).append($colMusics).append($colLabels);
        $row.appendTo($root);

        for (var i = 0; i < item.desc.artists.length; i++) {
            var artist = item.desc.artists[i];
            $("<div class='row'></div>").append("<div class='col-md-4'><img style='max-height: 40px;max-width: 100%' src='"+addonf.static+"artist/"+((artist.photo==null)?'default.png':artist.photo)+"'/></div><div class='col-md-8'><h4><a href='"+addonf.base+"artistPage.html?artistId="+artist.id+"'>"+artist.fullName+"</a></h4></div>").appendTo($colArtists);
        }
        $("<a href='"+addonf.base+"search/artist?req="+encodeURIComponent($( ".searchInput").val())+"'>Plus d'artistes</a>").appendTo($colArtists);

        for (var i = 0; i < item.desc.musics.length; i++) {
            var music = item.desc.musics[i];
            $("<div class='row'></div>").append("<div class='col-md-4'><img style='max-height: 40px;max-width: 100%' src='"+addonf.static+"jacket/"+((music.jacket==null)?'default.png':music.jacket)+"'/></div><div class='col-md-8'><h4><a href='"+addonf.base+"product.html?musicId="+music.id+"'>"+music.title+"</a></h4></div>").appendTo($colMusics);
        }
        $("<a href='"+addonf.base+"search/music?req="+encodeURIComponent($( ".searchInput").val())+"'>Plus de musiques</a>").appendTo($colMusics);


        for (var i = 0; i < item.desc.labels.length; i++) {
            var label = item.desc.labels[i];
            $("<div class='row'></div>").append("<div class='col-md-4'><img style='max-height: 40px;max-width: 100%' src='"+addonf.static+"productor/"+((label.jacket==null)?'default.png':music.jacket)+"'/></div><div class='col-md-8'><h4><a href='"+addonf.base+"majorPage.html?majorId="+label.id+"'>"+label.fullName+"</a></h4></div>").appendTo($colLabels);
        }
        $("<a href='"+addonf.base+"search/label?req="+encodeURIComponent($( ".searchInput").val())+"'>Plus de labels</a>").appendTo($colLabels);

        return $root.appendTo(ul);


    };
});