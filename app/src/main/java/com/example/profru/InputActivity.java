package com.example.profru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        ImageView input_button_2 = (ImageView) findViewById(R.id.input_button_2);
        input_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

        ImageView input_button_1 = (ImageView) findViewById(R.id.input_button_1);
        input_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputActivity.this,SummaryActivity.class);
                startActivity(intent);
            }
        });
    }
}