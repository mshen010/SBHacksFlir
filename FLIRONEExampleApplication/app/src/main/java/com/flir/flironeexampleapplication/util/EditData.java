package com.flir.flironeexampleapplication.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.flir.flironeexampleapplication.Patient;
import com.flir.flironeexampleapplication.R;

public class EditData extends AppCompatActivity {

    TextView idText;
    EditText nameText;
    EditText ageText;
    Button submitButton;
    Button deleteButton;

    //Stores non-editable ID
    String ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        idText = (TextView) findViewById(R.id.Id);
        nameText = (EditText) findViewById(R.id.Name);
        ageText = (EditText) findViewById(R.id.Age);
        submitButton = (Button) findViewById(R.id.Submit);
        deleteButton = (Button) findViewById(R.id.Delete);

        Intent i = getIntent();
        String value = i.getStringExtra("curr_data");
        //Split string by newlines into array
        String[] lines = value.split(System.getProperty("line.separator"));

        idText.setText("ID: " + lines[0]);
        ID = lines[0];
        nameText.setText(lines[1]);
        ageText.setText(lines[2]);

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

    }
}
