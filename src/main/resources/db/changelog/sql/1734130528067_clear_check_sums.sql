--liquibase formatted sql
--changeset avivannikov:0
UPDATE databasechangelog SET md5sum = NULL WHERE filename LIKE '%changelog%';