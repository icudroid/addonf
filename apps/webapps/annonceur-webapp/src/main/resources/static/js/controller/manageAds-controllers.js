'use strict';

/* Controllers */

var manageAdsControllers = angular.module('manageAdsControllers', ['ngRoute','ui.bootstrap','angularFileUpload']);


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

                    if(angular.isDefined(data.errors)){
                        $scope.drawErrors(data.errors);
                    }else{
                        $location.path("/step2");
                    }

                    console.log(data);
            });



        };

        $scope.adFile;

        $scope.onFileSelect = function($files){
            for (var i = 0; i < $files.length; i++) {
                $scope.adFile = $files[i];
            }
        };


        $scope.drawErrors = function(errors){

        };


        $scope.getAcceptedMedia = function(typeMedia){
            if("VIDEO"==typeMedia){
                return "video/*";
            }else if("STATIC"==typeMedia){
                return "image/*";
            }
        }

    }
]);




manageAdsControllers.controller('Step2Ctrl', ['$scope', 'Ads', 'Campaign', '$interval','$timeout', '$route', '$location','$upload',
    function($scope, Ads, Campaign,$interval, $timeout, $route, $location,$upload) {
        $scope.model = Campaign.getCurrent();



        $scope.submit = function(){


            $scope.upload = $upload.upload({
                url: addonf.base+'manageAds/saveStep/2?_csrf='+addonf.token, //upload.php script, node.js route, or servlet url
                // method: POST or PUT,
                // headers: {'headerKey': 'headerValue'}, withCredential: true,
                data: {command: $scope.model},
                file: $scope.productLogo
                // file: $files, //upload multiple files, this feature only works in HTML5 FromData browsers
                /* set file formData name for 'Content-Desposition' header. Default: 'file' */
                //fileFormDataName: myFile,
                /* customize how data is added to formData. See #40#issuecomment-28612000 for example */
                //formDataAppender: function(formData, key, val){}
            }).progress(function(evt) {
                    console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
                }).success(function(data, status, headers, config) {
                    // file is uploaded successfully

                    if(angular.isDefined(data.errors)){
                        $scope.drawErrors(data.errors);
                    }else{
                        $location.path("/step3");
                    }

                    console.log(data);
                });



        };



        $scope.productLogo;

        $scope.onFileSelect = function($files){
            for (var i = 0; i < $files.length; i++) {
                $scope.productLogo = $files[i];
            }
        };

    }
]);



manageAdsControllers.controller('Step3Ctrl', ['$scope', 'Ads', 'Campaign', 'Services', '$interval','$timeout', '$route', '$location','$upload','$modal',
    function($scope, Ads, Campaign, Services, $interval, $timeout, $route, $location,$upload,$modal) {
        $scope.model = Campaign.getCurrent();

        Services.getSexes(function(data){
            $scope.sexes = data;
        });

        $scope.alreadySex = false;
        $scope.alreadyAge = false;

        $scope.addCountryRule = function(){
            var countryModal = $modal.open({
                templateUrl: addonf.base+'manageAds/partials/create/modalCountry.html',
                controller: "AddCountryRuleCtrl"
            });

            countryModal.result.then(function (selectedCountry) {
                $scope.model.rules.countryRules.push(
                    {
                        country : {
                            code:selectedCountry
                        }
                    }
                );
            }, function () {});

        };
        $scope.addCityRule = function(){
            var cityModal = $modal.open({
                templateUrl: addonf.base+'manageAds/partials/create/modalCity.html',
                controller: "AddCityRuleCtrl"
            });

            cityModal.result.then(function (selectOptionCity) {
                $scope.model.rules.cityRules.push(
                    {
                        city : selectOptionCity.city,
                        around : selectOptionCity.around
                    }
                );
            }, function () {});
        };
        $scope.addSexRule = function(){
            var sexModal = $modal.open({
                templateUrl: addonf.base+'manageAds/partials/create/modalSex.html',
                controller: "SetSexRuleCtrl",
                resolve: {
                    sexes: function () {
                        return $scope.sexes;
                    }
                }
            });

            sexModal.result.then(function (selectSex) {
                $scope.model.rules.sexRule =
                    {
                        sex : selectSex
                    };
                $scope.alreadySex = true;
            }, function () {});
        };
        $scope.addAgeRule = function(){
            var ageModal = $modal.open({
                templateUrl: addonf.base+'manageAds/partials/create/modalAge.html',
                controller: "SetAgeRuleCtrl"
            });

            ageModal.result.then(function (ageRule) {
                $scope.model.rules.ageRule = ageRule;
                $scope.alreadyAge = true;
            }, function () {});
        };


        $scope.drawCountryRule = function(rule){
            return rule.country.code;
        };
        $scope.removeCountryRule = function(rule){
            var index = $scope.model.rules.countryRules.indexOf(rule);
            if (index > -1) {
                $scope.model.rules.countryRules.splice(index, 1);
            }
        };


        $scope.drawCityRule = function(rule){
            return rule.city.city + "|" + rule.around+ " km(s)";
        };
        $scope.removeCityRule = function(rule){
            var index = $scope.model.rules.cityRules.indexOf(rule);
            if (index > -1) {
                $scope.model.rules.cityRules.splice(index, 1);
            }
        };

        $scope.drawSexRule = function(rule){
            return rule.sex;
        };
        $scope.removeSexRule = function(rule){
            $scope.model.rules.sexRule = null;
            $scope.alreadySex = false;
        };


        $scope.drawAgeRule = function(rule){
            return "de " + rule.ageMin + " à "+rule.ageMax;
        };
        $scope.removeAgeRule = function(rule){
            $scope.model.rules.ageRule = null;
            $scope.alreadyAge = false;
        };


        $scope.submit = function(){

            Campaign.save($scope.model,3,function(data){
                if(angular.isDefined(data.errors)){
                    $scope.drawErrors(data.errors);
                }else{
                    $location.path("/step4");
                }
            });

        };

    }
]);


manageAdsControllers.controller('AddCountryRuleCtrl', ['$scope', 'Services', '$modalInstance',
    function($scope, Services, $modalInstance) {
        $scope.countries = [];
        $scope.selectedCountry = {};

        Services.getCountries(function(data){
            $scope.countries = data;
            $scope.selectedCountry = $scope.countries[0];
        });

        $scope.ok = function () {
            if($scope.selectedCountry==null){
                window.alert("Selectionnez un pays");
            }else{
                $modalInstance.close($scope.selectedCountry.value);
            }
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }
]);


manageAdsControllers.controller('AddCityRuleCtrl', ['$scope', 'Services', '$modalInstance','$http',
    function($scope, Services, $modalInstance,$http) {
        $scope.selectedCity = {
            city : null,
            around: 0
        };

        $scope.getCitiesByName = function(val){
            return $http.get(addonf.base+'getTownsByName/'+val ,{
                params: {
                    _csrf:addonf.token
                }
            }).then(function(data){
                var res = [];
                    angular.forEach(data.data, function(item){
                        item.label =item.zipcode+" "+item.city+", "+item.country.code;
                        res.push(item);
                    });
                 return res;
            });
        };


        $scope.ok = function () {
            if($scope.selectedCity.city==null || $scope.selectedCity.around==null){
                window.alert("Données incomplètes");
            }else{
                $modalInstance.close($scope.selectedCity);
            }

        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }
]);


manageAdsControllers.controller('SetSexRuleCtrl', ['$scope', 'Services', '$modalInstance', 'sexes',
    function($scope, Services, $modalInstance , sexes) {
        $scope.sexes = sexes;

        $scope.selectedSex = {
            item: $scope.sexes[0]
        };

        $scope.ok = function () {
            if($scope.selectedSex.item==null){
                window.alert("Données incomplètes");
            }else{
                $modalInstance.close($scope.selectedSex.item.value);
            }

        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }
]);


manageAdsControllers.controller('SetAgeRuleCtrl', ['$scope', 'Services', '$modalInstance',
    function($scope, Services, $modalInstance) {

        $scope.ageRule = {
            ageMin: 0,
            ageMax: 0
        };

        $scope.ok = function () {
            if($scope.ageRule.ageMin==null ||$scope.ageRule.ageMax==null){
                window.alert("Données incomplètes");
            }else{
                $modalInstance.close( $scope.ageRule);
            }

        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }
]);




manageAdsControllers.controller('Step4Ctrl', ['$scope', 'Ads', 'Campaign', 'Services', '$interval','$timeout', '$route', '$location','$upload','$modal',
    function($scope, Ads, Campaign, Services, $interval, $timeout, $route, $location,$upload,$modal) {
        $scope.model = Campaign.getCurrent();



        $scope.addBrandRule = function(){

        };

        $scope.addProductRule = function(){

        };


        $scope.addOpenRule = function(){

        };

        $scope.removeBrandRule = function(rule){

        };


        $scope.removeProductRule = function(rule){

        };

        $scope.removeOpenRule = function(rule){

        };

        $scope.submit = function(){

        }

    }
]);



/* Controllers */



