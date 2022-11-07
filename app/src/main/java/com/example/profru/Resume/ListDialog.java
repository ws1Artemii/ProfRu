package com.example.profru.Resume;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.profru.R;

public class ListDialog extends Dialog implements View.OnClickListener {

    private ListAdapter adapter;
    private int type;
    private int index;

    public ListDialog(Context ctx, int _type, ListAdapter _adapter, int _index) {
        super(ctx);
        type = _type;
        index = _index;
        adapter = _adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.default_dialog);

        ((TextView) findViewById(R.id.title)).setText("Вы действительно хотите удалить данный элемент?");
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
                if(type == 0) {
                    ObrListAdapter a = (ObrListAdapter) adapter;
                    a.removeItem(index);
                }
                else if(type == 1) {
                    WorkListAdapter a = (WorkListAdapter) adapter;
                    a.removeItem(index);
                }
                else if(type == 2) {
                    DostListAdapter a = (DostListAdapter) adapter;
                    a.removeItem(index);
                }
                dismiss();
                break;
        }
    }
}
