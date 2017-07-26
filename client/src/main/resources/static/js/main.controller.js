var app=angular.module("WebApp").controller('ctrl', ['$scope', '$http', function($scope, $http) {
    $scope.postFile = function() {
        var xsd = angular.element("schema");
        $http.post("localhost:8765/upload", xsd);
    };
}]);