'use strict';

/* Services */

var campaignServices = angular.module('campaignServices', ['ngResource']);




campaignServices.factory('Campaign', ['$resource',
    function($resource){

        var Campaign = $resource('/', {_csrf:addonf.token}, {
            _save: {url:addonf.base+'manageAds/saveStepNoFile/:step',method:'POST',responseType:"json",isArray: false},
            getCampaign: {url:addonf.base+'createCampaign/get',method:'GET',responseType:"json",isArray: false}
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

                singleton = Campaign.getCampaign({}, function(data){
                    singleton = data;
                    if (callback) {
                        callback(singleton);
                    }
                });
            }
            return singleton;
        };



        Campaign.save = function save(campaign,step, callback) {
            return Campaign._save({"step":step}, campaign, callback);
        };


        return Campaign;


    }]);
