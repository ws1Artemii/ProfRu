package com.example.profru.Resume;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.profru.R;

public class WorkAddDialog extends Dialog implements View.OnClickListener {

    private EditText place;
    private EditText profession;
    private EditText time;
    private WorkListAdapter adapter;

    public WorkAddDialog(@NonNull Context context, WorkListAdapter _adapter) {
        super(context);
        adapter = _adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.work_add_dialog);

        place = findViewById(R.id.place);
        profession = findViewById(R.id.profession);
        time = findViewById(R.id.time);

        findViewById(R.id.no).setOnClickListener(this);
        findViewById(R.id.yes).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no:
                dismiss();
                break;
            case R.id.yes:
                if(place.getText().toString().equals("") || profession.getText().toString().equals("") || time.getText().toString().equals("")) {
                    break;
                }
                else {

                    int t = 0;
                    try {
                        t = Integer.parseInt(time.getText().toString());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }

                    adapter.addItem( new WorkItem(place.getText().toString(), profession.getText().toString(), t ));
                    dismiss();
                }
        }
    }
}
