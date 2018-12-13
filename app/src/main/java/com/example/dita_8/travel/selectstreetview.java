package com.example.dita_8.travel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public class selectstreetview extends AppCompatActivity {
  StreetViewPanorama mStreetViewPanorama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectstreetview);
    Bundle bundle = getIntent().getParcelableExtra("bundle");
    final LatLng latLng = bundle.getParcelable("latlng");

    //streetview 프래그먼트 생성
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)getSupportFragmentManager().
                        findFragmentById(R.id.tap1selectstreetview);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
                mStreetViewPanorama = streetViewPanorama;
              mStreetViewPanorama.setPosition(latLng);
              mStreetViewPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
                  @Override
                  public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                      if (mStreetViewPanorama.getLocation() != null
                              && mStreetViewPanorama.getLocation().links != null) {
                          mStreetViewPanorama.setStreetNamesEnabled(true);
                          mStreetViewPanorama.setZoomGesturesEnabled(true);
                          mStreetViewPanorama.setPanningGesturesEnabled(true);
                          mStreetViewPanorama.setUserNavigationEnabled(true);
                      }
                  }
              });
            }
        });

    }
}
