(function() {
    var app = angular.module('orders', []);

    app.controller('TabController', function() {
        this.tab = 1;

        this.setTab = function(tabValue){
            this.tab = tabValue;
        };

        this.isSelected = function(tabValue){
            return this.tab === tabValue;
        };
    });

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
            templateUrl: 'order/order-detail.html'
        };
    });

    app.directive('orderId', function() {
        return {
            restrict: 'A',
            templateUrl: 'order/order-id.html'
        };
    });

    app.directive('orderTabs', function() {
        return {
            restrict: 'E',
            templateUrl: 'order/order-tabs.html',
            controller:function(){
                this.tab = 1;

                this.setTab = function(tabValue){
                    this.tab = tabValue;
                };

                this.isSelected = function(tabValue){
                    return this.tab === tabValue;
                };
            },
            controllerAs: 'tabctrl'
        };
    });

})();