/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pld.h4414.sportify;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.SupportMapFragment;

import java.io.InputStream;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setIcon(mAppSectionsPagerAdapter.getPageIcon(i))
                            .setTabListener(this));
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



                Button button1 = (Button)findViewById(R.id.button);

                if (button1.getVisibility() == View.GONE){

                    button1.setVisibility(View.VISIBLE);

                }else if(button1.getVisibility() == View.VISIBLE){
                    button1.setVisibility(View.GONE);
                }



            }
        });



    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {



                case 0:

//                    return new GmapsFragment();
                    GmapsFragment mapFragment = new GmapsFragment();
                    return mapFragment.newInstance();

                case 1:

                    return new SportifyActionFragment();

                case 2:
                    return new FriendsFragment();

                case 3:

                    return new UserFragment();

                default:

                    return new UserFragment();


            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }

        public int getPageIcon (int position) {
            switch (position) {

                case 0:
                    return R.drawable.ic_sportify;
                case 1:
                    return R.drawable.ic_add;

                case 2:

                    return R.drawable.ic_friends;

                case 3:

                    return R.drawable.ic_user;

                default:

                    return R.drawable.ic_sportify;

            }

        }

    }


    /**
     * You can create here a gmaps fragment following the dummy fragment model
     */
    public static class GmapsFragment extends SupportMapFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_gmaps, container, false);


            System.out.println("on arrive ICI");

            return rootView;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // TODO Add your menu entries here
            super.onCreateOptionsMenu(menu, inflater);

            inflater.inflate(R.menu.menu_fragment_maps, menu);

            // Get the SearchView and set the searchable configuration
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class UserFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user, container, false);

            // Here retrieve the photo and the name of the user if he's connected
            Intent intent = getActivity().getIntent();

            if(intent.getBooleanExtra("validPerson", false))
            {
                String first_name = intent.getStringExtra("givenName");
                String family_name = intent.getStringExtra("familyName");
                String imgUrl = intent.getStringExtra("urlImage");

                // show his sports in the gridview
                new DownloadImageTask((ImageView) rootView.findViewById(R.id.imageView)).execute(imgUrl);
                TextView text_name = (TextView) rootView.findViewById(R.id.name);
                text_name.setText(first_name + " " + family_name);
            }
            else
            {
                ((TextView) rootView.findViewById(R.id.name)).setText("no user");
            }

            return rootView;
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }
    }
    /**
     * A  fragment showing the principal action buttons of the app
     */
    public static class SportifyActionFragment extends Fragment {


        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_sportify_action, container, false);
            View createButton = rootView.findViewById(R.id.createEventButton);

           /* //create animation for adding the buttons
            LayoutTransition showButtons = new LayoutTransition();
            ObjectAnimator anim = ObjectAnimator.ofFloat(createButton, "rotation", 0, 90);
            anim.setDuration(6000);
            showButtons.setAnimator(LayoutTransition.CHANGE_APPEARING, anim);
            container.setLayoutTransition(showButtons);*/


            //click listener of the buttons

                    createButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           /* AnimatorSet buttonSet = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fab_animation);
                            buttonSet.setTarget(view);
                            buttonSet.start();*/


                            //transition to another activity here
                            Intent oldIntent = getActivity().getIntent();

                            String email = oldIntent.getStringExtra("email");
                            Intent intent = new Intent(getActivity(),ModalCreateActivity.class);

                            intent.putExtra("email", email);

                            startActivity(intent);
                        }
                    });

            rootView.findViewById(R.id.joinEventButton)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //transition to another activity here
                            Intent intent = new Intent(getActivity(), ModalFilterActivity.class);
                            startActivity(intent);
                        }
                    });


            return rootView;
        }
    }

        /**
         * A  fragment showing the friends section
         */
        public static class FriendsFragment extends Fragment {


            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

                //click listener of the buttons
            /*rootView.findViewById(R.id.demo_external_activity)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });*/
                return rootView;
            }


    }




}
