'use strict';

/* Controllers */

var adgameControllers = angular.module('adgameControllers', ['ngRoute','ui.bootstrap']);

adgameControllers.controller('GameCtrl', ['$scope', 'Game', '$interval','$timeout', '$route', '$location',
    function($scope, Game, $interval, $timeout, $route, $location) {


        var video = function(index,type){
            console.log("video",index,type);
            return $scope.base + "video/"+index+"/"+type+"?"+new Date().getTime();
        }

        var image = function(index){
            console.log("image",index);
            return $scope.base + "video/"+index+"?"+new Date().getTime();
        }



        $scope.multiResponse = [];
        $scope.base = addonf.base;
        $scope.videoElt = angular.element("video");
        //$scope.left = 0;
        $scope.responded = false;
        $scope.correct = "noreponse";
        $scope.timeoutStatic;



        if(addonf.restart){
            $scope.index = addonf.restart.index;
            $scope.score = addonf.restart.score;
        }else{
            $scope.index = 0;
            $scope.score = 0;
        }


        //$scope.adVideoWebm = video($scope.index,'webm');
        $scope.adVideoOgg = video($scope.index,'ogv');
        $scope.adVideoMp4 = video($scope.index,'mp4');
        $scope.adImage = image($scope.index);
        $scope.adAudio = $scope.base + "video/"+$scope.index;


        //initialize
        $scope.adGame = addonf.game;
        $scope.max =  $scope.adGame.score;
        $scope.current = $scope.adGame.game[$scope.index];
        $scope.gooseCases =  $scope.adGame.gooseGames;
        $scope.token =  $scope.adGame.userToken;
        $scope.left = (($scope.score*100)/$scope.adGame.score);


        switch ($scope.current.type.$name){
            case 'AUDIO':
                $scope.adAudio = $scope.base + "video/"+$scope.index;
                break;
            case 'VIDEO':
                //$scope.adVideoWebm = video($scope.index,'webm');
                $scope.adVideoOgg = video($scope.index,'ogv');
                $scope.adVideoMp4 = video($scope.index,'mp4');
                $scope.videoElt.unbind("ended",$scope.noResponse).bind("ended",$scope.noResponse);
                break;
            case 'STATIC':
                $scope.adImage = image($scope.index);
                break;
        }

        $scope.gameStarted =  !$scope.adGame.showSplashScreen;

        angular.element(document).bind("fullscreenchange", function(e) {
            console.log("fullscreenchange");

            if($(document).fullScreen()===false){
                $scope.$apply(function(){
                    //save addonf index
                    addonf.restart = {
                        index : $scope.index,
                        score : $scope.score
                    };

                    //stop all
                    $timeout.cancel($scope.timeoutStatic);
                    $scope.videoElt[0].pause();
                    $scope.videoElt.unbind("ended",$scope.noResponse)
                    //window.alert('stop');
                    $location.path('/resume');
                    //window.location.href = addonf.base+"resume";
                })
            }
        });




        $scope.startGame = function(){
            $(document).fullScreen(true);
            switch ($scope.current.type.$name){
                case 'AUDIO':
                    break;
                case 'VIDEO':
                    $scope.videoElt[0].load();
                    $scope.videoElt[0].play();
                    $scope.videoElt.unbind("ended",$scope.noResponse).bind("ended",$scope.noResponse);
                    break;
                case 'STATIC':
                    $scope.timeoutStatic = $timeout($scope.noResponse,$scope.current.duration);
                    break;
            }
            $scope.gameStarted = true;
        }


        if($scope.gameStarted){
            $scope.startGame();
        }

        $scope.doMargin = function(index){
            return (index==1)?"margin-top:2%;margin-bottom:2%;":"";
        };

        var doResponse = function(data){
            $scope.score = data.score;
            $scope.left = (($scope.score*100)/$scope.adGame.score);
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


            if(data.status == "WinLimitTime"){
                //create from for redirect
                var $form = $("<form>").attr("action",data.whereToGo).attr("method","post").hide();
                $form.append("<input type='hidden' name='idTransaction' value='"+data.idTransaction+"' />");
                $('body').append($form);
                $form.submit();

            }else if(data.status == "Lost"){

                    addonf.lost = {
                        whereToGo : data.whereToGo,
                        idTransaction : data.idTransaction
                    };

                    $timeout.cancel($scope.timeoutStatic);
                    $scope.videoElt[0].pause();
                    $scope.videoElt.unbind("ended",$scope.noResponse)
                    $location.path('/end');

            }
        }


        var doNext = function(){
            $scope.index++;
            console.log($scope.index);
            if( $scope.adGame.game.length > $scope.index){
                $scope.current = $scope.adGame.game[$scope.index];

                $scope.videoElt[0].pause();
                switch ($scope.current.type.$name){
                    case 'AUDIO':
                        $scope.adAudio = $scope.base + "video/"+$scope.index;
                        $scope.videoElt.unbind("ended",$scope.noResponse);
                        //$scope.$apply();
                        break;
                    case 'VIDEO':

                            //$scope.adVideoWebm = video($scope.index,'webm');
                            $scope.adVideoOgg = video($scope.index,'ogv');
                            $scope.adVideoMp4 = video($scope.index,'mp4');
                            $scope.$apply();
                            $scope.videoElt[0].load();
                            $scope.videoElt[0].play();

                        $scope.videoElt.unbind("ended",$scope.noResponse).bind("ended",$scope.noResponse);


                        break;
                    case 'STATIC':
                        $scope.adImage = image($scope.index);
                        $scope.timeoutStatic = $timeout($scope.noResponse,$scope.current.duration);
                        $scope.videoElt.unbind("ended",$scope.noResponse);
                        break;
                }

            }
        }


        $scope.noResponse = function(){
            $scope.$apply(function () {
                Game.noResponse({index:$scope.index},function(data){
                    doResponse(data)
                });
                doNext();
            });
        };


        $scope.userResponse = function(userResponse){
            $timeout.cancel($scope.timeoutStatic);
            Game.doResponse({index:$scope.index,responseId:userResponse.id},function(data){
                doResponse(data)
            });
            doNext();
        };


        $scope.userResponseMulti = function(){
            $timeout.cancel($scope.timeoutStatic);
            Game.doResponseMulti({index:$scope.index,responseId:$scope.multiResponse},function(data){
                doResponse(data)
            });
            doNext();
        };


        $scope.hasType3HasImage = function(possibility){
            return possibility.type==3 && possibility.answerImage!=null;
        }

        $scope.hasType3HasText = function(possibility){
            return possibility.type==3 && possibility.answerText!=null;
        }



    }]);



adgameControllers.controller('EndCtrl', ['$scope', 'Game', '$timeout', '$route',
    function($scope, Game, $timeout, $route) {

        $scope.base = addonf.base;
        $timeout(function(){

            var $form = $("<form>").attr("action",addonf.lost.whereToGo).attr("method","post").hide();
            $form.append("<input type='hidden' name='idTransaction' value='"+addonf.lost.idTransaction+"' />");
            $('body').append($form);
            $form.submit();

        },3000);


    }]);



adgameControllers.controller('ResumeCtrl', ['$scope', 'Game', '$timeout', '$route','$location',
    function($scope, Game, $timeout, $route,$location) {
        $scope.base = addonf.base;

        $scope.resume = function(){
            $(document).fullScreen(true);
            $location.path('/start');
        };
    }]);

/* Controllers */



