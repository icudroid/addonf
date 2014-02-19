'use strict';

/* Services */

var services = angular.module('services', ['ngResource']);

services.factory('Services', ['$resource',
    function($resource){
        return $resource('/', {_csrf:addonf.token}, {
            getCountries: {url:addonf.base+'getCountries',method:'GET',responseType:"json",isArray: true},
            getCitiesByName : {url:addonf.base+'getTownsByName/:name',method:'GET',responseType:"json",isArray: true},
            getSexes : {url:addonf.base+'getSexes',method:'GET',responseType:"json",isArray: true},
            getBrands : {url:addonf.base+'getBrands',method:'GET',responseType:"json",isArray: true},
            emptyDlImg: {url:addonf.base+'createCampaign/tmpImage/empty',method:'GET',responseType:"json",isArray: false},
            reloadDlImg : {url:addonf.base+'createCampaign/tmpImage/load/:numOpenRule',method:'GET',responseType:"json",isArray: false}
        });
    }
]);
