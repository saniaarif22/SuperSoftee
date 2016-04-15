var app = angular.module('ssApp', [
    'ngRoute',
    'ssCtrl'
]);

phonecatApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/menu', {
                templateUrl: 'partials/menu.html',
                controller: 'PhoneListCtrl'
            }).
            when('/order', {
                templateUrl: 'partials/order.html',
                controller: 'PhoneDetailCtrl'
            }).
            when('/contact', {
                templateUrl: 'partials/phone-detail.html',
                controller: 'PhoneDetailCtrl'
            }).
            otherwise({
                redirectTo: '/phones'
            });
    }]);