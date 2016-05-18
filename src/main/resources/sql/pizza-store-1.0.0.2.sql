alter table MENUITEM add column DTYPE VARCHAR(50) NULL;
update MENUITEM set DTYPE = 'Pizza' where menuItemType = 'pizza';
update MENUITEM set DTYPE = 'Breadsticks' where menuItemType = 'breadsticks';
commit;