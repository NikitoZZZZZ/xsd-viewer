app.service("mainService", function($http) {
    this.postFile = function(fileName) {
        var formdata = new FormData();
        var postURL = 'http://localhost:8765/upload?name=' + fileName;
        formdata.append('xsdScheme', $('input[type=file]')[0].files[0]);
        var request = {
            method: 'POST',
            data: formdata,
            url: postURL,
            headers: {
                'Content-Type': undefined
            }
        };
        $http(request).then(function (success) {
              alert("File sent");
        }, function (error) {

        });
    }
});