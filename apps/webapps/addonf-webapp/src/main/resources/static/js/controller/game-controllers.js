'use strict';

/* Controllers */

var adgameControllers = angular.module('adgameControllers', ['ngRoute','ui.bootstrap']);

adgameControllers.controller('GameCtrl', ['$scope', 'Game', '$interval','$timeout', '$route', '$location',
    function($scope, Game, $interval, $timeout, $route, $location) {

        $scope.responded = false;
        $scope.dynamic = 100;
        $scope.correct = "noreponse";
        $scope.videoElt = angular.element("video");
        $scope.index = 0;
        $scope.base = addonf.base;
        $scope.level = 3;
        $scope.canDownload = false;

        $scope.noResponse = function(){

                Game.noResponse({index:$scope.index},function(data){
                    $scope.score = data.score;

                    $scope.responded = true;

                    if(data.correct){
                        $scope.correct = "ok";
                    }else{
                        $scope.correct = "ko";
                    }

                    $timeout.cancel($scope.hideResult);
                    $scope.hideResult = $timeout(function() {
                        $scope.responded = false;
                    },3000);

                    if(data.status == "WinLimitTime"){
                        $location.path('/end');
                    }
                });

            $scope.$apply(function ($scope) {
                $scope.index++;
                if( $scope.adGame.game.length > $scope.index){
                    $scope.current = $scope.adGame.game[$scope.index];
                    $scope.ad = $scope.base + "video/"+$scope.index+"?"+new Date().getTime();
                    $scope.videoElt[0].load();
                    $scope.videoElt[0].play();
                }else{
                    $interval.cancel($scope.counter);
                }
            });

        };




        Game.createGame({level:$scope.level},function(data){

            $scope.index = 0;
            $scope.adGame = data;
            $scope.current = $scope.adGame.game[$scope.index];
            $scope.gooseCases =  $scope.adGame.gooseGames;
            $scope.token =  $scope.adGame.userToken;


            $scope.score = 0;
            $scope.ad = $scope.base + "video/"+$scope.index+"?"+new Date().getTime();
            $scope.videoElt[0].load();
            $scope.videoElt[0].play();

            $scope.videoElt.on("ended",$scope.noResponse);

            $scope.start = new Date().getTime();
            $scope.end = $scope.start + 30000;

            $scope.counter = $interval(function(){
                var time = new Date().getTime();
                if(time>$scope.end){
                    $interval.cancel($scope.counter);
                    $scope.noResponse();
                }
                $scope.dynamic = 100 * (time - $scope.start) / ($scope.end - $scope.start ) ;
            },500);

            $timeout(function() {
                $scope.responded = false;
            },3000);
        });




        $scope.userResponse = function(userResponse){
            Game.doResponse({index:$scope.index,responseId:userResponse.id},function(data){
                $scope.score = data.score;

                $scope.responded = true;

                $scope.token =  data.userToken;

                if(!$scope.adGame.multiple && $scope.gooseCases[$scope.index].num ==  $scope.token &&  $scope.gooseCases[$scope.index].type==7){
                    //download ask music
                    $scope.canDownload = true;
                }


                if(data.correct){
                    $scope.correct = "ok";
                }else{
                    $scope.correct = "ko";
                }

                $timeout.cancel($scope.hideResult);
                $timeout(function() {
                    $scope.responded = false;
                },3000);

                if(data.status == "WinLimitTime"){
                    $location.path('/end');
                }

            });
            $scope.index++;
            if( $scope.adGame.game.length > $scope.index){
                $scope.current = $scope.adGame.game[$scope.index];
                $scope.videoElt[0].load();
                $scope.ad = $scope.base + "video/"+$scope.index+"?"+new Date().getTime();
                $scope.videoElt[0].load();
                $scope.videoElt[0].play();
            }else{
                $interval.cancel($scope.counter);
            }

        };


}]);



adgameControllers.controller('EndCtrl', ['$scope', 'Game', '$timeout', '$route',
    function($scope, Game, $timeout, $route) {

    $scope.base = addonf.base;

    Game.resultGame(function(data){
        $scope.message = data.message;
        $scope.gooseCases = data.gooseGames;
        $scope.score = data.score;
        $scope.token =  data.userToken;
    });


}]);




/* Controllers */


