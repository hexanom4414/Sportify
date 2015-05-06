package  com.pld.h4414.sportify.model;
/**
 * Created by Meryem on 30/04/15.
 */


import java.util.Date;

public class Event {

    int _id;
    String _installation;
    String _sport;
    Date _date;
    String _email_user;

    // empty constructor
    public Event(){

    }

    // constructor
    public Event(int id, String installation, String sport, Date date){
        this._id = id;
        this._installation = installation;
        this._sport = sport;
        this._date = date;


    }

    // constructor
    public Event(String installation, String sport, Date date){
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

    public String get_installation() {
        return _installation;
    }

    public void set_installation(String _installation) {
        this._installation = _installation;
    }

    public Date get_date() {
        return _date;
    }

    public void set_time(int hours, int minutes) {
        this._date = new Date(_date.getYear(),_date.getMonth(), _date.getDay(), hours,minutes);
    }


    public void set_date(Date _date) {
        this._date = _date;
    }

    public String get_sport() {
        return _sport;
    }

    public void set_sport(String _sport) {
        this._sport = _sport;
    }

    public String get_email_user() {
        return _sport;
    }

    public void set_email_user(String _email_user) {
        this._email_user = _email_user;
    }
}
