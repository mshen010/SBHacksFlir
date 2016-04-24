package com.flir.flironeexampleapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class NewPatientActivity extends AppCompatActivity {

    Intent i;
    User user;

    EditText Name;
    EditText Gender;
    EditText Age;

    Button CaptureTemp;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        i = getIntent();
        user = i.getParcelableExtra("user");
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
                intent.putExtra("user", user);
                startActivityForResult(intent, 5);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Firebase newPatient = new Firebase("https://datatemp.firebaseio.com/" + user.getUID() + "/patients/" + user.getNumPatients() + "/");
                newPatient.child("name").setValue(Name.getText().toString());
                newPatient.child("gender").setValue(Gender.getText().toString());
                //TODO: Increment number of patients in User
                newPatient.child("age").setValue(Age.getText().toString());
                newPatient.child("id").setValue(user.getNumPatients());
                user.newPatient();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated_user", user);
                setResult(RESULT_OK, resultIntent);

                finish();
                Toast.makeText(getApplicationContext(), "Patient created.", Toast.LENGTH_SHORT);
                //finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            user = data.getParcelableExtra("updated_user"); //Brought from OTHER activities.
            Firebase newPatient = new Firebase("https://datatemp.firebaseio.com/" + user.getUID() + "/patients/" + user.getNumPatients() + "/");
        }
        else if (resultCode == RESULT_CANCELED){
            Log.i("Main", "Activity result failed");
        }
    }
}
