(function() {
    var app = angular.module('store', ['orders']);

//    app.controller('StoreController', ['$http', '$log', function($http, $log) {
//        var the_store = this;
//        this.orders = test_orders;
//
//        $http.get('/REST/getOrder/11').success(function(data){
//        $http.get('http://localhost:7080/pizzastore/REST/getOrder/1').success(function(data){
//            the_store.orders = data;
//        });
//    }]);
//
    app.controller('StoreController', function() {
        this.orders = test_orders;
    });

    var test_orders = [
        {
            orderId: 'ps1',
            customer: 'Bob',
            store: 'Petes Store!',
            price: 2.95,
            createDate: 1465843877000,
            editable: true,
            closed: false,
            images: [ {
                c1: '../../../resources/images/customer/customer1.jpg',
                c2: '../../../resources/images/customer/customer2.jpg',
                c3: '../../../resources/images/customer/customer3.jpg',
                c4: '../../../resources/images/customer/customer4.jpg'
            } ],
            reviews: [
                {
                    stars: 5,
                    body: 'The order experience was awesome!',
                    author: 'bob@cat.com'
                },
                {
                    stars: 2,
                    body: 'When it got to my house the pizza was cold',
                    author: 'bob@cat.com'
                }
            ]
        },
        {
            orderId: 'ps2',
            customer: 'Carl',
            store: 'Petes Store!',
            price: 3.25,
            createDate: 1465843894000,
            editable: false,
            closed: true,
            images: [ {
                c1: '../../../resources/images/customer/customer1.jpg',
                c2: '../../../resources/images/customer/customer2.jpg',
                c3: '../../../resources/images/customer/customer3.jpg',
                c4: '../../../resources/images/customer/customer4.jpg'
                } ]
        },
        {
            orderId: 'ls1',
            customer: 'Lisa',
            store: 'Larrys Store!',
            price: 3.00,
            createDate: 1464989109000,
            editable: true,
            closed: false,
            images: [ {
                c1: '../../../resources/images/customer/customer1.jpg',
                c2: '../../../resources/images/customer/customer2.jpg',
                c3: '../../../resources/images/customer/customer3.jpg',
                c4: '../../../resources/images/customer/customer4.jpg'
                } ]
        }];
})();