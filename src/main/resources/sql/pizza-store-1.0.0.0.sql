-- drop constraints if they exist
-- alter table STORE_ORDER drop foreign key 'order_store_fk';
-- alter table STORE_ORDER drop foreign key 'order_customer_fk';
-- alter table ORDER_LINE drop foreign key 'orderline_order_fk';
-- alter table ORDER_LINE drop foreign key 'orderline_menuitem_fk';
-- alter table ORDER_LINE_PIZZA_DETAILS drop foreign key 'oldetails_orderline_fk';
-- alter table ORDER_LINE_PIZZA_DETAILS drop foreign key 'oldetails_pizzadetail_fk';

-- drop tables if they exist
drop table IF EXISTS ORDER_LINE_PIZZA_DETAILS cascade;
drop table IF EXISTS ORDER_LINE cascade;
drop table IF EXISTS STORE_ORDER cascade;
drop table IF EXISTS PIZZA_DETAIL cascade;
drop table IF EXISTS MENUITEM cascade;
drop table IF EXISTS CUSTOMER cascade;
drop table IF EXISTS STORE cascade;
drop table IF EXISTS PIZZA_STORE_DB_INFO cascade;

-- create the tables

--
create table PIZZA_STORE_DB_INFO (
    db_version VARCHAR(20)
);
insert into pizza_store_db_info (db_version) values ('1.0.0.0');

-- defines the available pizza stores
create table STORE(
    storeId BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(200)
);
-- default data
insert into store (storeId, name) values (1, 'Pizzeria Pete');

-- defines the current customer base
create table CUSTOMER(
    customerId BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

-- definition of a menu item (ie: a pizza or side item)
create table MENUITEM(
    menuItemId BIGINT NOT NULL PRIMARY KEY,
    menuItemType VARCHAR(50),
    name VARCHAR(200),
    price DOUBLE
);
-- default data
insert into menuitem (menuItemId, menuItemType, name, price) values (1,'PIZZA', 'Pizza', 5.00);
insert into menuitem (menuItemId, menuItemType, name, price) values (2,'SIDE', 'Breadsticks', 2.25);

-- defines the available pizza details
create table PIZZA_DETAIL(
    pizzaDetailId BIGINT NOT NULL PRIMARY KEY,
    detailType VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    price DOUBLE NOT NULL
);
-- default data
insert into pizza_detail (pizzaDetailId, detailType, name, price)
    values (1, 'CRUST', 'Thin Crust', 0.00);
insert into pizza_detail (pizzaDetailId, detailType, name, price)
    values (2, 'CRUST', 'Regular Crust', 0.50);
insert into pizza_detail (pizzaDetailId, detailType, name, price)
    values (3, 'CRUST', 'Pan Crust', 1.25);
insert into pizza_detail (pizzaDetailId, detailType, name, price)
    values (4, 'TOPPPING', 'Sausage', 0.75);
insert into pizza_detail (pizzaDetailId, detailType, name, price)
    values (5, 'TOPPPING', 'Pepperoni', 0.75);
insert into pizza_detail (pizzaDetailId, detailType, name, price)
    values (6, 'TOPPPING', 'Onion', 0.50);
insert into pizza_detail (pizzaDetailId, detailType, name, price)
    values (7, 'TOPPPING', 'Mushroom', 0.50);

-- defines a given store order
create table STORE_ORDER(
    orderId BIGINT NOT NULL PRIMARY KEY,
    storeId BIGINT NOT NULL,
    customerId BIGINT NOT NULL,
    price DOUBLE NOT NULL,
    CONSTRAINT order_store_fk FOREIGN KEY(storeId) REFERENCES store(storeId),
    CONSTRAINT order_customer_fk FOREIGN KEY(customerId) REFERENCES customer(customerId)
);

-- defines an order line (ie: the menu item and qty purchased)
create table ORDER_LINE(
    orderLineId BIGINT NOT NULL PRIMARY KEY,
    orderId BIGINT NOT NULL,
    menuItemId BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    price DOUBLE NOT NULL,
    CONSTRAINT orderline_order_fk FOREIGN KEY(orderId) REFERENCES store_order(orderId),
    CONSTRAINT orderline_menuitem_fk FOREIGN KEY(menuItemId) REFERENCES menuitem(menuItemId)
);

create table ORDER_LINE_PIZZA_DETAILS(
    orderLineDetailId BIGINT NOT NULL PRIMARY KEY,
    orderLineId BIGINT NOT NULL,
    pizzaDetailId BIGINT NOT NULL,
    placement VARCHAR(20) NOT NULL,
    CONSTRAINT oldetails_orderline_fk FOREIGN KEY(orderLineId) REFERENCES order_line(orderLineId),
    CONSTRAINT oldetails_pizzadetail_fk FOREIGN KEY(pizzaDetailId) REFERENCES pizza_detail(pizzaDetailId)
);

commit;