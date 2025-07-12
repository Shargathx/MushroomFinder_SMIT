CREATE TABLE mushroom_finder.mushroom_locations (
                                                    id SERIAL PRIMARY KEY,
                                                    location geometry(Point, 4326) NOT NULL,
                                                    description TEXT
);