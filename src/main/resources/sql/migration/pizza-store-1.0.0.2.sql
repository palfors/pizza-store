alter table MENUITEM add column DTYPE VARCHAR(50) NULL;
update MENUITEM set DTYPE = 'Pizza' where menuItemType = 'pizza';
update MENUITEM set DTYPE = 'Breadsticks' where menuItemType = 'breadsticks';
commit;

update APP_PROPERTIES set property_value = '1.0.0.2' where property_key = 'db_version';
