package com.cmu.as.entities.patient;

/**
 * Created by Tina on 14-4-15.
 */
public class PatientInterface {

    private static Patient data ;

    //constructor
    public PatientInterface(Patient pat) {
        data = new Patient();
        data = pat;
    }
    //get patient
    public Patient getPat() {
        return data;
    }
    public void setPat(Patient pat){
        this.data = pat;
    }
    //get patient ID
    public String getPatID() {
        return data.getPatID();
    }
    //get patient name
    public String getPatName() {
        return data.getPatName();
    }
    //get patient age
    public String getPatAge() {
        return data.getPatAge();
    }
    //get patient gender
    public String getPatGender() {
        return data.getPatGender();
    }
    //get doctor phone by index
    public String getPatInsurance() {
        return data.getPatInsurance();
    }

    public void setPatID(String patID) {
        data.setPatID(patID);
    }
    public void setPatName(String patName) {
        data.setPatName(patName);
    }
    public void setPatAge(String patAge) {
        data.setPatAge(patAge);
    }
    public void setPatGender(String patGender) {
        data.setPatGender(patGender);
    }
    public void setPatInsurance(String patInsurance) {
        data.setPatInsurance(patInsurance);
    }
}
