'use strict';

/* Controllers */

var adgameControllers = angular.module('adgameControllers', ['ngRoute']);

adgameControllers.controller('GameCtrl', ['$scope', 'Game', '$timeout', '$route', '$location',
    function($scope, Game, $timeout, $route, $location) {

        $scope.responded = false;
        $scope.correct = "noreponse";

        Game.createGame(function(data){
            $scope.index = 0;
            $scope.adGame = data;
            $scope.current = $scope.adGame.game[$scope.index];
            $scope.score = 0;
            $scope.ad = "http://localhost:8080/video/"+$scope.index;
        });


        $scope.userResponse = function(userResponse){
            Game.doResponse({index:$scope.index,responseId:userResponse.id},function(data){
                $scope.score = data.score;

                $scope.responded = true;

                if(data.correct){
                    $scope.correct = "ok";
                }else{
                    $scope.correct = "ko";
                }

                $timeout(function() {
                    $scope.responded = false;
                },3000);

                if(data.status == "WinLimitTime"){
                    $location.path('/end');
                }

            });
            $scope.current = $scope.adGame.game[++$scope.index];
            $scope.ad = "http://localhost:8080/video/"+$scope.index;
        };

}]);



adgameControllers.controller('EndCtrl', ['$scope', 'Game', '$timeout', '$route',
    function($scope, Game, $timeout, $route) {

}]);




/* Controllers */


