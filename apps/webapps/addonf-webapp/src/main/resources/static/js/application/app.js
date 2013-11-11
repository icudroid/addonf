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
            when('/pause', {templateUrl: 'partials/pause.html', controller: 'PauseCtrl'}).
            when('/lost', {templateUrl: 'partials/lost.html', controller: 'LostCtrl'}).
            when('/win', {templateUrl: 'partials/win.html', controller: 'WinCtrl'}).
            otherwise({redirectTo: '/start'});
    }]);



