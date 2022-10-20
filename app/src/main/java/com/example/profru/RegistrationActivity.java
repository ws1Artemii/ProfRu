package com.example.profru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ImageView input_button_3 = (ImageView) findViewById(R.id.input_button_3);
        input_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this,SummaryActivity.class);
                startActivity(intent);
            }
        });
        ImageView input_button_4 = (ImageView) findViewById(R.id.input_button_4);
        input_button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}