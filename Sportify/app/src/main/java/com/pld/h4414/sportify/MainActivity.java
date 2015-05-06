package com.pld.h4414.sportify;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.pld.h4414.sportify.model.InstallationSportive;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity implements SearchOptionsFragment.OnFragmentInteractionListener, OnMapReadyCallback {

    private boolean mShowingBack;
    private MapFragment mMapFragment;
    private CardResultsListFragment mCardListFragment;
    private GoogleMap map;
    private String mTypeSearch;
    private SearchView mSearchView;
    private MenuItem searchItem;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayShowTitleEnabled(true);
        setContentView(R.layout.activity_main);

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

        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
            if (appData != null) {
                String typeSearch = appData.getString("type_search");
                query = typeSearch + query;
            }
            mProgressDialog = ProgressDialog.show(this, "dialog title",
                    "dialog message", true);
            invokeWS();
        }
    }

    public void invokeWS(){
        SportifyRestClient client = new SportifyRestClient();

        client.get("/fetch/installation_sportive", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<InstallationSportive> mResult = new ArrayList<InstallationSportive>();
                ArrayList<String> mListResult = new ArrayList<String>();
                // If the response is JSONObject instead of expected JSONArray
                mProgressDialog.dismiss();

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
//                    Toast.makeText(getApplicationContext(), "" + mResult, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                searchItem.collapseActionView();

                ListView mResultsList =  (ListView) findViewById(R.id.results_listview);
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

        // Get the SearchView and set the searchable configuration
        searchItem =  menu.findItem(R.id.action_search);
//        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        mSearchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {    //open (replace) the fragment for choosing the type of search. Here : Terrain, Evenement
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchOptionsFragment();
                // Insert the fragment by replacing any existing fragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                MenuItem searchMenuItem = getSearchMenuItem();
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
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
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
        LatLng LYON = new LatLng(45.750000, 4.850000);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LYON, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        map.addMarker(new MarkerOptions()
                .position(LYON));

    }

    @Override
    public boolean onSearchRequested() {
        // We're adding the first option type_search selected and send it also to the searchableActivity
        Bundle appData = new Bundle();
        appData.putString("type_search", mTypeSearch);
        Log.v("hello reserchresults", "hello results");
        startSearch(null, false, appData, false);
        mProgressDialog.show();
        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.v("hello results","hello results");
//        if( resultCode==1 ) {
//            Toast.makeText(getApplicationContext(),"hello results" + data.getStringArrayExtra(SearchableActivity.EXTRA_SEARCH_RESULTS) , Toast.LENGTH_LONG).show();
//            String[] mSearchResults = data.getStringArrayExtra(SearchableActivity.EXTRA_SEARCH_RESULTS);
//            // Set the adapter for the list view
//            ListView mResultsList =  (ListView) findViewById(R.id.results_listview);
//            mResultsList.setAdapter( new ArrayAdapter<String>(getApplicationContext(),
//                    R.layout.results_list_item, mSearchResults));
//
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
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

}

