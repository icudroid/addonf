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
            when('/search', {templateUrl: addonf.base+'manage/gooseGame/partials/goosegame.html',   controller: 'AdminCtrl'}).
            when('/detail/:levelId', {templateUrl: addonf.base+'manage/gooseGame/partials/detail.html',   controller: 'DetailCtrl'}).
            when('/create', {templateUrl: addonf.base+'manage/gooseGame/partials/create.html',   controller: 'CreateCtrl'}).
            when('/modify', {templateUrl: addonf.base+'manage/gooseGame/partials/modify.html',   controller: 'ModifyCtrl'}).
            otherwise({redirectTo: '/search'});
    }]);



