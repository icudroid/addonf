var MediaStatRT = function ($scope) {
    $scope.totalItems = addonf.config.media.nbTrs;
    $scope.currentPage = addonf.config.media.currentPage;

    $scope.pageChanged = function() {
        console.log('Page changed to: ' + $scope.currentPage);
        $("#formPagination").submit();
    };

    $scope.maxSize = 5;
};