'use strict';

angular.module('docs').controller('ModalRegisterRequest', function ($scope, $uibModalInstance) {
    $scope.requestData = {};

    $scope.submit = function() {
        $uibModalInstance.close($scope.requestData);
    };

    $scope.cancel = function() {
        $uibModalInstance.dismiss('cancel');
    };
});