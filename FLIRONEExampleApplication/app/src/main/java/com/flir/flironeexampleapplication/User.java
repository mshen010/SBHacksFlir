package com.flir.flironeexampleapplication;

/**
 * Created by Michaella on 4/24/2016.
 */
public class User {
    private String UID;
    private int numPatients;

    public String getUID() { return UID; }
    public int getNumPatients() { return numPatients; }
    public void newPatient() { numPatients += 1; }
    //TODO: Extend functionality to all activities
}
