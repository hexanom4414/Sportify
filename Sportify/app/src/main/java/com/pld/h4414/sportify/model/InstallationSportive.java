package com.pld.h4414.sportify.model;

/**
 * Created by Meryem on 30/04/15.
 */
public class InstallationSportive {

    int _id;
    int _nbTerrains;
    String _adresse;

    public String get_nom() {
        return _nom;
    }

    public void set_nom(String _nom) {
        this._nom = _nom;
    }

    String _nom;
    int _sports;



    Double _latitude;
    Double _longitude;

// empty constructor
    public InstallationSportive() {
    }


    // constructor
    public InstallationSportive(int id, int nbTerrains, String adresse, int sports,Double latitude,Double longitude) {
        this._id = id;
        this._nbTerrains = nbTerrains;
        this._adresse = adresse;
        this._sports = sports;
        this._latitude = latitude;
        this._longitude = longitude;
    }

    // constructor
    public InstallationSportive(int id,String nom, String adresse,Double latitude,Double longitude) {
        this._id = id;
        this._nom = nom;
        this._adresse = adresse;
        this._latitude = latitude;
        this._longitude = longitude;
    }

    // constructor
    public InstallationSportive(int nbTerrains, String adresse, int sports) {
        this._nbTerrains = nbTerrains;
        this._adresse = adresse;
        this._sports = sports;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_nbTerrains() {
        return _nbTerrains;
    }

    public void set_nbTerrains(int _nbTerrains) {
        this._nbTerrains = _nbTerrains;
    }

    public String get_adresse() {
        return _adresse;
    }

    public void set_adresse(String _adresse) {
        this._adresse = _adresse;
    }

    public int get_sports() {
        return _sports;
    }

    public void set_sports(int _sports) {
        this._sports = _sports;
    }

    public Double get_latitude() {
        return _latitude;
    }

    public void set_latitude(Double _latitude) {
        this._latitude = _latitude;
    }

    public Double get_longitude() {
        return _longitude;
    }

    public void set_longitude(Double _longitude) {
        this._longitude = _longitude;
    }
}
