package com.example.profru.RegistrationAndSignIn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import com.example.profru.MainScreen.MainActivity;
import com.example.profru.Resume.ResumeActivity;

import java.io.File;

public class MergeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "ProfRu Files");
        if (folder.exists())
            startActivity(new Intent(MergeActivity.this, MainActivity.class));
        else
            startActivity(new Intent(MergeActivity.this, ResumeActivity.class));
        finish();
    }
}