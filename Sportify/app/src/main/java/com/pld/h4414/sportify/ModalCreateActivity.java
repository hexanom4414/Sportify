package com.pld.h4414.sportify;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class ModalCreateActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener {

    private static SportifyEvent event ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal_create);

        event = new SportifyEvent();

        Spinner spinner_sport = (Spinner) findViewById(R.id.sportPicker);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_sport = ArrayAdapter.createFromResource(this,
                R.array.sport_array, android.R.layout.simple_spinner_item);


        //to replace by :
        //
        SportifyRestClient client = new SportifyRestClient();

        //ArrayAdapter<String> adapter_sport = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,client.listSports());
        //adapter_sport.setDropDownViewResource(android.R.layout.simple_spinner_item);


    // Apply the adapter to the spinner
        spinner_sport.setAdapter(adapter_sport);
        System.out.println(client.listSports());



        Spinner spinner_location = (Spinner) findViewById(R.id.locationPicker);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_location = ArrayAdapter.createFromResource(this,
                R.array.location_array, android.R.layout.simple_spinner_item);


        //to replace by :

      //  adapter_sport.addAll(client.listLocations()); // Add all the list of sport fields from the server to the adapter


    // Apply the adapter to the spinner
        spinner_location.setAdapter(adapter_location);
    }



    //listener for spinner

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (parent.getId() == R.id.sportPicker){

            Toast.makeText(getApplicationContext(),"coucou",Toast.LENGTH_LONG).show();


            event.setSport((String)parent.getItemAtPosition(position));

       }else if(parent.getId() == R.id.locationPicker){


            Toast.makeText(getApplicationContext(),"je suis là",Toast.LENGTH_LONG).show();

            event.setLocation((String)parent.getItemAtPosition(position));

       }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    // onClickListener for the buttons


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }





    // the dialog window that appears when clicking on the date and time buttons


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        event.setTime(hourOfDay, minute);

        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            event.setDate(new Date(year,month,day));


        }
    }



    // Listener for the validate button

    public void createEvent(View v){

        // Here we call the createEvent Method

        //SportifyRestClient client = new SportifyRestClient();


        Toast.makeText(getApplicationContext(), event+"",Toast.LENGTH_LONG).show();

    }

    public class SportifyEvent{

        private String sport;
        private Date date;
        private Date time;
        private String location;
        private String userEmail;


        SportifyEvent(){

        }


        SportifyEvent(String s, Date d, String l,String u ){
            sport = s;
            date = d;
            location = l;
            userEmail = u;

        }

        @Override
        public String toString() {
            return "Username: "+ userEmail+" Sport : " +sport+ " le : "+date.toString()+" à "+ time.getHours()+":"+time.getMinutes()+" à "+location;
        }

        public String getSport() {
            return sport;
        }

        public void setSport(String sport) {
            this.sport = sport;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(int hours, int minutes) {
            this.time = new Date(0,0,0,hours,minutes);
        }
    }




}
