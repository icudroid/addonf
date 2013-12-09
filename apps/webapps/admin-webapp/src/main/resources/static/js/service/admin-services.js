'use strict';

/* Services */

var adminServices = angular.module('adminServices', ['ngResource']);


adminServices.factory('Admin', ['$resource',
    function($resource){
        return $resource('/', {_csrf:addonf.token}, {
            search: {url:addonf.base+'manage/gooseGame/search',method:'POST',responseType:"json",isArray: true},
            level:{url:addonf.base+'manage/gooseGame/level/:levelId',method:'GET',responseType:"json",isArray: false},
            create:{url:addonf.base+'manage/gooseGame/create',method:'POST',responseType:"json",isArray: false},
            modify:{url:addonf.base+'manage/gooseGame/modify/:idCase/:type',method:'GET',responseType:"json",isArray: false},
            modifyToJump:{url:addonf.base+'manage/gooseGame/modify/:idCase/:jumpTo',method:'GET',responseType:"json",isArray: false}
        });
    }]);