package com.flir.flironeexampleapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class Login extends AppCompatActivity {

    //Variables for activity
    User user;
    EditText passwordEditText;
    EditText usernameEditText;
    Button loginButton;
    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        user = new User();

        //-----------------Defined Variables to .xml elements------------------------
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        createAccount = (Button) findViewById(R.id.createAccount);
        //-----------------------------------------------------------------------------

        //-----------------------Login Button and intent switch--------------------------
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Firebase myFirebaseRef = new Firebase("https://datatemp.firebaseio.com/");

                myFirebaseRef.authWithPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                        Firebase users = new Firebase("https://datatemp.firebaseio.com/");
                        users.child("Logged-In ID: ").setValue(authData.getUid());

                        //TODO: Where you include the rules/storage for firebase data
                        user.setUID(authData.getUid());
                        //switches back to the menu activity
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.putExtra("user", user);
                        startActivityForResult(intent, 1);

                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        Log.e("Login Error", firebaseError.toString());
                    }
                });
            }
        });

        //-----------------------This is the create account button---------------------------//
        createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
        //------------------------------------------------------------------------------------//
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            user = data.getParcelableExtra("updated_user"); //Brought from OTHER activities.
        }
        else if (resultCode == RESULT_CANCELED){
            Log.i("Main", "Activity result failed");
        }
    }

}
