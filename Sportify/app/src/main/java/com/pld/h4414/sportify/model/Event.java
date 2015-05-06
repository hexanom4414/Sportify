package com.h4414.bdd.sportify.model;
/**
 * Created by Meryem on 30/04/15.
 */


import java.util.Date;

public class Event {

    int _id;
    int _installation;
    int _sport;
    String _date;

    // empty constructor
    public Event(){

    }

    // constructor
    public Event(int id, int installation, int sport, String date){
        this._id = id;
        this._installation = installation;
        this._sport = sport;
        this._date = date;


    }

    // constructor
    public Event(int installation, int sport, String date){
        this._installation = installation;
        this._sport = sport;
        this._date = date;


    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_installation() {
        return _installation;
    }

    public void set_installation(int _installation) {
        this._installation = _installation;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public int get_sport() {
        return _sport;
    }

    public void set_sport(int _sport) {
        this._sport = _sport;
    }
}
