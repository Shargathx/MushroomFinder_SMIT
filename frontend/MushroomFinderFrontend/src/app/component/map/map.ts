import {Component, OnInit} from '@angular/core';
import * as L from 'leaflet';
import 'leaflet/dist/leaflet.css'
import {LocationService} from '../../service/location-service';
import {MushroomFeature} from "../../model/mushroom-feature.model";

@Component({
  selector: 'app-map',
  templateUrl: './map.html',
  styleUrls: ['./map.css']
})

export class MapComponent implements OnInit {
  private map!: L.Map;

  constructor(private locationService: LocationService) {
  }

  ngOnInit(): void {
    this.initMap();
    this.loadMushroomLocations();
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: [58.5953, 25.0136],
      zoom: 7,
      minZoom: 7,
      maxBounds: L.latLngBounds([56, 20], [61, 30]),
      maxBoundsViscosity: 0.7
    });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);

    this.map.on('click', this.onMapClick.bind(this));
    setTimeout(() => {
      this.map.invalidateSize();
    }, 100);
  }

  private loadMushroomLocations(): void {
    this.locationService.getLocations().subscribe((locations: MushroomFeature[]) => {
      L.geoJSON(locations, {
        pointToLayer: (feature, latlng) => L.marker(latlng),
        onEachFeature: (feature, layer) => {
          const desc = feature.properties?.description || 'No description';
          layer.bindPopup(desc);
        }
      }).addTo(this.map);
    });
  }


  private onMapClick(e: L.LeafletMouseEvent): void {
    const desc = prompt("Sisesta kirjeldus seenekoha kohta:");
    if (!desc) return;

    const geoJsonFeature: MushroomFeature = {
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: [e.latlng.lng, e.latlng.lat]
      },
      properties: {
        description: desc
      }
    };

    this.locationService.addLocation(geoJsonFeature).subscribe(() => {
      L.geoJSON(geoJsonFeature, {
        onEachFeature: (feature, layer) => {
          layer.bindPopup(desc);
        }
      }).addTo(this.map);
    });
  }
}
