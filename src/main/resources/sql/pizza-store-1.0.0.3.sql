alter table STORE_ORDER add column status VARCHAR(50) NOT NULL;

update APP_PROPERTIES set property_value = '1.0.0.2' where property_key = 'db_version';
