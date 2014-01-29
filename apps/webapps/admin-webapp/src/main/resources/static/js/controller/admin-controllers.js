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
        };

        $scope.newLevel = function(){
            $location.path('/create');
        };


        $scope.delete = function(gooseLevel){
            Admin.deleteLevel({levelId:gooseLevel.levelId},function(data){
                $scope.search();
            });
        };

        $scope.modify = function(gooseLevel){
            addonf.modify = {
                gooseLevel : gooseLevel
            };
            $location.path('/modify');
        };
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


adminControllers.controller('CreateCtrl', ['$scope', 'Admin', '$interval','$timeout', '$route', '$location','$modal','$log',
    function($scope, Admin, $interval, $timeout, $route, $location,$modal,$log) {
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
            Admin.create({multiple:$scope.multiple,level:$scope.level,strong:$scope.strong,nbCase:$scope.nbCase,minAmount:$scope.minAmount,nbError:$scope.nbError},function(data){
                $scope.result = data;
            });
        }

        $scope.change = function(toCaseValue,gooseCase){

            if(toCaseValue!=1){
                Admin.modify({idCase:gooseCase.id,type:toCaseValue});
                gooseCase.type = toCaseValue;
            }else{

                //open
                var modalInstance = $modal.open({
                    templateUrl: 'myModalContent.html',
                    controller: "ModalInstanceCtrl"
                });


                modalInstance.result.then(function (jumpToNumber) {
                    $scope.doModifyJump(gooseCase,jumpToNumber);
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });

            }

        };

        $scope.doModifyJump = function(gooseCase,jumpTo){
            Admin.modifyToJump({idCase:gooseCase.id,jumpTo:jumpTo});
            gooseCase.type = 1;
        };


    }
]);


adminControllers.controller('ModalInstanceCtrl', ['$scope', 'Admin', '$interval','$timeout', '$route', '$location','$modal','$log','$modalInstance',
    function($scope, Admin, $interval, $timeout, $route, $location,$modal,$log,$modalInstance) {
        $scope.selected = {
            jumpTo :""
        };

        $scope.ok = function () {
            $modalInstance.close($scope.selected.jumpTo);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }
]);




adminControllers.controller('ModifyCtrl', ['$scope', 'Admin', '$interval','$timeout', '$route', '$location','$modal',
    function($scope, Admin, $interval, $timeout, $route, $location,$modal) {
        $scope.base = addonf.base;
        $scope.items = [
            {label:"Add",value:5},
            {label:"Dead",value:4},
            {label:"Jail",value:6},
            {label:"Jump",value:1},
            {label:"None",value:8},
            {label:"Reduction",value:2}
        ];
        $scope.gooseLevel = addonf.modify.gooseLevel;

        $scope.modify = function () {
            Admin.modifyMinAmount({amount:$scope.gooseLevel.minAmount});
        };

        $scope.change = function(toCaseValue,gooseCase){

            if(toCaseValue!=1){
                Admin.modify({idCase:gooseCase.id,type:toCaseValue});
                gooseCase.type = toCaseValue;
            }else{

                //open
                var modalInstance = $modal.open({
                    templateUrl: 'myModalContent.html',
                    controller: "ModalInstanceCtrl"
                });


                modalInstance.result.then(function (jumpToNumber) {
                    $scope.doModifyJump(gooseCase,jumpToNumber);
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });

            }

        };

        $scope.doModifyJump = function(gooseCase,jumpTo){
            Admin.modifyToJump({idCase:gooseCase.id,jumpTo:jumpTo});
            gooseCase.type = 1;
        };


    }
]);
/* Controllers */



