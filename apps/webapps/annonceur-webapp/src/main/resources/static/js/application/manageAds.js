'use strict';

/* App Module */

var manageAdsApp = angular.module('manageAdsApp', [
    'ngRoute',
    'manageAdsControllers',
    'manageAdsServices',
    'campaignServices'
]);

manageAdsApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/search', {templateUrl: addonf.base+'manageAds/partials/listads.html',   controller: 'ManageAdsCtrl'}).
            when('/create', {templateUrl: addonf.base+'manageAds/partials/create/step1.html',   controller: 'CreateAdsCtrl'}).
            otherwise({redirectTo: '/search'});
    }]);



