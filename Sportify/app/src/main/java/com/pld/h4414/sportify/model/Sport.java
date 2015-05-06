package com.h4414.bdd.sportify.model;

/**
 * Created by Meryem on 30/04/15.
 */
public class Sport {

    //private variables
    int _id;
    String _name;

    //empty constructor
    public Sport() {

    }

    // constructor
    public Sport(int id, String name){
        this._id = id;
        this._name = name;
    }

    // constructor
    public Sport(String name){
        this._name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
