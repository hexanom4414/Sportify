package com.h4414.bdd.sportify.model;

/**
 * Created by Meryem on 30/04/15.
 */
public class User {

    int _id;
    String _nom;
    String _prenom;

    public User() {
    }

    public User(int id, String nom, String prenom) {
        this._id = id;
        this._nom = nom;
        this._prenom = prenom;
    }

    public User(String nom, String prenom) {
        this._nom = nom;
        this._prenom = prenom;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_nom() {
        return _nom;
    }

    public void set_nom(String _nom) {
        this._nom = _nom;
    }

    public String get_prenom() {
        return _prenom;
    }

    public void set_prenom(String _prenom) {
        this._prenom = _prenom;
    }
}
