import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {MushroomFeature} from '../model/mushroom-feature.model';

@Injectable({
  providedIn: 'root'
})

export class LocationService {
  private readonly getAllLocationsUrl = 'http://localhost:8080/locations';
  private readonly postLocationUrl = 'http://localhost:8080/locations';

  constructor(private http: HttpClient) {
  }

  getLocations(): Observable<MushroomFeature[]> {
    return this.http.get<MushroomFeature[]>(this.getAllLocationsUrl);
  }

  addLocation(geoJsonFeature: MushroomFeature): Observable<MushroomFeature> {
    return this.http.post<MushroomFeature>(this.postLocationUrl, geoJsonFeature);
  }
}
