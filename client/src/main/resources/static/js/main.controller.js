angular.module("WebApp").controller('ctrl', ['$scope','$http', function($scope, $http) {
    var xsd = angular.element("schema");
    $http.post("localhost:8080/upload", xsd);
}]);