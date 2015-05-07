package com.pld.h4414.sportify;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;

import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.loopj.android.http.RequestParams;
import com.pld.h4414.sportify.model.InstallationSportive;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pld.h4414.sportify.model.InstallationSportive;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements SearchOptionsFragment.OnFragmentInteractionListener, OnMapReadyCallback {


    public final static LatLngBounds LYON = new LatLngBounds(
            new LatLng(45.706343, 4.808287), new LatLng(45.793546, 4.908366));
    private boolean mShowingBack;
    private MapFragment mMapFragment;
    private CardResultsListFragment mCardListFragment;
    private GoogleMap mMap;
    private String mTypeSearch;
    private android.support.v7.widget.SearchView mSearchView;
    private MenuItem searchItem;
    private ProgressDialog mProgressDialog;
    ArrayList<String> mListResult = new ArrayList<String>();
    static final int CODE_SPORT = 1;  // The request code
    public int sport_global = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setContentView(R.layout.activity_main);





        Button buttonCreate = (Button)findViewById(R.id.buttonCreate);
        Button buttonJoin = (Button)findViewById(R.id.buttonJoin);



 


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                           /* AnimatorSet buttonSet = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fab_animation);
                            buttonSet.setTarget(view);
                            buttonSet.start();*/


                //transition to another activity here
                Intent oldIntent = getIntent();

                String email = oldIntent.getStringExtra("email");
                Intent intent = new Intent(getApplicationContext(), ModalCreateActivity.class);


                intent.putExtra("email", email);
                startActivity(intent);

            }
        });



        buttonCreate.setVisibility(View.GONE);
        buttonJoin.setVisibility(View.GONE);
        if (savedInstanceState == null) {   // first opening of the app create the two fragment -> convert:
            mShowingBack = false;
            mCardListFragment = new CardResultsListFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mCardListFragment)
                    .commit();
            mMapFragment = MapFragment.newInstance();
            mMapFragment.getMapAsync(this);
        }





        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_add))
                .withButtonColor(Color.WHITE)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();



        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Button button1 = (Button)findViewById(R.id.buttonCreate);
                Button button2 = (Button)findViewById(R.id.buttonJoin);

                if (button1.getVisibility() == View.GONE){

                    button1.setVisibility(View.VISIBLE);

                }else if (button2.getVisibility() == View.VISIBLE){

                    button1.setVisibility(View.GONE);

                }
                if(button2.getVisibility()==View.GONE){
                    button2.setVisibility(View.VISIBLE);

                }else if(button2.getVisibility() == View.VISIBLE ){
                    button2.setVisibility(View.GONE);
                }



            }
        });




    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            String paramSport ="";
            Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
            if (appData != null) {
                paramSport = appData.getString("sport");
            }
            mProgressDialog = ProgressDialog.show(this, null,
                    "recherche de terrains..", true);
            invokeWS(paramSport,query);

        }
    }

    public void invokeWS(String paramSport, String query){
        SportifyRestClient client = new SportifyRestClient();



                String suffixe = "/fetch/installation_sportive?filter=sport";

        suffixe = suffixe + "&arg=" + sport_global ;
        client.get(suffixe, new RequestParams(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                ArrayList<InstallationSportive> mResult = new ArrayList<InstallationSportive>();
                // If the response is JSONObject instead of expected JSONArray
                mProgressDialog.dismiss();
                ArrayList<String> mListResult = new ArrayList<String>();

                try {
                    if (response.getBoolean("success")) {
                        JSONArray data = response.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject row = data.getJSONObject(i);
                            InstallationSportive aInstallationSportive = new InstallationSportive(row.getInt("id"),row.getString("nom"),row.getString("adresse"),row.getDouble("latitude"),row.getDouble("longitude"));

                            mResult.add(aInstallationSportive);
                            mListResult.add(row.getString("nom"));
                        }
                    }
                    else {
                        //gestion erreur
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                searchItem.collapseActionView();

                ListView mResultsList =  (ListView) findViewById(R.id.results_listview);
                System.out.println(mListResult.size());
                mResultsList.setAdapter( new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.results_list_item, mListResult));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final FragmentManager fragmentManager = getFragmentManager();
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.filter).setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), ModalFilterActivity.class);

                startActivityForResult(intent, CODE_SPORT);
                return false;
            }
        });
        // Get the SearchView and set the searchable configuration
        searchItem =  menu.findItem(R.id.action_search);
        mSearchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
//        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {    //open (replace) the fragment for choosing the type of search. Here : Terrain, Evenement
            @Override
            public void onClick(View v) {
               /* Fragment fragment = new SearchOptionsFragment();
                // Insert the fragment by replacing any existing fragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();*/





            }
        });
        mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchItem != null) {
                    searchItem.collapseActionView();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // ...
                return true;
            }
        });

//        mSearchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    Toast.makeText(getApplicationContext(),"hello results"  , Toast.LENGTH_LONG).show();
//                     InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//                }
//            }
//        });
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
        mSearchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mCardListFragment)
                        .commit();
                return false;
            }
        });
//
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_toggle_card) {    // for switching between the two fragment : CardResultListView and CardGmaps
            if (mShowingBack) {
                item.setTitle(R.string.action_switch_carte);
                flipCard();
                mShowingBack = false;

            } else {
                item.setTitle(R.string.action_switch_list);
                flipCard();
                mShowingBack = true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String typeSearch) {
        mTypeSearch = typeSearch;
        mSearchView.setQueryHint(mTypeSearch+" : ");
        FragmentManager aFragmentManager = getFragmentManager();
        aFragmentManager.beginTransaction()
                .replace(R.id.container, mCardListFragment)
                .commit();


    }

    private void flipCard() {
        // Flip to the front.
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.
        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.
        getFragmentManager()
                .beginTransaction()
                        // Replace the default fragment animations with animator resources representing
                        // rotations when switching to the back of the card, as well as animator
                        // resources representing rotations when flipping back to the front (e.g. when
                        // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                        // Replace any fragments currently in the container view with a fragment
                        // representing the next page (indicated by the just-incremented currentPage
                        // variable).
                .replace(R.id.container, mMapFragment)
                        // Add this transaction to the back stack, allowing users to press Back
                        // to get to the front of the card.
                .addToBackStack(null)
                        // Commit the transaction.
                .commit();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        System.out.println("onMapReady !!");
        mMap = map;

        mMap.moveCamera(CameraUpdateFactory.newLatLng(LYON.getCenter()));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LYON, 0));



        ArrayList<InstallationSportive> list_installations = new ArrayList<>();
        for (InstallationSportive ins : list_installations){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(ins.get_latitude(), ins.get_longitude()))
                    .title(ins.get_nom())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .snippet(""));

        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onSearchRequested() {
        // We're adding the first option type_search selected and send it also to the searchableActivity
        Bundle appData = new Bundle();
        appData.putInt("sport", sport_global);
        startSearch(null, false, appData, false);
//        mProgressDialog.show();
        return true;
    }


    /**
     * A fragment representing the front of the card.
     */
    public static class CardResultsListFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_card_results_list, container, false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSearchView.setFocusable(true);
        // Check which request we're responding to
        if (requestCode == CODE_SPORT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                 sport_global = getIntent().getIntExtra("sport",1);

              mSearchView.setIconifiedByDefault(false);
                mSearchView.requestFocus();

            }
        }
    }

}

