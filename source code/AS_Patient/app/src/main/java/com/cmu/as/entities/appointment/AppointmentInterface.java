package com.cmu.as.entities.appointment;

import java.util.ArrayList;

/**
 * Created by Tina on 14-4-15.
 */
public class AppointmentInterface {

    private ArrayList<Appointment> data ;
    private ArrayList<Appointment> past ;
    private ArrayList<Appointment> future ;

    //constructor
    public AppointmentInterface() {
        data = new ArrayList<Appointment>();
        past = new ArrayList<Appointment>();
        future = new ArrayList<Appointment>();
    }
    //set
    public void setAppList(ArrayList<Appointment> app){ this.data = app;} ;
    public ArrayList<Appointment> getAppList(){ return this.data;}
    //get appointment by the index
    public Appointment getApp(int ind) {
        return this.data.get(ind);
    }
    //get appointment ID by index
    public String getAppID(int ind) {
        return this.data.get(ind).getAppID();
    }
    //get doctor ID by index
    public String getDocID(int ind) {
        return this.data.get(ind).getDocID();
    }
    //get patient ID by index
    public String getPatID(int ind) {
        return this.data.get(ind).getPatID();
    }
    //get appointment date by index
    public String getAppDate(int ind) {
        return this.data.get(ind).getAppDate();
    }
    //get appointment time by index
    public String getAppTime(int ind) {
        return this.data.get(ind).getAppTime();
    }
    //get date and time availability by index
    public String getAppAvailability(int ind) {
        return this.data.get(ind).getAppAvailability();
    }
    public void setAppID(int ind, String newAppInd) {
        this.data.get(ind).setAppID(newAppInd);
    }
    public void setDocID(int ind, String newDocInd) {
        this.data.get(ind).setDocID(newDocInd);
    }
    public void setPatID(int ind, String newPatInd) {
        this.data.get(ind).setPatID(newPatInd);
    }
    public void setAppDate(int ind, String newAppDate) {
        this.data.get(ind).setAppDate(newAppDate);
    }
    public void setAppTime(int ind, String newAppTime) {
        this.data.get(ind).setAppTime(newAppTime);
    }
    public void setAppAvailability(int ind, String newAvailability) {
        this.data.get(ind).setAppAvailability(newAvailability);
    }


    public ArrayList<Appointment> getPast() {
        return past;
    }

    public void setPast(ArrayList<Appointment> past) {
        this.past = past;
    }

    public ArrayList<Appointment> getFuture() {
        return future;
    }

    public void setFuture(ArrayList<Appointment> future) {
        this.future = future;
    }
}
