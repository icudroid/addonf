'use strict';

/* Services */

var manageAdsServices = angular.module('manageAdsServices', ['ngResource']);


manageAdsServices.factory('Ads', ['$resource',
    function($resource){
        return $resource('/', {_csrf:addonf.token}, {
            getAll: {url:addonf.base+'manageAds/getAll',method:'GET',responseType:"json",isArray: false}
        });
    }]);


