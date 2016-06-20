(function() {
    var app = angular.module('customer', []);

    app.controller('CustomerTabController', function() {
        this.tab = 1;

        this.setTab = function(tabValue){
            this.tab = tabValue;
        };

        this.isSelected = function(tabValue){
            return this.tab === tabValue;
        };
    });

    app.directive('customerDetail', function() {
        return {
            restrict: 'E',
            templateUrl: '/pizzastore/static/angular/customer/customer-detail.html'
        };
    });

    app.directive('customerId', function() {
        return {
            restrict: 'A',
            templateUrl: '/pizzastore/static/angular/customer/customer-id.html'
        };
    });

    app.directive('customerTabs', function() {
        return {
            restrict: 'E',
            templateUrl: '/pizzastore/static/angular/customer/customer-tabs.html',
            controller:function(){
                this.tab = 1;

                this.setTab = function(tabValue){
                    this.tab = tabValue;
                };

                this.isSelected = function(tabValue){
                    return this.tab === tabValue;
                };
            },
            controllerAs: 'customerTabCtrl'
        };
    });

    app.service('customerService', function($http) {
        delete $http.defaults.headers.common['X-Requested-With'];
        this.getCustomers = function(callbackFunc) {
            $http({
                method: 'GET',
                url: 'http://localhost:7080/pizzastore/REST/getCustomers'
            }).success(function(data){
                    console.log(data);
                    // With the data succesfully returned, call our callback
                    callbackFunc(data);
                }).error(function(){
                        alert("error");
                    });
        }
    });

})();