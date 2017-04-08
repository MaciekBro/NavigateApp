package com.maciekbro.navigateapp.map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

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

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker groszekMarker;
    private List<Marker> markers;
    private LatLngBounds.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapFragment.setRetainInstance(true);    // do zachowywania stanu!!!!!!!

        findViewById(R.id.delete_markers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groszekMarker != null) {
                    groszekMarker.remove();
                    if (markers != null) {
                        for (Marker marker : markers) {
                            marker.remove();
                        }
                    }
                    markers = new ArrayList<Marker>();
                }
            }
        });

        findViewById(R.id.show_markers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = LatLngBounds.builder();
                if (markers != null && markers.size() > 0) { //przy && najpierw sprawdzi lewe zapytanie pozniej prawe, przy & sprawdza na raz
                    for (Marker marker : markers) {
                        builder.include(marker.getPosition());  //dodajemy do buildera pozycję każdego markera!!
                    }

                    LatLngBounds latLngBounds = builder.build();
                    int padding = 125;
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, padding);
                    mMap.animateCamera(cameraUpdate);
                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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

    }
}
