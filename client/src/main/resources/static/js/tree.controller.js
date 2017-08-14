app.controller("TreeCtrl", ["$scope", "$http", "treeService", function($scope, $http, treeService) {

  var names = [];
            var request = {
                method: 'GET',
                url: 'http://localhost:8765/all',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    }
                };

            $http(request).then(function (response) {
                names = response.data.slice();
                $scope.schemaList = [];

                // Initialization of Select field
                for(var i = 0; i < names.length; ++i) {
                  $scope.schemaList[i] = { value: names[i], label: names[i] };
                }
            }, function (error) {
                console.info()
            });

  $scope.showTree = treeService.showTree;
}]);