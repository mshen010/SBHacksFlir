package com.flir.flironeexampleapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.flir.flironeexampleapplication.R;
import com.flir.flironeexampleapplication.ViewData;

public class MenuActivity extends AppCompatActivity {

    Button NewPatientButton;
    Button ViewPatientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        NewPatientButton = (Button) findViewById(R.id.NewPatientButton);
        ViewPatientButton = (Button) findViewById(R.id.ViewPatientButton);

        NewPatientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewPatientActivity.class);
                startActivity(intent);
            }
        });
        ViewPatientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewData.class);
                startActivity(intent);
            }
        });
    }

}
