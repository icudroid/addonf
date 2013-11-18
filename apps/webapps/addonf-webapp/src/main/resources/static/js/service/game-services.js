'use strict';

/* Services */

var adgameServices = angular.module('adgameServices', ['ngResource']);


adgameServices.factory('Game', ['$resource',
    function($resource){
        return $resource('/', {}, {
            createGame: {url:'rest/createGame',method:'GET',responseType:"json",isArray: false},
            doResponse: {url:'rest/play/:index/:responseId',method:'GET',responseType:"json",isArray: false},
            noResponse: {url:'rest/noresponse/:index',method:'GET',responseType:"json",isArray: false},
            resultGame: {url:'rest/getResultAdGame',method:'GET',responseType:"json",isArray: false}
        });
    }]);