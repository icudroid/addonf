'use strict';


var directives = angular.module('directives', []);

directives.directive('chosen',function(){
    var linker = function(scope,element,attrs) {
        var list = attrs['chosen'];

        scope.$watch(list, function(){
            element.trigger('liszt:updated');
            element.trigger("chosen:updated");
        });

        element.chosen({width: "100%"});
    };

    return {
        restrict:'A',
        link: linker
    }
});

/* Directives */
