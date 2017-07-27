var app = angular.module("WebApp", []).controller('ctrl', function ($scope, $http) {

    //$scope.uploader = new FileUploader();
    //console.info($scope.uploader)

    /*$scope.uploadFile = function(files) {
        var fd = new FormData();
        //Take the first selected file
        fd.append("file", files[0]);

        $http.post(uploadUrl, fd, {
            withCredentials: true,
            headers: {'Content-Type': undefined },
            transformRequest: angular.identity
        }).success( ...all right!... ).error( ..damn!... );

    };*/

    $scope.postFile = function () {
        var formdata = new FormData();
        formdata.append('xsdScheme', '/home/nikita/Downloads/ex1.xsd');
        var request = {
            method: 'POST',
            url: 'http://localhost:8765/upload?name=check1',
            data: formdata,
            headers: {
                'Content-Type': undefined
            }
        };
        $http(request).then(function (success) {
              alert("File send");
        }, function (error) {

        });
    };

    $scope.getFile = function () {
        var request = {
            method: 'GET',
            url: 'http://localhost:8765/check1',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Access-Control-Allow-Origin': '*'
            }
        };

        $http(request).then(function (success) {
            alert(data);
        }, function (error) {

        });
    };
});