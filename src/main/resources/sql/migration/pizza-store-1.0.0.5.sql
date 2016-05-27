alter table CUSTOMER add column createDate timestamp  NOT NULL default NOW();
alter table CUSTOMER add column lastModifiedDate timestamp  NOT NULL default NOW();

alter table STORE add column createDate timestamp  NOT NULL default NOW();
alter table STORE add column lastModifiedDate timestamp  NOT NULL default NOW();

alter table MENUITEM add column createDate timestamp  NOT NULL default NOW();
alter table MENUITEM add column lastModifiedDate timestamp  NOT NULL default NOW();

alter table MENUITEM_DETAIL add column createDate timestamp  NOT NULL default NOW();
alter table MENUITEM_DETAIL add column lastModifiedDate timestamp  NOT NULL default NOW();

alter table STORE_ORDER add column createDate timestamp  NOT NULL default NOW();
alter table STORE_ORDER add column lastModifiedDate timestamp  NOT NULL default NOW();

alter table ORDER_LINE add column createDate timestamp  NOT NULL default NOW();
alter table ORDER_LINE add column lastModifiedDate timestamp  NOT NULL default NOW();

alter table ORDER_LINE_DETAIL add column createDate timestamp  NOT NULL default NOW();
alter table ORDER_LINE_DETAIL add column lastModifiedDate timestamp  NOT NULL default NOW();
