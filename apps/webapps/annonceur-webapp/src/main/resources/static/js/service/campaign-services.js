'use strict';

/* Services */

var campaignServices = angular.module('campaignServices', ['ngResource']);




campaignServices.factory('Campaign', ['$resource',
    function($resource){

        var Campaign = $resource('/', {_csrf:addonf.token}, {
            save: {url:addonf.base+'manageAds/save',method:'POST',responseType:"json",isArray: false},
            create: {url:addonf.base+'manageAds/create',method:'GET',responseType:"json",isArray: false}
        });

        Campaign.resolve = function (Campaign, $q) {
            var deferred = $q.defer();
            Campaign.getCurrent(function (campaign) {
                deferred.resolve(campaign);
            });
        };

        var singleton;

        Campaign.getCurrent = function (callback) {
            if (!singleton) {

                singleton = Campaign.create({}, function(data){
                    singleton = data;
                    if (callback) {
                        callback(singleton);
                    }
                });
            }
            return singleton;
        };

        return Campaign;


    }]);
