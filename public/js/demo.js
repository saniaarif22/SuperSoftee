var app = angular.module('ssApp', ['ngRoute']);

app.controller('ssCtrl', function ($scope, $http) {

    $http.get('/getordersupply').
    success(function (data, status, headers, config) {
        $scope.ordersupply = data; // is data a json object?? If yes. Proceed as normal. If not redirect to securesocial login
        console.log("test"); // maybe just check if data[0] == '{'. Every Json should start with that char
        console.log(" header " + headers);
        console.log(" data " + data);

    }).
    error(function (data, headers) {
        // log error
    });

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

    approveOrder = function(id) {
        //$http.post('/approveOrder/', data, config).then(successCallback, errorCallback);
        $http({
            url: '/approveOrder/' + id,
            method: "POST",
            data: { 'message' : "id" }
        }).then(function(response) {},function(response) {});
    }


    $scope.submit = function (approveform) {
        orders = $scope.orders;
        for (i = 0; i < orders.length; i++) {
            if (orders[i].checked) {
                approveOrder(orders[i].oId);
                orders.splice(i, 1);
            }
        }
        $scope.orders = orders;
    };
});

app.controller('ssOrder', function ($scope, $http) {

    $http.get('/getordersupplies').
    success(function(data, status, headers, config) {
        $scope.ordersupplies = data; // is data a json object?? If yes. Proceed as normal. If not redirect to securesocial login
        console.log("test"); // maybe just check if data[0] == '{'. Every Json should start with that char
        console.log(" header " + headers);
        console.log(" status " + status);
        console.log(" config " +config);
        console.log(" data " +data);

    }).
    error(function(data, status, headers, config) {
        // log error
    });

});


//phonecatApp.config(['$routeProvider',
//    function($routeProvider) {
//        $routeProvider.
//            when('/', {
//                templateUrl: 'partials/phone-list.html',
//                controller: 'PhoneListCtrl'
//            })
//    }]);

