delete from MENUITEM_DETAIL;
delete from MENUITEM;
delete from APP_PROPERTIES;

-- app_properties data
INSERT INTO APP_PROPERTIES (property_key, property_value, createDate, lastModifiedDate)
  VALUES ('db_version', '1.0.0.5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- menuItem data
INSERT INTO MENUITEM(menuItemId, menuItemType, name, price, DTYPE, createDate, lastModifiedDate)
  VALUES ('1','pizza','Pizza','5.25','Pizza',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM(menuItemId, menuItemType, name, price, DTYPE, createDate, lastModifiedDate)
  VALUES ('2','breadsticks','Breadsticks','1.25','Breadsticks',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- menuItemDetail data
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('1','1','crust','Thin Crust','0',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('2','1','crust','Regular Crust','0.5',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('3','1','crust','Pan Crust','1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('4','1','topping','Sausage','1.75',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('5','1','topping','Pepperoni','1.75',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('6','1','topping','Onion','0.75',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('7','1','topping','Mushroom','0.75',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('8','1','topping','Green Pepper','0.75',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO MENUITEM_DETAIL (menuItemDetailId, menuItemId, detailType, name, price, createDate, lastModifiedDate)
  VALUES('9','1','topping','Bacon','0.75',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
