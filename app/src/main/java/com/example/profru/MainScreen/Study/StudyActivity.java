package com.example.profru.MainScreen.Study;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.profru.R;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent i = getIntent();

        String title = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        int image = i.getIntExtra("image", 0);
        int cost = i.getIntExtra("cost", 10);
        int time = i.getIntExtra("time", 1);

        ((TextView) findViewById(R.id.vac_descr)).setText(description);
        ((TextView) findViewById(R.id.card_description)).setText(title);
        ((TextView) findViewById(R.id.card_cost)).setText(cost + " р");
        ((TextView) findViewById(R.id.time)).setText(time + " мес.");
        TypedArray a = getResources().obtainTypedArray(R.array.images);
        ((ImageView) findViewById(R.id.card_image)).setImageDrawable(getDrawable(a.getResourceId(image, R.drawable.noimage)));
    }
}