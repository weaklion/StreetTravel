package com.example.dita_8.travel;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewSource;


import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback
        ,GoogleMap.OnMapClickListener,GoogleMap.OnMarkerClickListener/*GoogleMap.OnMarkerDragListener,
        OnStreetViewPanoramaReadyCallback, StreetViewPanorama.OnStreetViewPanoramaChangeListener*/ {

    double lat = (Math.random() * 90) - 90;
    double lng = (Math.random() * 180) - 180;
    private LatLng random = new LatLng(lat,lng);
    private Marker mMarker;
    public  LatLng mLatLng;
    private GoogleMap mMap;
    private StreetViewPanorama RStreetViewPanorama;
    private RecyclerView mRecyclerView;
    private  RecycleAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    Button randombutton;
    //아이템 리스트
    private static ArrayList<Item> itemArrayList;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        randombutton =  (Button)findViewById(R.id.random);
        TabHost tabHost1 = (TabHost)findViewById(R.id.tabHost1);
        tabHost1.setup();

        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("지도 탐색");
        tabHost1.addTab(ts1);
        SupportMapFragment mapFragment =
                (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("랜덤 탐색");
        tabHost1.addTab(ts2);

         final SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)getSupportFragmentManager().
                        findFragmentById(R.id.randomstreetView);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
                RStreetViewPanorama =streetViewPanorama;
                RStreetViewPanorama.setPosition(random,3000000, StreetViewSource.OUTDOOR);
                RStreetViewPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
                    @Override
                    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                        if (RStreetViewPanorama.getLocation() != null
                                && RStreetViewPanorama.getLocation().links != null) {
                            RStreetViewPanorama.setStreetNamesEnabled(true);
                            RStreetViewPanorama.setZoomGesturesEnabled(true);
                            RStreetViewPanorama.setPanningGesturesEnabled(true);
                            RStreetViewPanorama.setUserNavigationEnabled(true);
                        }
                    }
                });
            }
        });
    randombutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            double rlat = (Math.random() * 90) -90;
            double rlng =  (Math.random() * 180) - 180;
            LatLng rlatLng= new LatLng(rlat,rlng);
            RStreetViewPanorama.setPosition(rlatLng,3000000, StreetViewSource.OUTDOOR);
            RStreetViewPanorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
                @Override
                public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                    if (RStreetViewPanorama.getLocation() != null
                            && RStreetViewPanorama.getLocation().links != null) {
                        RStreetViewPanorama.setStreetNamesEnabled(true);
                        RStreetViewPanorama.setZoomGesturesEnabled(true);
                        RStreetViewPanorama.setPanningGesturesEnabled(true);
                        RStreetViewPanorama.setUserNavigationEnabled(true);
                    }
                }
            });
        }
    });


        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("관광지 추천");
        tabHost1.addTab(ts3);

        itemArrayList = new ArrayList<>();

        itemArrayList.add(new Item("미국 자유의 여신상 ",40.6892534,-74.0466891));
        itemArrayList.add(new Item("한국 경복궁 ",37.575723, 126.976926));
        itemArrayList.add(new Item("페루 마추픽추",-13.1630604,-72.5451228));
        itemArrayList.add(new Item("볼리비아 살라르 데 우유니",-20.241024,-67.6255569));

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecycleAdapter(itemArrayList,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDecoration(25));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
      mMarker=mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
      mLatLng=latLng;
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
       Bundle bundle = new Bundle();
      Intent intent = new Intent(MapActivity.this,selectstreetview.class);
        bundle.putParcelable("latlng",mLatLng);
        intent.putExtra("bundle",bundle);
      startActivity(intent);
      return true;

    }


    public class Item {


        String location;
        double lat;
        double lng;


        //생성자
        public Item(String location,double lat,double lng){
            this.location=location;
            this.lat=lat;
            this.lng=lng;


        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}

/*
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)getSupportFragmentManager().
                        findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        SupportMapFragment mapFragment=
                (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MARKER_POSITION_KEY,mMarker.getPosition());
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        mStreetViewPanorama = streetViewPanorama;
        mStreetViewPanorama.setOnStreetViewPanoramaChangeListener(
                MapActivity.this);
            mStreetViewPanorama.setPosition(random,3000, StreetViewSource.DEFAULT);
        }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {

        if (streetViewPanoramaLocation != null) {
            mMarker.setPosition(streetViewPanoramaLocation.position);

            StreetViewPanoramaLocation location = mStreetViewPanorama.getLocation();
            if (location != null && location.links != null) {
                mStreetViewPanorama.setUserNavigationEnabled(true);
                mStreetViewPanorama.setPanningGesturesEnabled(true);
                mStreetViewPanorama.setStreetNamesEnabled(true);
                mStreetViewPanorama.setZoomGesturesEnabled(true);
            }

        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
    Toast.makeText(getApplicationContext(),"값 : " +latLng,Toast.LENGTH_SHORT).show();
    mMarker.setPosition(latLng);

        mStreetViewPanorama.setPosition(latLng);

        StreetViewPanoramaLocation location = mStreetViewPanorama.getLocation();
        if (location != null && location.links != null) {
            mStreetViewPanorama.setPosition(location.links[0].panoId);
            mStreetViewPanorama.setUserNavigationEnabled(true);
            mStreetViewPanorama.setPanningGesturesEnabled(true);
            mStreetViewPanorama.setStreetNamesEnabled(true);
            mStreetViewPanorama.setZoomGesturesEnabled(true);

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMarkerDragListener(MapActivity.this);
        googleMap.setOnMapClickListener(this);
        mMarker = googleMap.addMarker(new MarkerOptions()
                .position(random)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pegman))
                .draggable(true));

    }
}
*/