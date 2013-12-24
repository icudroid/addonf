'use strict';

/* Controllers */

var manageAdsControllers = angular.module('manageAdsControllers', ['ngRoute','ui.bootstrap','angularFileUpload']);

manageAdsControllers.controller('ManageAdsCtrl', ['$scope', 'Ads', '$interval','$timeout', '$route', '$location',
    function($scope, Ads, $interval, $timeout, $route, $location) {
         $scope.page = 0;
         $scope.size=3;
         $scope.sort='startDate,desc';
         $scope.results = [];
         $scope.nb = 0;

         Ads.getAll({page:$scope.page,size:$scope.size,sort:$scope.sort},function(data){
             $scope.results = data.content;
             $scope.nb = data.totalElements;
         });

        $scope.create = function(){
            $location.path("/create");
        }
    }
]);



manageAdsControllers.controller('CreateAdsCtrl', ['$scope', 'Ads', 'Campaign', '$interval','$timeout', '$route', '$location','$upload',
    function($scope, Ads, Campaign,$interval, $timeout, $route, $location,$upload) {
        $scope.model = Campaign.getCurrent();
        $scope.format = addonf.config.dateFormat;

        $scope.startDatepicker = {
            opened : false
        };

        $scope.endDate = {
            opened : false
        };

        $scope.startDatepicker = {
            'year-format': "'yy'",
            'starting-day': 1
        };

        $scope.openDate = function(which){
            $timeout(function() {
                which.opened = true;
            });
        };


        $scope.submit = function(){


            $scope.upload = $upload.upload({
                url: addonf.base+'manageAds/saveStep/1?_csrf='+addonf.token, //upload.php script, node.js route, or servlet url
                // method: POST or PUT,
                // headers: {'headerKey': 'headerValue'}, withCredential: true,
                data: {command: $scope.model},
                file: $scope.adFile
                // file: $files, //upload multiple files, this feature only works in HTML5 FromData browsers
                /* set file formData name for 'Content-Desposition' header. Default: 'file' */
                //fileFormDataName: myFile,
                /* customize how data is added to formData. See #40#issuecomment-28612000 for example */
                //formDataAppender: function(formData, key, val){}
            }).progress(function(evt) {
                    console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
                }).success(function(data, status, headers, config) {
                    // file is uploaded successfully
                    console.log(data);
            });



        };

        $scope.adFile;

        $scope.onFileSelect = function($files){
            for (var i = 0; i < $files.length; i++) {
                $scope.adFile = $files[i];
            }
        }

    }
]);
/* Controllers */



