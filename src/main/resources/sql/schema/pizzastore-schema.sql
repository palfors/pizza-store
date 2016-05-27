-- drop in reverse order
DROP TABLE IF EXISTS ORDER_LINE_DETAIL CASCADE;
DROP TABLE IF EXISTS ORDER_LINE CASCADE;
DROP TABLE IF EXISTS STORE_ORDER CASCADE;
DROP TABLE IF EXISTS MENUITEM_DETAIL CASCADE;
DROP TABLE IF EXISTS MENUITEM CASCADE;
DROP TABLE IF EXISTS CUSTOMER CASCADE;
DROP TABLE IF EXISTS STORE CASCADE;
DROP TABLE IF EXISTS APP_PROPERTIES CASCADE;

CREATE TABLE `APP_PROPERTIES` (
  `property_key` varchar(200) NOT NULL,
  `property_value` varchar(200) NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `STORE` (
  `storeId` bigint(20) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`storeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `CUSTOMER` (
  `customerId` bigint(20) NOT NULL,
  `name` varchar(200) NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `MENUITEM` (
  `menuItemId` bigint(20) NOT NULL,
  `menuItemType` varchar(50) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `DTYPE` varchar(50) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`menuItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `MENUITEM_DETAIL` (
  `menuItemDetailId` bigint(20) NOT NULL,
  `menuItemId` bigint(20) NOT NULL,
  `detailType` varchar(50) NOT NULL,
  `name` varchar(200) NOT NULL,
  `price` double NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`menuItemDetailId`),
  KEY `midetail_menuitem_fk` (`menuItemId`),
  CONSTRAINT `midetail_menuitem_fk` FOREIGN KEY (`menuItemId`) REFERENCES `menuitem` (`menuItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `STORE_ORDER` (
  `orderId` bigint(20) NOT NULL,
  `storeId` bigint(20) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `status` varchar(50) NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`orderId`),
  KEY `order_store_fk` (`storeId`),
  KEY `order_customer_fk` (`customerId`),
  CONSTRAINT `order_customer_fk` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  CONSTRAINT `order_store_fk` FOREIGN KEY (`storeId`) REFERENCES `store` (`storeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ORDER_LINE` (
  `orderLineId` bigint(20) NOT NULL,
  `orderId` bigint(20) NOT NULL,
  `menuItemId` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`orderLineId`),
  KEY `orderline_order_fk` (`orderId`),
  KEY `orderline_menuitem_fk` (`menuItemId`),
  CONSTRAINT `orderline_menuitem_fk` FOREIGN KEY (`menuItemId`) REFERENCES `menuitem` (`menuItemId`),
  CONSTRAINT `orderline_order_fk` FOREIGN KEY (`orderId`) REFERENCES `store_order` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ORDER_LINE_DETAIL` (
  `orderLineDetailId` bigint(20) NOT NULL,
  `orderLineId` bigint(20) NOT NULL,
  `menuItemDetailId` bigint(20) NOT NULL,
  `placement` varchar(20) NOT NULL,
  `price` double NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`orderLineDetailId`),
  KEY `oldetails_orderline_fk` (`orderLineId`),
  KEY `oldetails_menuitemdetail_fk` (`menuItemDetailId`),
  CONSTRAINT `oldetails_menuitemdetail_fk` FOREIGN KEY (`menuItemDetailId`) REFERENCES `menuitem_detail` (`menuItemDetailId`),
  CONSTRAINT `oldetails_orderline_fk` FOREIGN KEY (`orderLineId`) REFERENCES `order_line` (`orderLineId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
