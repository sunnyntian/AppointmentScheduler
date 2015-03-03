package com.cmu.as.entities.doctor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tina on 14-4-15.
 */
public class DoctorInterface {

    private Doctor doc;

    public DoctorInterface() {
        this.doc = new Doctor();
        this.doc.setDocName("1");
        this.doc.setDocSpeciality("1");
        this.doc.setDocPhone("1");
        this.doc.setDepID("1");
        this.doc.setDocID("1");
        this.doc.setDocPsw("1");
    }


    public Doctor getDoc() {
        return doc;
    }

    public void setDoc(Doctor doc) {

        this.doc = doc;
    }

    public String getDocID() {
        return this.doc.getDocID();
    }

    public void setDocID(String docID) {
        this.doc.setDocID(docID);
    }

    public String getDepID() {
        return this.doc.getDepID();
    }

    public void setDepID(String depID) {
        this.doc.setDepID(depID);
    }

    public String getDocName() {
        return this.doc.getDocName();
    }

    public void setDocName(String docName) {
        this.doc.setDocName(docName);
    }

    public String getDocPhone() {
        return this.doc.getDocPhone();
    }

    public void setDocPhone(String docPhone) {
        this.doc.setDocPhone(docPhone);
    }

    public String getDocSpeciality() {
        return this.doc.getDocSpeciality();
    }

    public void setDocSpeciality(String docSpeciality) {
        this.doc.setDocSpeciality(docSpeciality);
    }

}
