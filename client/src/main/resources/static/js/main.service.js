app.service("mainService", function() {
    this.postFile = function() {
        var formdata = new FormData();
        formdata.append('xsdScheme', 'path_to_xsd...'); // TODO: Get path from input field in html
        var request = {
            method: 'POST',
            url: 'http://localhost:8765/upload?name=check1', // TODO: Get name from html
            data: formdata,
            headers: {
                'Content-Type': undefined
            }
        };
        $http(request).then(function (success) {
              alert("File send");
        }, function (error) {

        });
    }

    this.getFile = function() {
        var request = {
            method: 'GET',
            url: 'http://localhost:8765/check3',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                }
            };

        $http(request).then(function (response) {
            $scope.myData = JSON.stringify(response.data, null, 4);
        }, function (error) {
            console.info()
        });
    }
});