'use strict';

/* App Module */

var manageAdsApp = angular.module('manageAdsApp', [
    'ngRoute',
    'manageAdsControllers',
    'manageAdsServices',
    'campaignServices',
    'services',
    'directives'
]);

manageAdsApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/create', {templateUrl: addonf.base+'manageAds/partials/create/step1.html',   controller: 'CreateAdsCtrl'}).
            when('/step2', {templateUrl: addonf.base+'manageAds/partials/create/step2.html',   controller: 'Step2Ctrl'}).
            when('/step3', {templateUrl: addonf.base+'manageAds/partials/create/step3.html',   controller: 'Step3Ctrl'}).
            when('/step4', {templateUrl: addonf.base+'manageAds/partials/create/step4.html',   controller: 'Step4Ctrl'}).
            otherwise({redirectTo: '/create'});
    }]);



