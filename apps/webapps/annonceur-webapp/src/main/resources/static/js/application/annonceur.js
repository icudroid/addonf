'use strict';

/* App Module */

var annonceurApp = angular.module('annonceurApp', [
    'ngRoute',
    'annonceurControllers',
    'annonceurServices'
]);

annonceurApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/home', {templateUrl: 'partials/home.html',   controller: 'AnnonceurCtrl'}).
            otherwise({redirectTo: '/home'});
    }]);



