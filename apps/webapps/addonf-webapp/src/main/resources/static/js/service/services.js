'use strict';

/* Services */

var adgameServices = angular.module('adgameServices', ['ngResource']);


adgameServices.factory('Game', ['$resource',
    function($resource){
        return $resource('rest/createGame', {}, {
            query: {method:'GET', params:{}, isArray:true}
        });
    }]);