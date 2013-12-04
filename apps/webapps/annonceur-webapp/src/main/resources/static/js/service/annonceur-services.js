'use strict';

/* Services */

var annonceurServices = angular.module('annonceurServices', ['ngResource']);


annonceurServices.factory('Annonceur', ['$resource',
    function($resource){
        return $resource('/', {}, {




        });
    }]);