package com.flir.flironeexampleapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.flir.flironeexampleapplication.R;
import com.flir.flironeexampleapplication.ViewData;

public class MenuActivity extends AppCompatActivity {

    User user;
    Intent i;
    Button NewPatientButton;
    Button ViewPatientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        NewPatientButton = (Button) findViewById(R.id.NewPatientButton);
        ViewPatientButton = (Button) findViewById(R.id.ViewPatientButton);

        i = getIntent();
        user = i.getParcelableExtra("user");


        NewPatientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewPatientActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 2);
            }
        });
        ViewPatientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewData.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 3);
            }
        });
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
