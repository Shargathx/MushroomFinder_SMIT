import {Component, OnInit} from '@angular/core';
import * as L from 'leaflet';
import 'leaflet/dist/leaflet.css'
import {LocationService} from '../../service/location-service';
import {MushroomFeature} from "../../model/mushroom-feature.model";
import {FormsModule} from '@angular/forms';

const greyMarkerIcon = new L.Icon({
  iconUrl: 'assets/images/marker-icon-grey.png',
  shadowUrl: 'assets/images/marker-shadow.png',
});

const defaultMarkerIcon = new L.Icon({
  iconUrl: 'assets/images/marker-icon.png',
  shadowUrl: 'assets/images/marker-shadow.png',
});

@Component({
  selector: 'app-map',
  templateUrl: './map.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./map.css'],
})


export class MapComponent implements OnInit {
  private map!: L.Map;
  latitude: number | null = null;
  longitude: number | null = null;
  description: string = '';
  isLocked = true;
  private currentMarker: L.Marker | null = null;

  constructor(private locationService: LocationService) {
  }

  ngOnInit(): void {
    this.initMap();
    this.loadMushroomLocations();
  }

  submitLocation(): void {
    if (!this.handleEmptyInputFields()) {
      return;
    }
    this.handleLocationAdded();
  }

  toggleLock(): void {
    this.isLocked = !this.isLocked;
  }

  private initMap(): void {
    this.setMapInitialValues();
    this.addStreetMapTileLayer();
    this.setAndListenForEvent();
  }

  private loadMushroomLocations(): void {
    this.fetchAndDisplayAllMushroomLocations().subscribe((locations: MushroomFeature[]) => {
      this.convertAndAddMarkersToMap(locations);
    });
  }

  private handleEmptyInputFields(): boolean {
    if (this.coordinatesAreEmpty()) {
      this.displayCoordinateErrorMessage();
      return false;
    } else if (this.descriptionFieldIsEmpty()) {
      this.displayDescriptionErrorMessage();
      return false;
    }
    return true;
  }

  private coordinatesAreEmpty() {
    return this.latitude === null || this.longitude === null;
  }

  private displayCoordinateErrorMessage() {
    alert('Please click on the map and enter valid coordinates.');
    return;
  }

  private descriptionFieldIsEmpty() {
    return !this.description.trim();
  }

  private displayDescriptionErrorMessage() {
    alert('Please enter a valid description.');
    return;
  }

  private handleLocationAdded() {
    const geoJsonFeature = this.createMushroomFeature();
    this.locationService.addLocation(geoJsonFeature).subscribe(() => {
      if (this.currentMarker) {
        this.currentMarker.setIcon(defaultMarkerIcon);
        this.currentMarker.bindPopup(this.description).openPopup();
        this.currentMarker = null;
      } else {
        this.addSingleMapMarker(geoJsonFeature);
      }
      this.resetInputFields();
    });
  }

  private createMushroomFeature() {
    const geoJsonFeature: MushroomFeature = {
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: [this.longitude!, this.latitude!]
      },
      properties: {
        description: this.description
      }
    };
    return geoJsonFeature;
  }

  private addSingleMapMarker(geoJsonFeature: MushroomFeature) {
    L.geoJSON(geoJsonFeature, {
      onEachFeature: (feature, layer) => {
        layer.bindPopup(this.description).openPopup();
      }
    }).addTo(this.map);
  }

  private resetInputFields() {
    this.latitude = null;
    this.longitude = null;
    this.description = '';
    if (this.currentMarker) {
      this.map.removeLayer(this.currentMarker);
      this.currentMarker = null;
    }
  }

  private setMapInitialValues() {
    this.map = L.map('map', {
      center: [58.5953, 25.0136],
      zoom: 7,
      minZoom: 7,
      maxBounds: L.latLngBounds([56, 20], [61, 30]),
      maxBoundsViscosity: 0.7
    });
  }

  private addStreetMapTileLayer() {
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);
  }

  private setAndListenForEvent() {
    this.map.on('click', this.onMapClick.bind(this));
    setTimeout(() => {
      this.map.invalidateSize();
    }, 100);
  }

  private fetchAndDisplayAllMushroomLocations() {
    return this.locationService.getLocations();
  }

  private convertAndAddMarkersToMap(locations: MushroomFeature[]) {
    L.geoJSON(locations, {
      pointToLayer: (feature, latlng) => L.marker(latlng),
      onEachFeature: (feature, layer) => {
        const desc = feature.properties?.description || 'No description';
        layer.bindPopup(desc);
      }
    }).addTo(this.map);
  }

  private onMapClick(e: L.LeafletMouseEvent): void {
    this.latitude = e.latlng.lat;
    this.longitude = e.latlng.lng;
    const desc: string | null = prompt("Sisesta kirjeldus seenekoha kohta:");
    if (desc === null || desc.trim() === '') {
      this.resetInputFields();
      return;
    }
    this.description = desc.trim();
    if (this.currentMarker) {
      this.currentMarker.setLatLng(e.latlng);
    } else {
      this.currentMarker = L.marker(e.latlng, {icon: greyMarkerIcon}).addTo(this.map);
    }
  }


}
