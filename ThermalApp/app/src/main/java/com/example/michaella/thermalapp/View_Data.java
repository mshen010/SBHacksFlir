package com.example.michaella.thermalapp;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class View_Data extends AppCompatActivity {

    String[] patients = new String[1];
    Button contentButton;

    ListView patient_view;
    ArrayAdapter<String> arrayAdapter;
    /*
    My general idea for this activity:
    We want to display the whole list of patients onto the listview from the database.
    What we do first is establish our arrayAdapter and and listview. Then, we simply
    just set the adapter on the onDataChanged function. What this does is sets the actual
    list, but ONLY after data is being stored onto the array from the database. If not, we
    get a null reference error.

    Now, this only worked for one element. Theoretically speaking if we had multiple patients
    then we can add onto more to the listview. How do we do that? When we initialize our array
    we should have some variable storing the current number of patients per user. Then, we check
    the user's patients and allocate the array with that number. We then follow the rest of this
    process and set up the list view to see the patient's data.

    We need an index for the for loop to do this as well. Also, we may need to find a way to expand
    on a patient's information if you press on it.

    Note: We may need some users class to store in the data.

    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__data);
        //Button and array adapter set up
        contentButton = (Button) findViewById(R.id.contentButton);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1  ,patients);
        //Set up list
        patient_view = (ListView) findViewById(R.id.view);
        //Setup Firebase
        Firebase.setAndroidContext(this);
        Firebase users = new Firebase("https://datatemp.firebaseio.com/patients/1");
        //Test nodes created
        //users.child("name").setValue("Bob");
        //users.child("age").setValue("15");
        System.out.println("This should be initialized.");

        Firebase list = new Firebase("https://datatemp.firebaseio.com/patients");
        list.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " patients.");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    //TODO: For each snapshot, we need to have some index that we increment by 1
                    //for each patient.
                    Patient patient = postSnapshot.getValue(Patient.class);

                    patients[0] = patient.getName() + " / " + patient.getAge();
                    System.out.println(patients[0]);

                }
                patient_view.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }


        });



        contentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){

                Firebase patients = new Firebase("https://datatemp.firebaseio.com/patients");
                patients.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("There are " + dataSnapshot.getChildrenCount() + " patients.");
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                            Patient patient = postSnapshot.getValue(Patient.class);
                            System.out.println(patient.getName() + " / " + patient.getAge());

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
            }
        });

    }
}
