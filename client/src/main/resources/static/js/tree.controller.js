app.controller("TreeCtrl", ["$scope", "treeService", function($scope, treeService) {

  var names = [ 'check1', 'check2', 'check3' ];

  $scope.schemaList = [];

  for(var i = 0; i < names.length; ++i) {
    $scope.schemaList[i] = { value: names[i], label: names[i] };
  }

  $scope.showTree = treeService.showTree;

}]);