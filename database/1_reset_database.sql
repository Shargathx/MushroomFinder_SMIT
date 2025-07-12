-- Kustutab X (name here) schema (mis põhimõtteliselt kustutab kõik tabelid)
DROP SCHEMA IF EXISTS mushroom_finder CASCADE;
-- Loob uue public schema vajalikud õigused
CREATE SCHEMA mushroom_finder;
-- taastab vajalikud andmebaasi õigused
GRANT ALL ON SCHEMA mushroom_finder TO postgres;
GRANT ALL ON SCHEMA mushroom_finder TO PUBLIC;