-- drop new tables created for this version
drop table IF EXISTS ORDER_LINE_DETAIL cascade;
drop table IF EXISTS MENUITEM_DETAIL cascade;
drop table IF EXISTS APP_PROPERTIES cascade;

-- truncate data from preview version tables
delete from ORDER_LINE;
delete from STORE_ORDER;
delete from MENUITEM;
delete from CUSTOMER;
delete from STORE;

-- drop tables no longer needed for this version
drop table IF EXISTS ORDER_LINE_PIZZA_DETAILS cascade;
drop table IF EXISTS PIZZA_DETAIL cascade;
drop table IF EXISTS PIZZA_STORE_DB_INFO;

insert into MENUITEM (menuItemId, menuItemType, name, price)
    values (1, 'pizza', 'Pizza', 5.25);
insert into MENUITEM (menuItemId, menuItemType, name, price)
    values (2, 'breadsticks', 'Breadsticks', 1.25);

-- defines the available menuItem details, such as pizza toppings/crust
create table MENUITEM_DETAIL (
    menuItemDetailId BIGINT NOT NULL PRIMARY KEY,
    menuItemId BIGINT NOT NULL,
    detailType VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    price DOUBLE NOT NULL,
    CONSTRAINT midetail_menuitem_fk FOREIGN KEY(menuItemId) REFERENCES menuitem(menuItemId)
);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (1, 1, 'crust', 'Thin Crust', 0.00);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (2, 1, 'crust', 'Regular Crust', 0.50);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (3, 1, 'crust', 'Pan Crust', 1.00);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (4, 1, 'topping', 'Sausage', 1.75);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (5, 1, 'topping', 'Pepperoni', 1.75);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (6, 1, 'topping', 'Onion', 0.75);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (7, 1, 'topping', 'Mushroom', 0.75);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (8, 1, 'topping', 'Green Pepper', 0.75);
insert into MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price)
    values (9, 1, 'topping', 'Bacon', 0.75);

create table ORDER_LINE_DETAIL(
    orderLineDetailId BIGINT NOT NULL PRIMARY KEY,
    orderLineId BIGINT NOT NULL,
    menuItemDetailId BIGINT NOT NULL,
    placement VARCHAR(20) NOT NULL,
    CONSTRAINT oldetails_orderline_fk FOREIGN KEY(orderLineId) REFERENCES order_line(orderLineId),
    CONSTRAINT oldetails_menuitemdetail_fk FOREIGN KEY(menuItemDetailId) REFERENCES menuitem_detail(menuItemDetailId)
);

create table APP_PROPERTIES (
    property_key VARCHAR(200) NOT NULL,
    property_value VARCHAR(200) NOT NULL
);
insert into APP_PROPERTIES (property_key, property_value) values ("db_version", "1.0.0.1");
