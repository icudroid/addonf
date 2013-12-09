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
        $scope.base = addonf.base;
        $scope.items = [
            {label:"Add",value:5},
            {label:"Dead",value:4},
            {label:"Jail",value:6},
            {label:"Jump",value:1},
            {label:"None",value:8},
            {label:"Reduction",value:2}
        ];

        $scope.create = function(){
            Admin.create({multiple:$scope.multiple,level:$scope.level,strong:$scope.strong,nbCase:$scope.nbCase,minAmount:$scope.minAmount},function(data){
                $scope.result = data;
            });
        }

        $scope.change = function(toCaseValue,gooseCase){

            if(toCaseValue!=1){
                Admin.modify({idCase:gooseCase.id,type:toCaseValue});
                gooseCase.type = toCaseValue;
            }

        };

        $scope.doModifyJump = function(jumpTo){
            Admin.modifyToJump({idCase:gooseCase.id,jumpTo:jumpTo});
            gooseCase.type = toCaseValue;
        }
    }
]);


var ModalInstanceCtrl = function ($scope, $modalInstance, items) {

    $scope.items = items;
    $scope.selected = {
        item: $scope.items[0]
    };

    $scope.doModifyJump = function () {
        $modalInstance.close($scope.selected.item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};


/* Controllers */



