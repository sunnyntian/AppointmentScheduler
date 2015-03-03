package com.cmu.as.entities.appointment;

/**
 * Created by Tina on 14-4-15.
 */
public class Appointment implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //        db.execSQL("CREATE TABLE appointment (AppID INTEGER PRIMARY KEY, DocID INTEGER, PatID INTEGER" +
    //    "AppDate VARCHAR, AppTime VARCHAR, AppAvailability INTEGER)");

    private String AppID;
    private String DocID;
    private String PatID;
    private String AppDate;
    private String AppTime;
    private String AppAvailability;
    private String AppCheckIn;
    private String HosName;
    private String DocName;


    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    public String getDocID() {
        return DocID;
    }

    public void setDocID(String docID) {
        DocID = docID;
    }

    public String getPatID() {
        return PatID;
    }

    public void setPatID(String patID) {
        PatID = patID;
    }

    public String getAppDate() {
        return AppDate;
    }

    public void setAppDate(String appDate) {
        AppDate = appDate;
    }

    public String getAppTime() {
        return AppTime;
    }

    public void setAppTime(String appTime) {
        AppTime = appTime;
    }

    public String getAppAvailability() {
        return AppAvailability;
    }

    public void setAppAvailability(String appAvailability) {
        AppAvailability = appAvailability;
    }

    public String getAppCheckIn() {
        return AppCheckIn;
    }

    public void setAppCheckIn(String appCheckIn) {
        AppCheckIn = appCheckIn;
    }

    public String getHosName() {
        return HosName;
    }

    public void setHosName(String hosName) {
        HosName = hosName;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }
}
