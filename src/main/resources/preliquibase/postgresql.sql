-- PreLiquibase
--    The following SQL gets executed prior to invoking Liquibase.
--    It only gets executed if the database is PostgreSQL.
--
CREATE SCHEMA IF NOT EXISTS xmlparser AUTHORIZATION postgres;

COMMENT ON SCHEMA xmlparser IS 'Schema for test project - XML parser';

GRANT USAGE ON SCHEMA xmlparser TO PUBLIC;

GRANT ALL ON SCHEMA xmlparser TO postgres;

set search_path = 'xmlparser',"$user",public;