'use strict';

/* App Module */

var adgameApp = angular.module('adgameApp', [
    'ngRoute',
    'adgameControllers',
    'adgameFilters',
    'adgameServices'
]);

adgameApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/start', {templateUrl: 'partials/game.html',   controller: 'GameCtrl'}).
            when('/end', {templateUrl: 'partials/end.html', controller: 'EndCtrl'}).
            otherwise({redirectTo: '/start'});
    }]);



