'use strict';

/* Controllers */

var adgameControllers = angular.module('adgameControllers', []);

adgameControllers.controller('GameCtrl', ['$scope', 'Game',
    function($scope, Game) {

        $scope.phones = Game.query();
        $scope.score = 0;



    }]);



/* Controllers */


