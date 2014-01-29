var controllers = angular.module('myModule', ['ui.bootstrap','services','campaignServices', 'directives', 'plupload.module', 'ngResource']);

controllers.controller('Step3Ctrl', ['$scope', 'Services', 'Campaign', '$modal', '$resource',
    function($scope, Services, Campaign, $modal,$resource) {
        $scope.format = "dd/MM/yyyy";
        var CreateCampaign =  $resource(addonf.base+"createCampaign/rule",{_csrf:addonf.token},
            {
                addBrandRule: {method:'POST', params:{addBrandRule:true}, isArray:false},
                removeBrandRule: {method:'POST', params:{removeBrandRule:true}, isArray:false},
                modifyBrandRule: {method:'POST', params:{modifyBrandRule:true}, isArray:false},

                addOpenRule: {method:'POST', params:{addOpenRule:true}, isArray:false},
                removeOpenRule: {method:'POST', params:{removeOpenRule:true}, isArray:false},
                modifyOpenRule: {method:'POST', params:{modifyOpenRule:true}, isArray:false}


            });


        $scope.model = Campaign.getCurrent();


        $scope.displayMaxDisplayByUser = function(rule){
            if(rule.maxDisplayByUser == null){
                return "Pas de limite";
            }else if(rule.maxDisplayByUser==0){
                return "désactivée";
            }else{
                return rule.maxDisplayByUser;
            }

        };


        $scope.addBrandRule = function(){

            var brandRuleModal = $modal.open({
                templateUrl: addonf.base+'manageAds/partials/common/modalBrand.html',
                controller: "AddBrandRuleCtrl",
                resolve: {
                    options: function () {
                        return {
                            rule : {
                                startDate : $scope.model.information.startDate,
                                endDate : $scope.model.information.endDate
                            },
                            isNew :true,
                            minDate : $scope.model.information.startDate,
                            maxDate : $scope.model.information.endDate
                        };
                    }
                }
            });

            brandRuleModal.result.then(function (brandRule) {
                CreateCampaign.addBrandRule({},brandRule,function(data){
                    if(data.errors){
                        alert(data.errors);
                    }else{
                        $scope.model.adServices.brandRules.push(data.rule);
                    }

                });
            }, function () {});

        };


        $scope.modifyBrandRule = function(rule){
            var brandRuleModal = $modal.open({
                templateUrl: addonf.base+'manageAds/partials/common/modalBrand.html',
                controller: "AddBrandRuleCtrl",
                resolve: {
                    options: function () {
                        return {
                            rule : rule,
                            isNew :false,
                            minDate : $scope.model.information.startDate,
                            maxDate : $scope.model.information.endDate
                        };
                    }
                }
            });

            brandRuleModal.result.then(function (brandRule) {
                CreateCampaign.modifyBrandRule({},brandRule,function(data){
                    if(data.errors){
                        alert(data.errors);
                    }else{
                        // $scope.model.adServices.brandRules.push(data.rule);
                    }

                });

            }, function () {});
        };


        $scope.removeBrandRule = function(rule){

            var index = $scope.model.adServices.brandRules.indexOf(rule);
            if (index > -1) {

                CreateCampaign.removeBrandRule({},$scope.model.adServices.brandRules[index], function(data) {
                    if(data.errors){
                        alert(data.errors);
                    }else{
                        $scope.model.adServices.brandRules.splice(index,1);
                    }
                });


            }
        }

        $scope.addOpenRule= function(){

            var openRuleModal = $modal.open({
                templateUrl: addonf.base+'manageAds/partials/common/modalOpen.html',
                windowClass:"modal-more-larger",
                controller: "AddOpenRuleCtrl",
                resolve: {
                    options: function () {
                        return {
                            rule : {
                                startDate : $scope.model.information.startDate,
                                endDate : $scope.model.information.endDate
                            },
                            isNew :true,
                            minDate : $scope.model.information.startDate,
                            maxDate : $scope.model.information.endDate
                        };
                    }
                }
            });

            openRuleModal.result.then(function (openRule) {
                CreateCampaign.addOpenRule({},openRule,function(data){
                    if(data.errors){
                        alert(data.errors);
                    }else{
                        $scope.model.adServices.openRules.push(data.rule);
                    }

                });
            }, function () {});

        };



        $scope.modifyOpenRule = function(rule){
            var index = $scope.model.adServices.openRules.indexOf(rule);
            Services.reloadDlImg({numOpenRule:index});

            var openRuleModal = $modal.open({
                templateUrl: addonf.base+'manageAds/partials/common/modalOpen.html',
                windowClass:"modal-more-larger",
                controller: "AddOpenRuleCtrl",
                resolve: {
                    options: function () {
                        return {
                            rule : rule,
                            isNew :false,
                            minDate : $scope.model.information.startDate,
                            maxDate : $scope.model.information.endDate
                        };
                    }
                }
            });

            openRuleModal.result.then(function (openRule) {
                CreateCampaign.modifyOpenRule({},openRule,function(data){
                    if(data.errors){
                        alert(data.errors);
                    }else{
                        //$scope.model.adServices.openRules.push(data.rule);
                        Services.emptyDlImg();
                    }

                });

            }, function () {});
        };


        $scope.removeOpenRule = function(rule){

            var index = $scope.model.adServices.openRules.indexOf(rule);
            if (index > -1) {

                CreateCampaign.removeOpenRule({},$scope.model.adServices.openRules[index], function(data) {
                    if(data.errors){
                        alert(data.errors);
                    }else{
                        $scope.model.adServices.openRules.splice(index,1);
                    }
                });


            }
        };




    }
]);



controllers.controller('AddBrandRuleCtrl', ['$scope', 'Services', '$modalInstance', '$timeout','options',
    function($scope, Services, $modalInstance , $timeout, options) {
        $scope.formatImage = [{title : 'Image files', extensions : 'jpg,jpeg,gif,png'}];
        $scope.base=addonf.base;
        $scope.brands = [];


        $scope.minDate = options.minDate;
        $scope.maxDate = options.maxDate;


        if(options.isNew){
            $scope.brandRule = {
                noDisplayWith : [],
                startDate : options.rule.startDate,
                endDate : options.rule.endDate
            };
            $scope.btnValidate = "valider";
            $scope.nbDisplay = [1,2,3,4,5,6,7,8,9,10];
        }else{
            $scope.brandRule = options.rule;
            $scope.btnValidate = "Modifier";
            $scope.nbDisplay = [0,1,2,3,4,5,6,7,8,9,10];
        }
        $scope.selectedBrand = {};
        $scope.format = "dd/MM/yyyy";


        Services.getBrands(function(data){
            $scope.brands = data;
        });

        $scope.addNoShowWith = function(selectedBrand){
            if(!angular.isUndefined(selectedBrand.id)){
                var index = $scope.brandRule.noDisplayWith.indexOf(selectedBrand);
                if(index == -1){
                    $scope.brandRule.noDisplayWith.push(selectedBrand);
                }else{
                    alert("Companie déjà sélectionnée");
                }

            }else{
                alert("aucune companie sélectionnée");
            }
        };

        $scope.removeBrandRule = function(brand){
            var index = $scope.brandRule.noDisplayWith.indexOf(brand);
            if (index > -1) {
                $scope.brandRule.noDisplayWith.splice(index, 1);
            }
        };

        $scope.startDatepicker = {
            opened : false
        };

        $scope.endDatepicker = {
            opened : false
        };

        $scope.openDate = function(which){
            $timeout(function() {
                which.opened = true;
            });
        };

        $scope.ok = function () {
            $modalInstance.close($scope.brandRule);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }
]);



controllers.controller('AddOpenRuleCtrl', ['$scope', 'Services', '$modalInstance','$timeout', 'options',
    function($scope, Services, $modalInstance, $timeout, options) {
        $scope.base=addonf.base;
        $scope.img = [];
        $scope.percent = [];

        $scope.getUploadUrl = function (index){
            return addonf.base+"createCampaign/upload/"+"0"+"/"+index+"?_csrf="+addonf.token;
        }

        $scope.getDownloadTmpImageUrl = function (index){
            return addonf.base+"createCampaign/tmpImage/"+index+"?"+new Date().getTime();
        }

        $scope.setNowTmpImageUrl = function (index){
            $scope.img[index] = $scope.getDownloadTmpImageUrl(index);
        }


        $scope.setNowTmpImageUrl(0);
        $scope.setNowTmpImageUrl(1);
        $scope.setNowTmpImageUrl(2);

        $scope.percent[0] = 0;
        $scope.percent[1] = 0;
        $scope.percent[2] = 0;

        $scope.minDate = options.minDate;
        $scope.maxDate = options.maxDate;

        if(options.isNew){
            $scope.openRule = {
                responses : [
                    {
                        correct:false
                    },
                    {
                        correct:false
                    },
                    {
                        correct:false
                    }
                ],
                startDate : options.rule.startDate,
                endDate : options.rule.endDate
            };
            $scope.btnValidate = "valider"
        }else{
            $scope.openRule = options.rule;
            $scope.btnValidate = "Modifier";
        }

        $scope.format = "dd/MM/yyyy";
        $scope.nbDisplay = [1,2,3,4,5,6,7,8,9,10];

        $scope.startDatepicker = {
            opened : false
        };

        $scope.endDatepicker = {
            opened : false
        };

        $scope.openDate = function(which){
            $timeout(function() {
                which.opened = true;
            });
        };

        $scope.ok = function () {
            $modalInstance.close($scope.openRule);
        };

        $scope.cancel = function () {
            //empty image on session
            Services.emptyDlImg();
            $modalInstance.dismiss('cancel');
        };
    }
]);
