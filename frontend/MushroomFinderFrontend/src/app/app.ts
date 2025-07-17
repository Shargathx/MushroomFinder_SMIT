import {Component, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {MapComponent} from './component/map/map';
import {HttpClientModule} from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MapComponent, HttpClientModule],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent {
  protected readonly title = signal('MushroomFinderFrontend');
}
