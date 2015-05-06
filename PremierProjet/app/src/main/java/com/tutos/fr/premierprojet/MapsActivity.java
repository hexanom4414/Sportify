package com.tutos.fr.premierprojet;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private MapFragment mapFragment; // Might be null if Google Play services APK is not available.
    private static final LatLng LYON = new LatLng(45.750000, 4.850000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_maps, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_open_list:
                openListView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openListView(){

    }
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mapFragment} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mapFragment == null) {
            // Try to obtain the map from the SupportMapFragment.
            createMapView();

            // Check if we were successful in obtaining the map.
//            if (mapFragment != null) {
//                setUpMap();
//            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(LYON.latitude,LYON.longitude), 14));

        addMarker(LYON);

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow²
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);

                TextView tvPeople = (TextView) v.findViewById(R.id.nb_people_present);
                TextView tvField = (TextView) v.findViewById(R.id.field_name);


                tvPeople.setText("40");
                tvField.setText("par des princes");

                return v;
            }
        });
    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mapFragment} is not null.
     */
    private void setUpMap() {
        addMarker(LYON);
        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(LYON)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();
        mapFragment.getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // Setting a custom info window adapter for the google map
        mapFragment.getMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);

                TextView tvPeople = (TextView) v.findViewById(R.id.nb_people_present);
                TextView tvField = (TextView) v.findViewById(R.id.field_name);


                tvPeople.setText("40");
                tvField.setText("par des princes");

                return v;
            }
        });
    }

    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == mapFragment){
                mapFragment = (MapFragment) getFragmentManager().findFragmentById(
                        R.id.map);
                mapFragment.getMapAsync(this);
                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == mapFragment) {
                    Toast.makeText(getApplicationContext(),
                            "Erreur création de la map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * Adds a marker to the map
     */
    private void addMarker(LatLng pos){

        /** Make sure that the map has been initialised **/
        if(null != mapFragment){
            mapFragment.getMap().addMarker(new MarkerOptions()
                            .position(pos)
            );
        }
    }
}
