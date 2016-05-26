alter table CUSTOMER add column createDate timestamp  NOT NULL default NOW();
alter table CUSTOMER add column lastModifiedDate timestamp  NOT NULL default NOW();

