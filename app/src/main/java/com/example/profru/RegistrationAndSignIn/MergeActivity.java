package com.example.profru.RegistrationAndSignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.profru.MainScreen.MainActivity;
import com.example.profru.R;
import com.example.profru.Resume.ResumeActivity;

public class MergeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);

        findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MergeActivity.this, ResumeActivity.class));
                finish();
            }
        });

        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MergeActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}