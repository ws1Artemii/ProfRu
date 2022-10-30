package com.example.profru.Resume;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.profru.MainScreen.Vacations.VacationListActivity;
import com.example.profru.R;

import java.util.Calendar;

public class ResumeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog cd = new CustomDialog("Сохранить все и перейти на главную страницу?", ResumeActivity.this, VacationListActivity.class);
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.show();
            }
        });

        ListView obr_list = findViewById(R.id.obr_list);
        ObrListAdapter obr_adapter = new ObrListAdapter(getLayoutInflater(), getSupportFragmentManager(), obr_list);
        obr_list.setAdapter(obr_adapter);

        findViewById(R.id.obr_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObrAddDialog od = new ObrAddDialog(ResumeActivity.this, obr_adapter);
                od.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                od.show();
            }
        });

        ListView work_list = findViewById(R.id.work_list);
        WorkListAdapter work_adapter = new WorkListAdapter(getLayoutInflater(), getSupportFragmentManager(), work_list);
        work_list.setAdapter(work_adapter);

        findViewById(R.id.work_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkAddDialog wd = new WorkAddDialog(ResumeActivity.this, work_adapter);
                wd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                wd.show();
            }
        });

        TextView birthdate = findViewById(R.id.birthdate);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Overrid
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int d = c.get(Calendar.DAY_OF_MONTH);
                int m = c.get(Calendar.MONTH);
                int y = c.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(ResumeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int _y, int _m, int _d) {

                        Log.e("PickedDate", "_y = " + _y + "_m = " + _m + "_d = " + _d);

                        String ms = "" + (_m + 1);
                        if (ms.length() == 1)
                            ms = "0" + ms;

                        String ds = "" + _d;
                        if (ds.length() == 1)
                            ds = "0" + ds;

                        String ys = "" + _y;
                        ys = String.valueOf(ys.charAt(2)) + ys.charAt(3);

                        birthdate.setText((ds + "." + ms + "." + ys).toString());
                    }
                }, d, m, y);
                dpd.show();
            }
        });
    }
}