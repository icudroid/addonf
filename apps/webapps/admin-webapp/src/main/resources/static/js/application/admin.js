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
            when('/home', {templateUrl: 'partials/home.html',   controller: 'AdminCtrl'}).
            otherwise({redirectTo: '/home'});
    }]);



