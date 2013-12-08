'use strict';

/* Controllers */

var adminControllers = angular.module('adminControllers', ['ngRoute','ui.bootstrap']);

adminControllers.controller('AdminCtrl', ['$scope', 'Admin', '$interval','$timeout', '$route', '$location',
    function($scope, Admin, $interval, $timeout, $route, $location) {

        $scope.level = '';
        $scope.multiple = false;
        $scope.responseResults = false;
        $scope.results = [];

        $scope.search = function(){
            $scope.responseResults=false;
            Admin.search({'level':$scope.level,'multiple':$scope.multiple},function(data){
                $scope.responseResults=true;
                $scope.results=data;
            });
        };

        $scope.showDetailLevel = function(gooseLevel){
            $location.path('/detail/'+gooseLevel.levelId);
        }

        $scope.newLevel = function(){
            $location.path('/create');
        }
    }
]);




adminControllers.controller('DetailCtrl', ['$scope', 'Admin', '$routeParams','$interval','$timeout', '$route', '$location',
    function($scope, Admin, $routeParams, $interval, $timeout, $route, $location) {
        $scope.base = addonf.base;
        $scope.id = $routeParams.levelId;
        $scope.responseResults = false;
        Admin.level({levelId:$scope.id },function(data){
            $scope.responseResults = true;
            $scope.result = data;
        });

    }
]);


adminControllers.controller('CreateCtrl', ['$scope', 'Admin', '$interval','$timeout', '$route', '$location',
    function($scope, Admin, $interval, $timeout, $route, $location) {

    }
]);

/* Controllers */



