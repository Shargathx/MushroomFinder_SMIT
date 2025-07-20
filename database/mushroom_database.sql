DROP SCHEMA IF EXISTS mushroom_finder CASCADE;

CREATE SCHEMA mushroom_finder;

GRANT ALL ON SCHEMA mushroom_finder TO postgres;
GRANT ALL ON SCHEMA mushroom_finder TO PUBLIC;

CREATE TABLE mushroom_finder.mushroom_locations (
    id SERIAL PRIMARY KEY,
    location geometry(Point, 4326) NOT NULL,
    description TEXT
);

INSERT INTO mushroom_finder.mushroom_locations (location, description) VALUES
    (ST_SetSRID(ST_MakePoint(24.675, 58.985), 4326), 'Delicious porcini found near a birch grove.'),
    (ST_SetSRID(ST_MakePoint(25.345, 58.210), 4326), 'Plentiful chanterelles scattered around the old pine forest.'),
    (ST_SetSRID(ST_MakePoint(27.030, 58.610), 4326), 'Small clusters of boletus spotted near the lakeside.'),
    (ST_SetSRID(ST_MakePoint(26.150, 59.280), 4326), 'Abundant morels found beneath the alder trees.'),
    (ST_SetSRID(ST_MakePoint(25.540, 58.700), 4326), 'Fly agaric mushrooms spotted along the forest path.'),
    (ST_SetSRID(ST_MakePoint(25.650, 58.100), 4326), 'Scattered honey mushrooms near the old oak trees.'),
    (ST_SetSRID(ST_MakePoint(22.500, 58.375), 4326), 'Golden chanterelles found near Kuressaare pine woods in Saaremaa.'),
    (ST_SetSRID(ST_MakePoint(22.300, 58.500), 4326), 'Rare hedgehog mushrooms discovered in Saaremaa coastal forest.'),
    (ST_SetSRID(ST_MakePoint(22.583, 58.930), 4326), 'Brown-capped boletus seen near Kõpu lighthouse area in Hiiumaa.'),
    (ST_SetSRID(ST_MakePoint(22.700, 59.000), 4326), 'A few morels hidden beneath mossy fir trees in Hiiumaa.');