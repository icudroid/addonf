'use strict';

/* Controllers */

var adgameControllers = angular.module('adgameControllers', ['ngRoute','ui.bootstrap']);

adgameControllers.controller('GameCtrl', ['$scope', 'Game', '$interval','$timeout', '$route', '$location',
    function($scope, Game, $interval, $timeout, $route, $location) {

        $scope.left = 0;
        $scope.responded = false;
        $scope.correct = "noreponse";

        $scope.videoElt = angular.element("video");

        $scope.index = 0;
        $scope.base = addonf.base;
        $scope.adVideoWebm = '';
        $scope.adVideoOgg = '';
        $scope.adVideoMp4 = '';
        $scope.score = 0;

        $scope.timeoutStatic;


        angular.element(document).bind("fullscreenchange", function(e) {
            if($(document).fullScreen()===false){
                //console.debug("resume");
                window.location.href = addonf.base+"resume";
                //$location.path('/resume');
            }
        });


        Game.createGame({},function(data){

            if(angular.isUndefined(data.game)){
                $(document).fullScreen(false);
                window.location.href = addonf.base+"cart.html";
                return;
            }

            $scope.index = 0;
            $scope.adGame = data;
            $scope.max =  $scope.adGame.minScore;
            $scope.current = $scope.adGame.game[$scope.index];
            $scope.gooseCases =  $scope.adGame.gooseGames;
            $scope.token =  $scope.adGame.userToken;

            $scope.score = 0;
            //$scope.ad = $scope.base + "video/"+$scope.index;

            switch ($scope.current.type){
                case 'AUDIO':
                    $scope.adAudio = $scope.base + "video/"+$scope.index;
                    break;
                case 'VIDEO':
                    $scope.adVideoWebm = $scope.video($scope.index,'webm');
                    $scope.adVideoOgg = $scope.video($scope.index,'ogg');
                    $scope.adVideoMp4 = $scope.video($scope.index,'mp4');

                    $scope.videoElt[0].load();
                    $scope.videoElt[0].play();
                    break;
                case 'STATIC':
                    $scope.adImage = $scope.image($scope.index);
                    $scope.timeoutStatic = $timeout($scope.noResponse,$scope.duration);
                    break;
            }

            $scope.videoElt.on("ended",$scope.noResponse);


            $timeout(function() {
                $scope.responded = false;
            },3000);
        });


        $scope.doMargin = function(index){
            return (index==1)?"margin-top:2%;margin-bottom:2%;":"";
        };


        var doResponse = function($scope, data){
            $scope.score = data.score;
            $scope.left = (($scope.score*100)/$scope.adGame.minScore);
            $scope.responded = true;
            $scope.token =  data.userToken;

            if(data.correct){
                $scope.correct = "ok";
            }else{
                $scope.correct = "ko";
            }

            $timeout.cancel($scope.hideResult);
            $scope.hideResult = $timeout(function() {
                $scope.responded = false;
            },3000);

            if(data.status == "Lost"){
                $location.path('/end');
            }

            if(data.status == "WinLimitTime"){
                window.location.href = addonf.base+"downloadMusics.html";
            }
        }


        var doNext = function($scope){
            $scope.index++;
            if( $scope.adGame.game.length > $scope.index){
                $scope.current = $scope.adGame.game[$scope.index];

                switch ($scope.current.type){
                    case 'AUDIO':
                        $scope.adAudio = $scope.base + "video/"+$scope.index;
                        break;
                    case 'VIDEO':
                        $scope.adVideoWebm = $scope.video($scope.index,'webm');
                        $scope.adVideoOgg = $scope.video($scope.index,'ogg');
                        $scope.adVideoMp4 = $scope.video($scope.index,'mp4');

                        $scope.videoElt[0].load();
                        $scope.videoElt[0].play();
                        break;
                    case 'STATIC':
                        $scope.adImage = $scope.image($scope.index);
                        $scope.timeoutStatic = $timeout($scope.noResponse,$scope.duration);
                        break;
                }

            }
        }


        $scope.noResponse = function(){
            Game.noResponse({index:$scope.index},function(data){
                doResponse($scope,data)
            });

            $scope.$apply(function ($scope) {
                doNext($scope);
            });

        };


        $scope.userResponse = function(userResponse){
            $timeout.cancel($scope.timeoutStatic);

            Game.doResponse({index:$scope.index,responseId:userResponse.id},function(data){
                doResponse($scope,data)
            });
            doNext($scope);
        };


        $scope.hasType3HasImage = function(possibility){
            return possibility.type==3 && possibility.answerImage!=null;
        }

        $scope.hasType3Hastext = function(possibility){
            return possibility.type==3 && possibility.answerText!=null;
        }


        $scope.video = function(index,type){
            return $scope.base + "video/"+index+"/"+type+"?"+new Date().getTime();
        }

        $scope.image = function(ad){
            return $scope.base + "video/"+index+"?"+new Date().getTime();
        }
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



adgameControllers.controller('ResumeCtrl', ['$scope', 'Game', '$timeout', '$route','$location',
    function($scope, Game, $timeout, $route,$location) {
        $scope.base = addonf.base;

        $scope.restart = function(){
            $(document).fullScreen(true);
            $location.path('/start');
        };
}]);

/* Controllers */



