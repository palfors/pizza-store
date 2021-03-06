(function() {
    var app = angular.module('order', []);

    app.controller('ReviewController', function() {
        this.review = {};

        this.addReview = function(order) {
            order.reviews.push(this.review);
            this.review = {};
        };
    });

    app.directive('orderDetail', function() {
        return {
            restrict: 'E',
            templateUrl: '/pizzastore/static/angular/order/order-detail.html'
        };
    });

    app.directive('orderId', function() {
        return {
            restrict: 'A',
            templateUrl: '/pizzastore/static/angular/order/order-id.html'
        };
    });

    app.directive('orderTabs', function() {
        return {
            restrict: 'E',
            templateUrl: '/pizzastore/static/angular/order/order-tabs.html',
            controller:function(){
                this.tab = 1;

                this.setTab = function(tabValue){
                    this.tab = tabValue;
                };

                this.isSelected = function(tabValue){
                    return this.tab === tabValue;
                };
            },
            controllerAs: 'orderTabCtrl'
        };
    });

    app.service('orderService', function($http) {
        delete $http.defaults.headers.common['X-Requested-With'];
        this.getOrders = function(callbackFunc) {
            $http({
                method: 'GET',
                url: 'http://localhost:7080/pizzastore/REST/getOrders'
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