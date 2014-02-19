angular.module('myModule', ['ui.bootstrap']);


var Step1Ctrl = function($scope,$timeout) {
    $scope.format = addonf.config.dateFormat;
    $scope.model = addonf.config.step1;
    $scope.minDate = new Date();

    $scope.cannotChangeStartDate = ($scope.minDate.getTime()>$scope.model.startDate);

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

    $scope.getAcceptedMedia = function(typeMedia){
        if("VIDEO"==typeMedia){
            return "video/*";
        }else if("STATIC"==typeMedia){
            return "image/*";
        }
    }
}