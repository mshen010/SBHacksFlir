package com.flir.flironeexampleapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.collection.LLRBNode;
import com.firebase.client.collection.LLRBNode.Color;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import static com.firebase.client.collection.LLRBNode.Color.*;

public class SignUp extends FragmentActivity {

    //-------------Variables to use------------------
    EditText emailEditText;
    EditText passwordEditText;
    Button signUpButton;
    EditText firstNameEditText;
    EditText lastNameEditText;
    Spinner genderSpinner;
    //    Button birthDateButton;
    EditText birthDateEditText;
    TextView displayDateTextView;
    int mYear;
    int mMonth;
    int mDay;

    /** This integer will uniquely define the dialog to be used for displaying date picker.*/
    static final int DATE_DIALOG_ID = 0;

    //-------------------------show date picker dialog------------------------------
//    public void setDate(View v) {
//        DatePickerFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "date_picker");
//    }

//    public void showDatePickerDate(View v) {
//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
//    }

    

    //--------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Firebase.setAndroidContext(this);

        //----------------Varaibles Defined connect to .xml files-------------------
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        firstNameEditText = (EditText) findViewById(R.id.firstName);
        lastNameEditText = (EditText) findViewById(R.id.lastName);
        genderSpinner = (Spinner) findViewById(R.id.gender);
        //----------------------------------------------------------------------------

        //----------------------Spinner Code------------------------------
        String[] items = new String[]{"Select Gender", "Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        genderSpinner.setAdapter(adapter);

        //------------------------Date Picker---------------------------


        //--------------------Sign Up Button Listener-------------------------
        //Email works as long as you include @ and .com
        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Create a firebase ref to the website
                Firebase myFirebaseRef = new Firebase("https://datatemp.firebaseio.com/");
                //sign up the user and see if it signs up successfully with given email and password
                myFirebaseRef.createUser(emailEditText.getText().toString(), passwordEditText.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {

                        //check if any of the fields are empty if so then return an error
                        if (firstNameEditText.getText().toString().trim().equalsIgnoreCase("") ||
                                lastNameEditText.getText().toString().trim().equalsIgnoreCase("") ||
                                genderSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("Select Gender"))
                        {
                            if(firstNameEditText.getText().toString().trim().equalsIgnoreCase(""))
                                firstNameEditText.setError("This field can not be blank");
                            if(lastNameEditText.getText().toString().trim().equalsIgnoreCase(""))
                                lastNameEditText.setError("This field can not be blank");
                            if(genderSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("Select Gender"))
                            {
                                TextView errorText = (TextView) genderSpinner.getSelectedView();
                                errorText.setError("anything here, just to add the icon");
                                errorText.setText("Please select a gender.");//changes the selected item text to this
                            }
                        }
                        else
                        {
                            Toast.makeText(SignUp.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                            //Afterwards sign the user into the database and store in the fields that they typed in
                            Firebase myFirebaseRef2 = new Firebase("https://datatemp.firebaseio.com/");
                            myFirebaseRef2.authWithPassword(emailEditText.getText().toString(), passwordEditText.getText().toString(), new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    Toast.makeText(SignUp.this, "User ID: " + authData.getUid() + "Provider: " + authData.getProvider(), Toast.LENGTH_SHORT).show();    //TODO:-----------THIS IS FOR DEBUG REMOVE THIS WHEN NEEDED

                                    //grab the user id and store it in the base
                                    Firebase users = new Firebase("https://datatemp.firebaseio.com/");
                                    users.child("User ID (Email): ").setValue(authData.getUid());

                                    //store in the values in the user branch
                                    Firebase userData = new Firebase("https://datatemp.firebaseio.com/" + authData.getUid());
                                    userData.child("First Name:").setValue(firstNameEditText.getText().toString());
                                    userData.child("Last Name:").setValue(lastNameEditText.getText().toString());
                                    userData.child("Gender:").setValue(genderSpinner.getSelectedItem().toString());

                                    Firebase intData = new Firebase("https://datatemp.firebaseio.com/" + authData.getUid() + "/internal/0/");
                                    intData.child("numPats").setValue("0");

                                    //TODO: Where you include the rules/storage for firebase data

                                    //users.child("users").setValue(authData.getUid());


                                    //insert next intent mainly the menu

                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    Toast.makeText(SignUp.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    Log.e("Login Error", firebaseError.toString());
                                }
                            });

                            //switches back to the start activity
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(SignUp.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                        Log.e("Account Creation Error", firebaseError.toString());
                    }
                });

//
            }
        });

    }

}