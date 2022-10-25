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

public class ObrAddDialog extends Dialog implements View.OnClickListener {

    private Spinner spinner;
    private EditText origin;
    private ObrListAdapter adapter;

    public ObrAddDialog(@NonNull Context context, ObrListAdapter _adapter) {
        super(context);
        adapter = _adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.obr_add_dialog);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(getContext(), R.array.obr_types, R.layout.obr_spinner_item);
        spinner.setAdapter(adapter);

        origin = findViewById(R.id.origin);

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
                if(spinner.getSelectedItemPosition()==0 || origin.getText().toString().equals("")) {
                    if (spinner.getSelectedItemPosition() == 0)
                        Toast.makeText(getContext(), "Не выбран тип образования", Toast.LENGTH_SHORT);
                    if (origin.getText().toString().equals(""))
                        Toast.makeText(getContext(), "Не указано место обучения", Toast.LENGTH_SHORT);
                    break;
                }
                else {
                    adapter.addItem( new ObrItem(origin.getText().toString(), spinner.getSelectedItemPosition()-1) );
                    dismiss();
                }
        }
    }
}
