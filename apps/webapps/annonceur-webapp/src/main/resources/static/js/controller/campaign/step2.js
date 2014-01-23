var controllers = angular.module('myModule', ['ui.bootstrap','services','campaignServices', 'ngResource']);

controllers.controller('Step2Ctrl', ['$scope', 'Services', 'Campaign', '$modal', '$resource',
    function($scope, Services, Campaign, $modal,$resource) {


    var CreateCampaign =  $resource(addonf.base+"createCampaign/rule",{_csrf:addonf.token},
                        {
                            addCountryRule: {method:'POST', params:{addCountryRule:true}, isArray:false},
                            removeCountryRule: {method:'POST', params:{removeCountryRule:true}, isArray:false},
                            addCityRule: {method:'POST', params:{addCityRule:true}, isArray:false},
                            removeCityRule: {method:'POST', params:{removeCityRule:true}, isArray:false},
                            setSexRule: {method:'POST', params:{setSexRule:true}, isArray:false},
                            removeSexRule: {method:'POST', params:{removeSexRule:true}, isArray:false},
                            setAgeRule: {method:'POST', params:{setAgeRule:true}, isArray:false},
                            removeAgeRule: {method:'POST', params:{removeAgeRule:true}, isArray:false}
                        });


    $scope.model = Campaign.getCurrent();

    Services.getSexes(function(data){
        $scope.sexes = data;
    });

    $scope.alreadySex = false;
    $scope.alreadyAge = false;

    $scope.addCountryRule = function(){
        var countryModal = $modal.open({
            templateUrl: addonf.base+'manageAds/partials/common/modalCountry.html',
            controller: "AddCountryRuleCtrl"
        });

        countryModal.result.then(function (selectedCountry) {
            CreateCampaign.addCountryRule({},{
                country : {
                    code:selectedCountry
                }
            }, function(data) {
                if(data.errors){
                   alert(data.errors);
                }else{
                    $scope.model.rules.countryRules.push(
                        {
                            country : {
                                code:selectedCountry
                            }
                        }
                    );
                }
            });
        }, function () {});

    };

    $scope.addCityRule = function(){
        var cityModal = $modal.open({
            templateUrl: addonf.base+'manageAds/partials/common/modalCity.html',
            controller: "AddCityRuleCtrl"
        });

        cityModal.result.then(function (selectOptionCity) {
            CreateCampaign.addCityRule({},{
                                            city : {
                                                    id :selectOptionCity.city.id
                                            },
                                            around : selectOptionCity.around
                                        }
                                    , function(data) {
                                        if(data.errors){
                                            alert(data.errors);
                                        }else{
                                            $scope.model.rules.cityRules.push(
                                                {
                                                    city : selectOptionCity.city,
                                                    around : selectOptionCity.around
                                                }
                                            );
                                        }
                                    });

        }, function () {});
    };

    $scope.addSexRule = function(){
        var sexModal = $modal.open({
                                        templateUrl: addonf.base+'manageAds/partials/common/modalSex.html',
                                        controller: "SetSexRuleCtrl",
                                        resolve: {
                                            sexes: function () {
                                                return $scope.sexes;
                                            }
                                    }
                                });

    sexModal.result.then(function (selectSex) {

        CreateCampaign.setSexRule({},{
                                        sex : selectSex
                                    }
                                    , function(data) {
                                        if(data.errors){
                                            alert(data.errors);
                                        }else{
                                            $scope.model.rules.sexRule =
                                            {
                                                sex : selectSex
                                            };
                                            $scope.alreadySex = true;
                                        }
                                    });

                                }, function () {});
        };

    $scope.addAgeRule = function(){
        var ageModal = $modal.open({
            templateUrl: addonf.base+'manageAds/partials/common/modalAge.html',
            controller: "SetAgeRuleCtrl"
        });

        ageModal.result.then(function (ageRule) {

            CreateCampaign.setAgeRule({},ageRule
                        , function(data) {
                            if(data.errors){
                                alert(data.errors);
                            }else{
                                $scope.model.rules.ageRule = ageRule;
                                $scope.alreadyAge = true;
                                }
                        });

                        }, function () {});
    };


    $scope.drawCountryRule = function(rule){
            return rule.country.code;
    };

    $scope.removeCountryRule = function(rule){
        var index = $scope.model.rules.countryRules.indexOf(rule);
            if (index > -1) {
                CreateCampaign.removeCountryRule({},$scope.model.rules.countryRules[index], function(data) {
                    if(data.errors){
                        alert(data.errors);
                    }else{
                        $scope.model.rules.countryRules.splice(index, 1);
                    }
                });


            }
    };


    $scope.drawCityRule = function(rule){
        return rule.city.city + "|" + rule.around+ " km(s)";
    };

    $scope.removeCityRule = function(rule){
        var index = $scope.model.rules.cityRules.indexOf(rule);
            if (index > -1) {
                CreateCampaign.removeCityRule({},{
                                                    city : {
                                                            id : $scope.model.rules.cityRules[index].city.id
                                                            },
                                                    around : $scope.model.rules.cityRules[index].around
                                                }, function(data) {
                                                        if(data.errors){
                                                            alert(data.errors);
                                                        }else{
                                                            $scope.model.rules.cityRules.splice(index, 1);
                                                        }
                                                });
            }
    };

    $scope.drawSexRule = function(rule){
        return rule.sex;
    };

    $scope.removeSexRule = function(rule){

        CreateCampaign.removeSexRule({}, function(data) {
            if(data.errors){
                alert(data.errors);
            }else{
                $scope.model.rules.sexRule = null;
                $scope.alreadySex = false;
            }
        });
    };


    $scope.drawAgeRule = function(rule){
        return "de " + rule.ageMin + " à "+rule.ageMax + "ans";
    };

    $scope.removeAgeRule = function(rule){

        CreateCampaign.removeAgeRule({}, function(data) {
            if(data.errors){
                alert(data.errors);
            }else{
                $scope.model.rules.ageRule = null;
                $scope.alreadyAge = false;
            }
        });

    };



    }
]);


controllers.controller('AddCountryRuleCtrl', ['$scope', 'Services', '$modalInstance',
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


controllers.controller('AddCityRuleCtrl', ['$scope', 'Services', '$modalInstance','$http',
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


controllers.controller('SetSexRuleCtrl', ['$scope', 'Services', '$modalInstance', 'sexes',
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


controllers.controller('SetAgeRuleCtrl', ['$scope', 'Services', '$modalInstance',
    function($scope, Services, $modalInstance) {

        $scope.ageRule = {
            ageMin: 18,
            ageMax: 99
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


