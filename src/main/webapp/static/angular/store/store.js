(function() {
    var app = angular.module('store', []);

    app.controller('StoreTabController', function() {
        this.tab = 1;

        this.setTab = function(tabValue){
            this.tab = tabValue;
        };

        this.isSelected = function(tabValue){
            return this.tab === tabValue;
        };
    });

    app.directive('storeDetail', function() {
        return {
            restrict: 'E',
            templateUrl: '/pizzastore/static/angular/store/store-detail.html'
        };
    });

    app.directive('storeId', function() {
        return {
            restrict: 'A',
            templateUrl: '/pizzastore/static/angular/store/store-id.html'
        };
    });

    app.directive('storeTabs', function() {
        return {
            restrict: 'E',
            templateUrl: '/pizzastore/static/angular/store/store-tabs.html',
            controller:function(){
                this.tab = 1;

                this.setTab = function(tabValue){
                    this.tab = tabValue;
                };

                this.isSelected = function(tabValue){
                    return this.tab === tabValue;
                };
            },
            controllerAs: 'storeTabCtrl'
        };
    });

    app.service('storeService', function($http) {
        delete $http.defaults.headers.common['X-Requested-With'];
        this.getStores = function(callbackFunc) {
            $http({
                method: 'GET',
                url: 'http://localhost:7080/pizzastore/REST/getStores'
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