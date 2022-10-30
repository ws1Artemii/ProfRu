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

public class CustomDialog extends Dialog implements View.OnClickListener {

    private String message;
    private Activity activity;
    private Class targetActivity;

    public CustomDialog(String _message, Activity _activity, Class _targetActivity) {
        super(_activity);
        message = _message;
        activity = _activity;
        targetActivity = _targetActivity;
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
                activity.startActivity(new Intent(activity, targetActivity));
                activity.finish();
                dismiss();
                break;
        }
    }
}
