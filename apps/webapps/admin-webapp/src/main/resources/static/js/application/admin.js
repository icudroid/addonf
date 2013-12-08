'use strict';

/* App Module */

var adminApp = angular.module('adminApp', [
    'ngRoute',
    'adminControllers',
    'adminServices'
]);

adminApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/search', {templateUrl: addonf.base+'partials/goosegame.html',   controller: 'AdminCtrl'}).
            when('/detail/:levelId', {templateUrl: addonf.base+'partials/detail.html',   controller: 'DetailCtrl'}).
            when('/create', {templateUrl: addonf.base+'partials/create.html',   controller: 'CreateCtrl'}).
            otherwise({redirectTo: '/search'});
    }]);



