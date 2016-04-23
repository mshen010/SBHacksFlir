package com.example.michaella.thermalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;

public class View_Data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__data);

        Firebase users = new Firebase("https://chatlisttest.firebaseio.com/user");
        users.child(authData.getUid()).setValue("Name:");
    }
}
