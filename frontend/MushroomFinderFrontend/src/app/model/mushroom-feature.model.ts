import {Feature} from 'geojson';

export interface MushroomFeature extends Feature {
  geometry: {
    type: 'Point';
    coordinates: [number, number];
  };
  properties: {
    description: string;
  };
}
