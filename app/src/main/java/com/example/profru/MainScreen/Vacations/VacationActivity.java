package com.example.profru.MainScreen.Vacations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.profru.MainScreen.MainActivity;
import com.example.profru.R;

import java.util.ArrayList;

public class VacationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation);

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
        int zp = i.getIntExtra("zp", 10);
        String actions = i.getStringExtra("actions");

        ((TextView) findViewById(R.id.vac_descr)).setText(description);
        ((TextView) findViewById(R.id.card_description)).setText(title);
        ((TextView) findViewById(R.id.vac_requiments)).setText(actions);
        ((TextView) findViewById(R.id.card_cost)).setText("от " + zp + " р");
        TypedArray a = getResources().obtainTypedArray(R.array.images);
        ((ImageView) findViewById(R.id.card_image)).setImageDrawable(getDrawable(a.getResourceId(image, R.drawable.noimage)));
    }
}