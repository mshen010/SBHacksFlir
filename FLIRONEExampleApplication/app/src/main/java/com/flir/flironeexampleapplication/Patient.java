package com.flir.flironeexampleapplication;

/**
 * Created by skill on 4/23/2016.
 */
public class Patient {
    private String name;
    private int age;
    private int id;
    private float temperature;
    private String gender;
    public String[] recentTemps = new String[5];
    public Patient() {

    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public int getId()
    {
        return id;
    }

    public String getGender() { return gender; }

    public float getTemperature() { return temperature; }

}
