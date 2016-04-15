var appCtrls = angular.module('ssApp', ['ngRoute']);


// create angular controller
appCtrls.controller('mainController', function($scope, $http) {

    $http.get('/getDriverEmails').
    success(function (data, status, headers, config) {
        $scope.driverEmails = data;
    }).
    error(function (data, status, headers, config) {
        // log error
    });

    $http.get('/getManagerEmails').
    success(function (data, status, headers, config) {
        $scope.managerEmails = data;
    }).
    error(function (data, status, headers, config) {
        // log error
    });

    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

    $scope.removeDriverEmail = function(email) {
        $http({
            url: '/removeDriverEmail/' + email,
            method: "POST",
            data: { 'message' : "email" }
         }).then(function(response) {},function(response) {});
        var i = $scope.driverEmails.indexOf(email);
        $scope.driverEmails.splice(i, 1);
    }

    $scope.removeManagerEmail = function(email) {
        $http({
            url: '/removeManagerEmail/' + email,
            method: "POST",
            data: { 'message' : "email" }
        }).then(function(response) {},function(response) {});
        var i = $scope.managerEmails.indexOf(email);
        $scope.managerEmails.splice(i, 1);
    }

    $scope.submitDriverEmail = function (email) {
        orders = $scope.driverEmails;
        for (i = 0; i < orders.length; i++) {
            if (orders[i].checked) {
                approveOrder(orders[i].oId);
                orders.splice(i, 1);
            }
        }
        $scope.orders = orders;
    };
});

appCtrls.controller('ssCtrl', function ($scope, $http) {

    $http.get('/getorders').
    success(function (data, status, headers, config) {
        $scope.orders = data;

    }).
    error(function (data, status, headers, config) {
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


appCtrls.controller('sidebarCtrl', function ($scope, $http) {

    $http.get('/getUserInfo').
    success(function (data, status, headers, config) {
        $scope.userInfo = data;

        if (data == "") {
            // redirect user to a page that informs that he is not authorized
            //window.location = "/auth/notauth";
        }
    }).
    error(function (data, status, headers, config) {
        // log error
    });
});


appCtrls.controller('ssOrder', function ($scope, $http) {
    console.log("SS Order Controller");

    $http.get('/getordersupplies').
    success(function(data, status, headers, config) {
        // Use if (data) to make sure we actually got data back from the server.
        if (data) {
            // Create a new array to hold each item ordered.
            $scope.driverName = [];
            $scope.driverOrders = [];
            $scope.days = [];
            $scope.dailyOrders = {};

            $scope.totalOrders = null;

            console.log(data);

            // Iterate through each order that is returned
            _.forEach(data,function (orderSupply) {
                // Ignore any old orders that don't have dates.
                if (orderSupply.orderDate === undefined || orderSupply.orderDate === null) {
                    return;
                }

                // We want just the date portion of the timestamp
                var date = orderSupply.orderDate.split(" ").slice(0,3).join(" ");

                // If we haven't created an array for this day, make one
                // to hold the orders.
                if (!$scope.dailyOrders[date]) {
                    $scope.days.push(date);
                    $scope.dailyOrders[date] = [];
                }

                // put this order in the array for this date.
                $scope.dailyOrders[date].push(orderSupply);

                // If total orders is not set, we will set it to the first order (for
                // any truck driver).
                if (null == $scope.totalOrders) {
                    // Make a copy of the order... else totalOrders will just point to
                    // the reference of the first order and the first order will end up
                    // being the totals as well.  We just care about the data, we don't
                    // want the prototype so we'll use JSON parse/stringify
                    console.log('Cloning Order Supply');
                    $scope.totalOrders = JSON.parse(JSON.stringify(orderSupply));
                } else {
                    console.log('Updating total.');
                    $scope.totalOrders.kidCone = $scope.totalOrders.kidCone+ orderSupply.kidCone;
                    $scope.totalOrders.smallCones += orderSupply.smallCones;
                    $scope.totalOrders.doubleCone += orderSupply.doubleCone;
                    $scope.totalOrders.waffleCones += orderSupply.waffleCones;
                    $scope.totalOrders.cartweels += orderSupply.cartweels;
                    $scope.totalOrders.simpleSyrup += orderSupply.simpleSyrup;
                    $scope.totalOrders.chocolateSyrup += orderSupply.chocolateSyrup;
                    $scope.totalOrders.vanillaSyrup += orderSupply.vanillaSyrup;
                    $scope.totalOrders.strawberryShakeBase += orderSupply.strawberryShakeBase;
                    $scope.totalOrders.wetWalnuts += orderSupply.wetWalnuts;
                    $scope.totalOrders.wholeCherries += orderSupply.wholeCherries;
                    $scope.totalOrders.toppingStrawberry += orderSupply.toppingStrawberry;
                    $scope.totalOrders.toppingPineapple += orderSupply.toppingPineapple;
                    $scope.totalOrders.toppingButterscotch += orderSupply.toppingButterscotch;
                    $scope.totalOrders.toppingCruchedCherry += orderSupply.toppingCruchedCherry;
                    $scope.totalOrders.smallDixieCups += orderSupply.smallDixieCups;
                    $scope.totalOrders.largeDixieCups += orderSupply.largeDixieCups;
                    $scope.totalOrders.ozCups += orderSupply.ozCups;
                    $scope.totalOrders.milkShakeCups += orderSupply.milkShakeCups;
                    $scope.totalOrders.sundaeCups += orderSupply.sundaeCups;
                    $scope.totalOrders.bananaBoats += orderSupply.bananaBoats;
                    $scope.totalOrders.lids += orderSupply.lids;
                    $scope.totalOrders.spoons += orderSupply.spoons;
                    $scope.totalOrders.straws += orderSupply.straws;
                    $scope.totalOrders.chocolateJimmies += orderSupply.chocolateJimmies;
                    $scope.totalOrders.rainbowJimmies += orderSupply.rainbowJimmies;
                    $scope.totalOrders.vanillaMix += orderSupply.vanillaMix;
                    $scope.totalOrders.fatVanillaMix += orderSupply.fatVanillaMix;
                    $scope.totalOrders.bananasBunch += orderSupply.bananasBunch;
                    $scope.totalOrders.napkins += orderSupply.napkins;
                    $scope.totalOrders.milk += orderSupply.milk;
                    $scope.totalOrders.rwbBombPop += orderSupply.rwbBombPop;
                    $scope.totalOrders.jollyRancherBombPop += orderSupply.jollyRancherBombPop;
                    $scope.totalOrders.jollyRancherSnowcone += orderSupply.jollyRancherSnowcone;
                    $scope.totalOrders.screwballCherry += orderSupply.screwballCherry;
                    $scope.totalOrders.faceBratz += orderSupply.faceBratz;
                    $scope.totalOrders.faceSonic += orderSupply.faceSonic;
                    $scope.totalOrders.minion += orderSupply.minion;
                }

                // Make sure we have an order array for this driver
                if (!$scope.driverOrders[orderSupply.driverName]) {
                    $scope.driverOrders[orderSupply.driverName] = [];
                }

                // push the order onto the drivers order array using the same
                // JSON parse/stringify method to copy just the data from
                // the object
                $scope.driverOrders[orderSupply.driverName].push(JSON.parse(JSON.stringify(orderSupply)));
            });

            // We need to help angular out here.  You can't really have nested ng-repeats
            // in a table header so instead of trying to iterate over an array of arrays
            // we're going to "flatten" the daily orders into one array that we can pass
            // to ng-repeat.
            $scope.allDailyOrders = [];

            _.forEach($scope.dailyOrders, function (dailyOrder) {
                _.forEach(dailyOrder,function (order) {
                    $scope.allDailyOrders.push(order);
                });
            });

            console.log($scope.allDailyOrders);
            console.log($scope.totalOrders);

            /*
             $scope.ordersupplies = data; // is data a json object?? If yes. Proceed as normal. If not redirect to securesocial login
             console.log("test"); // maybe just check if data[0] == '{'. Every Json should start with that char
             console.log(" header " + headers);
             console.log(" status " + status);
             console.log(" config " + config);
             console.log(" data " + data);
             */
        }
    }).
    error(function(data, status, headers, config) {
        // log error
    });



});

appCtrls.controller('ssFB', function ($scope, $http) {

    $http.get('/getfeedbacks ').
    success(function (data, status, headers, config) {
        $scope.feedbacks = data; // is data a json object?? If yes. Proceed as normal. If not redirect to securesocial login
        console.log("test"); // maybe just check if data[0] == '{'. Every Json should start with that char
        console.log(" header " + headers);
        console.log(" status " + status);
        console.log(" config " + config);
        console.log(" data " + data);

    }).
    error(function (data, status, headers, config) {
        // log error
    });
});

appCtrls.controller('ssApproved', function ($scope, $http) {

    $http.get('/getapprovedorders ').
    success(function (data, status, headers, config) {
        $scope.approvedorders = data; // is data a json object?? If yes. Proceed as normal. If not redirect to securesocial login
        console.log("test"); // maybe just check if data[0] == '{'. Every Json should start with that char
        console.log(" header " + headers);
        console.log(" status " + status);
        console.log(" config " + config);
        console.log(" data " + data);

    }).
    error(function (data, status, headers, config) {
        // log error
    });



    $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";

    finishOrder = function(id) {
        //$http.post('/finishOrder/', data, config).then(successCallback, errorCallback);
        $http({
            url: '/finishOrder/' + id,
            method: "POST",
            data: { 'message' : "id" }
        }).then(function(response) {},function(response) {});}


    $scope.submit = function (finishform) {
        approvedorders = $scope.approvedorders;
        for (i = 0; i < approvedorders.length; i++) {
            if (approvedorders[i].checked) {
                finishOrder(approvedorders[i].oId);
                approvedorders.splice(i, 1);
            }
        }
        $scope.approvedorders = approvedorders;
    };
});

appCtrls.controller('ssFinish', function ($scope, $http) {

    $http.get('/getfinishedorders').
    success(function (data, status, headers, config) {
        $scope.finishedorders = data; // is data a json object?? If yes. Proceed as normal. If not redirect to securesocial login
        console.log("test"); // maybe just check if data[0] == '{'. Every Json should start with that char
        console.log(" header " + headers);
        console.log(" status " + status);
        console.log(" config " + config);
        console.log(" data " + data);

    }).
    error(function (data, status, headers, config) {
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