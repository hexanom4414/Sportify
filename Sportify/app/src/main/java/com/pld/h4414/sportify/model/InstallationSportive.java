package com.h4414.bdd.sportify.model;

/**
 * Created by Meryem on 30/04/15.
 */
public class InstallationSportive {

    int _id;
    int _nbTerrains;
    String _adresse;
    int _sports;

// empty constructor
    public InstallationSportive() {
    }

    // constructor
    public InstallationSportive(int id, int nbTerrains, String adresse, int sports) {
        this._id = id;
        this._nbTerrains = nbTerrains;
        this._adresse = adresse;
        this._sports = sports;
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
}
