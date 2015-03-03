package com.cmu.as.entities.hospital;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tina on 14-4-20.
 */
public class HospitalInterface {
    private ArrayList<Hospital> data ;
    private ArrayList<String> depts;
    private ArrayList<ArrayList<String>> doctors;

    //constructor
    public HospitalInterface(ArrayList<Hospital> hos) {
        this.data = hos;
    }
    //set Hospital array list
    public void setHospitalList(ArrayList<Hospital> hos) {
        this.data = hos;
    }
    //get Hospital name list
    public ArrayList<String> getHospitalNames(){
        ArrayList<String> hosNames = new ArrayList<String>();
        Iterator<Hospital> iter = data.iterator();
        while (iter.hasNext()){
            hosNames.add(iter.next().getHosName());
        }
        return hosNames;
    }
    //get Hospital distance list
    public ArrayList<String> getHospitalDistances(){
        ArrayList<String> hosDists = new ArrayList<String>();
        Iterator<Hospital> iter = data.iterator();
        while (iter.hasNext()){
            hosDists.add(iter.next().getHosDis());
        }
        return hosDists;
    }
    //get Hospital add list
    public ArrayList<String> getHospitalAdds(){
        ArrayList<String> hosAdds = new ArrayList<String>();
        Iterator<Hospital> iter = data.iterator();
        while (iter.hasNext()){
            hosAdds.add(iter.next().getHosAdd());
        }
        return hosAdds;
    }

    //get hosptal by the index
    public Hospital getHos(int ind) {
        return this.data.get(ind);
    }
    //get hospital name by index
    public String getHosName(int ind) {
        return this.data.get(ind).getHosName();
    }
    //get hospital add by index
    public String getHosAdd(int ind) {
        return this.data.get(ind).getHosAdd();
    }
    public String getHosDistance(int ind) {
        return this.data.get(ind).getHosDis();
    }

    public void setHosName(int ind, String hosName) {
        this.data.get(ind).setHosName(hosName);
    }
    public void setHosAdd(int ind, String hosAdd) {
        this.data.get(ind).setHosAdd(hosAdd);
    }
    public void setHosDistance(int ind, String hosDistance) {
        this.data.get(ind).setHosDis(hosDistance);
    }

    public ArrayList<String> getDepts() {
        return depts;
    }

    public void setDepts(ArrayList<String> depts) {
        this.depts = depts;
    }

    public ArrayList<ArrayList<String>> getDoctors() {

        return doctors;
    }

    public void setDoctors(ArrayList<ArrayList<String>> doctors) {
        this.doctors = doctors;
    }

    public String getHosIdByName(String name){
        for (Hospital h: data)
        {
            if(h.getHosName().equals(name))
                return h.getHosID();

        }
        return null;
    }

    public int getHosIndexByName(String name){
        int i = 0;
        for (Hospital h: data)
        {
            if(h.getHosName().equals(name))
                return i;
            i++;

        }
        return -1;
    }

}
