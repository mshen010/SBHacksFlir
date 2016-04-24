package com.flir.flironeexampleapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class NewPatientActivity extends AppCompatActivity {

    EditText Name;
    EditText Gender;
    EditText Age;

    Button CaptureTemp;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        Firebase.setAndroidContext(this);

        Name = (EditText) findViewById(R.id.editTextName);
        Gender = (EditText) findViewById(R.id.editTextGender);
        Age = (EditText) findViewById(R.id.editTextAge);

        CaptureTemp = (Button) findViewById(R.id.buttonCaptureTemp);
        Submit = (Button) findViewById(R.id.buttonSubmit);

        CaptureTemp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);
                startActivity(intent);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Firebase newPatient = new Firebase("https://datatemp.firebaseio.com/patients");
                newPatient.child("name").setValue(Name.getText().toString());
                newPatient.child("gender").setValue(Gender.getText().toString());
                //TODO: Increment number of patients in User
                newPatient.child("age").setValue(Age.getText().toString());
                Toast.makeText(getApplicationContext(), "Patient created.", Toast.LENGTH_SHORT);
                finish();
            }
        });
    }
}
