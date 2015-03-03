package com.cmu.as.entities.hospital;


/**
 * Created by Tina on 14-4-20.
 */

public class Hospital implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String HosID;
    String HosName;
    String HosAdd;
    String HosLon;
    String HosLat;
    String HosPhone;
    String HosDis;

    public String getHosDis() {
        return HosDis;
    }

    public void setHosDis(String hosDis) {
        HosDis = hosDis;
    }

    /**
     * @return the hosID
     */
    public String getHosID() {
        return HosID;
    }

    /**
     * @return the hosName
     */
    public String getHosName() {
        return HosName;
    }

    /**
     * @return the hosAdd
     */
    public String getHosAdd() {
        return HosAdd;
    }


    /**
     * @return the hosPhone
     */
    public String getHosPhone() {
        return HosPhone;
    }

    /**
     * @param hosID the hosID to set
     */
    public void setHosID(String hosID) {
        HosID = hosID;
    }

    /**
     * @param hosName the hosName to set
     */
    public void setHosName(String hosName) {
        HosName = hosName;
    }

    /**
     * @param hosAdd the hosAdd to set
     */
    public void setHosAdd(String hosAdd) {
        HosAdd = hosAdd;
    }

    public String getHosLon() {
        return HosLon;
    }

    public void setHosLon(String hosLon) {
        HosLon = hosLon;
    }

    public String getHosLat() {
        return HosLat;
    }

    public void setHosLat(String hosLat) {
        HosLat = hosLat;
    }


    /**
     * @param hosPhone the hosPhone to set
     */
    public void setHosPhone(String hosPhone) {
        HosPhone = hosPhone;
    }
}
