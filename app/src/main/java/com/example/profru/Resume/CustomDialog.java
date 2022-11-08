package com.example.profru.Resume;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.profru.MainScreen.Vacations.VacationListActivity;
import com.example.profru.R;

import java.io.IOException;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private String message;
    private Activity activity;

    public CustomDialog(Activity _activity, String _message) {
        super(_activity);
        activity = _activity;
        message = _message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.default_dialog);

        ((TextView) findViewById(R.id.title)).setText(message);
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
                try {
                    ((ResumeActivity) activity).SaveAll();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                dismiss();
                break;
        }
    }
}
