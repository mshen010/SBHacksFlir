package com.flir.flironeexampleapplication;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michaella on 4/24/2016.
 */
public class User extends Application implements Parcelable {
    private String UID;
    private int numPatients;

    public User(){
    }
    public User(Parcel p)
    {
        UID = p.readString();
        numPatients = p.readInt();
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel (Parcel dest, int flags)
    {
        dest.writeString(UID);
        dest.writeInt(numPatients);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel parcel)
        {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    public String getUID() { return UID; }
    public void setUID(String i) {UID = i; }
    public int getNumPatients() { return numPatients; }
    public void newPatient() { numPatients += 1; }
    public void setNumPatients(int i) { numPatients = i; }
    //TODO: Extend functionality to all activities
}
