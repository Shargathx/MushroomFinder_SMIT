import {Component, OnInit} from '@angular/core';
import * as L from 'leaflet';
import {GeoJSON} from 'leaflet';
import {HttpClient} from '@angular/common/http';
import 'leaflet/dist/leaflet.css'

@Component({
  selector: 'app-map',
  templateUrl: './map.html',
  styleUrls: ['./map.css']
})

export class MapComponent implements OnInit {
  private map!: L.Map;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.initMap();
    this.loadMushroomLocations();
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: [58.5953, 25.0136],
      zoom: 7,
      maxBounds: L.latLngBounds([56, 20], [61, 30]),
      maxBoundsViscosity: 1.0
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
    this.http.get<any[]>('http://localhost:8080/locations')
      .subscribe((geoJsonData) => {
        L.geoJSON(geoJsonData, {
          pointToLayer: (feature, latlng) => {
            return L.marker(latlng);
          },
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

    const geoJsonFeature: GeoJSON.Feature = {
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: [e.latlng.lng, e.latlng.lat]
      },
      properties: {
        description: desc
      }
    };

    this.http.post('http://localhost:8080/locations', geoJsonFeature)
      .subscribe(() => {
        L.geoJSON(geoJsonFeature, {
          onEachFeature: (feature, layer) => {
            layer.bindPopup(desc);
          }
        }).addTo(this.map);
      });
  }
}
