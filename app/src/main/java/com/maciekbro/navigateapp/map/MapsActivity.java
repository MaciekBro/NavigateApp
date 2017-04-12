package com.maciekbro.navigateapp.map;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maciekbro.navigateapp.R;
import com.maciekbro.navigateapp.SearchApplication;
import com.maciekbro.navigateapp.dagger.SearchComponent;
import com.maciekbro.navigateapp.map.dagger.DaggerMapsActivityComponent;
import com.maciekbro.navigateapp.map.dagger.MapsActivityModule;
import com.maciekbro.navigateapp.map.mvp.MapsMVP;
import com.maciekbro.navigateapp.model.dto.PlacesDto;
import com.maciekbro.navigateapp.model.dto.SearchParamsDto;
import com.maciekbro.navigateapp.utils.AppLog;
import com.maciekbro.navigateapp.utils.PermissionAsk;
import com.maciekbro.navigateapp.views.SearchQueryView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsMVP.View {

    private GoogleMap mMap;
    private Marker groszekMarker;
    private List<Marker> markers;
    private LatLngBounds.Builder builder;
    private SearchQueryView searchQueryView;

    @Inject
    MapsMVP.Presenter presenter;    //przekazujemy mu z metody providePresenter MapsPresenter
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SearchComponent searchComponent = ((SearchApplication)getApplication()).getSearchComponent();
        DaggerMapsActivityComponent.builder()
                .searchComponent(searchComponent)
                .mapsActivityModule(new MapsActivityModule(this))
                .build().inject(this);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapFragment.setRetainInstance(true);    // do zachowywania stanu!!!!!!!

        searchQueryView = (SearchQueryView) findViewById(R.id.search_widget);

        searchQueryView.subscribeToStream()
                .debounce(1000, TimeUnit.MILLISECONDS)  //mozna kliknąć co sekunde.
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchParamsDto ->{
                    presenter.search(searchParamsDto);
                },error ->{
                    error.printStackTrace();
                });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                AppLog.log("POSITION", String.valueOf(location.getLatitude()+" , "+location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        PermissionAsk.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,12345);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, listener); //podkreśla ale jest dobrze


//        setButtonsListener();
    }


//    private void setButtonsListener() {
//        findViewById(R.id.delete_markers).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (groszekMarker != null) {
//                    groszekMarker.remove();
//                    if (markers != null) {
//                        for (Marker marker : markers) {
//                            marker.remove();
//                        }
//                    }
//                    markers = new ArrayList<Marker>();
//                }
//            }
//        });
//
//        findViewById(R.id.show_markers).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder = LatLngBounds.builder();
//                if (markers != null && markers.size() > 0) { //przy && najpierw sprawdzi lewe zapytanie pozniej prawe, przy & sprawdza na raz
//                    for (Marker marker : markers) {
//                        builder.include(marker.getPosition());  //dodajemy do buildera pozycję każdego markera!!
//                    }
//
//                    LatLngBounds latLngBounds = builder.build();
//                    int padding = 125;
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, padding);
//                    mMap.animateCamera(cameraUpdate);
//                }
//            }
//        });
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng groszekLatLng = new LatLng(52.245231, 21.093469);
        groszekMarker = mMap.addMarker(new MarkerOptions().position(groszekLatLng).title("Marker in Grochów!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(groszekLatLng, 14));
        markers = new ArrayList<>();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(latLng.toString()));
                markers.add(marker);
            }
        });

        HashMap<String,String> querys = new HashMap<>();
        querys.put("query","Centrum konferencyjne kopernik");
        presenter.search(new SearchParamsDto("textSearch",querys));



    }

    @Override
    public void showPlaces(List<PlacesDto> placesDtos) {
        if (placesDtos.size()>0){
            for (PlacesDto dto: placesDtos) {
                mMap.addMarker(new MarkerOptions()
                        .position(dto.getLatLng())
                        .title(dto.getTitle()));
            }
        }
    }
}
