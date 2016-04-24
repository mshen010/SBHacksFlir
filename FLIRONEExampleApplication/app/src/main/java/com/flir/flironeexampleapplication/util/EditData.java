package com.flir.flironeexampleapplication.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.flir.flironeexampleapplication.Patient;
import com.flir.flironeexampleapplication.PreviewActivity;
import com.flir.flironeexampleapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EditData extends AppCompatActivity {

    String[] temps = new String[5];
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, temps);

    TextView idText;
    TextView tempText;
    EditText nameText;
    EditText ageText;
    Button submitButton;
    Button deleteButton;
    Button tempButton;

    //Stores non-editable ID
    String ID;

    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        idText = (TextView) findViewById(R.id.Id);
        tempText = (TextView) findViewById(R.id.AvgTemp);
        nameText = (EditText) findViewById(R.id.Name);
        ageText = (EditText) findViewById(R.id.Age);
        submitButton = (Button) findViewById(R.id.Submit);
        //deleteButton = (Button) findViewById(R.id.Delete);
        tempButton = (Button) findViewById(R.id.Photo);

        lv = (ListView) findViewById(R.id.listView);

        Intent i = getIntent();
        String value = i.getStringExtra("curr_data");
        //Split string by newlines into array
        String[] lines = value.split(System.getProperty("line.separator"));
        ID = lines[0];
        Firebase curr_user = new Firebase("https://datatemp.firebaseio.com/patients/");
        curr_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " patients.");
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    //Once we reach the ID of the patient, break out of the for loop.
                   Patient patient = postSnapShot.getValue(Patient.class);
                    if (patient.getId() == Integer.parseInt(ID)) {
                        idText.setText("ID: " + String.valueOf(patient.getId()));
                        ID = String.valueOf(patient.getId());
                        nameText.setText(patient.getName());
                        ageText.setText(String.valueOf(patient.getAge()));
                        for(int i = 0; i < 5; ++i) {
                             temps[i] = patient.recentTemps[i];
                        }
                        break;
                    }
                    lv.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }

    });


     /*   idText.setText("ID: " + lines[0]);
        ID = lines[0];
        nameText.setText(lines[1]);
        ageText.setText(lines[2]);*/

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase new_data = new Firebase("https://datatemp.firebaseio.com/patients/" + ID );
                new_data.child("name").setValue(nameText.getText().toString());
                new_data.child("age").setValue(ageText.getText().toString());
                Toast.makeText(getApplicationContext(), "Patient updated.", Toast.LENGTH_SHORT);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Firebase rem_data = new Firebase("https://datatemp.firebase.io.com/patients/" + ID);
                rem_data.setValue(null);
                Toast.makeText(getApplicationContext(), "Patient deleted.", Toast.LENGTH_SHORT);
                finish();
            }
        });

        tempButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), PreviewActivity.class);

            }
        });

    }
}
