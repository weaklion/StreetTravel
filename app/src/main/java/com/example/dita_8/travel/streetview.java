package com.example.dita_8.travel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public class streetview extends AppCompatActivity {
    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetview);
        getIncomingIntent();
    }


    private void getIncomingIntent() {
        if (getIntent().hasExtra("lat") && getIntent().hasExtra("lng")) {
            Log.d("streetview", "getInComingIntent: found intent extras.");
            String lat = getIntent().getStringExtra("lat");
            String lng = getIntent().getStringExtra("lng");

            setLat(lat,lng);
        }
    }

    private void setLat(String lat, String lng) {
    double mlat = Double.parseDouble(lat);
    double mlng = Double.parseDouble(lng);
    final LatLng latLng = new LatLng(mlat,mlng);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.selectstreet);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
                final StreetViewPanorama mstreetViewPanorama = streetViewPanorama;

                mstreetViewPanorama.setPosition(latLng);
                mstreetViewPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
                    @Override
                    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                        if (mstreetViewPanorama.getLocation() != null
                                && mstreetViewPanorama.getLocation().links != null) {
                            mstreetViewPanorama.setStreetNamesEnabled(true);
                            mstreetViewPanorama.setZoomGesturesEnabled(true);
                            mstreetViewPanorama.setPanningGesturesEnabled(true);
                            mstreetViewPanorama.setUserNavigationEnabled(true);
                        }
                    }
                });
            }
        });
    }
}