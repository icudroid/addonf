'use strict';

/* Services */

var adminServices = angular.module('adminServices', ['ngResource']);


adminServices.factory('Admin', ['$resource',
    function($resource){
        return $resource('/', {}, {




        });
    }]);