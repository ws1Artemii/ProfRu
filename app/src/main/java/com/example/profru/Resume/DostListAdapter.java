package com.example.profru.Resume;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.profru.R;

import java.util.ArrayList;

public class DostListAdapter extends BaseAdapter {

    private ArrayList<DostItem> list;
    private LayoutInflater inflater;
    private FragmentManager fm;
    private ListView lv;
    private int current_edit_index = -1;
    private View.OnClickListener listener;

    public DostListAdapter(LayoutInflater _inflater, FragmentManager _fm, ListView _lv, View.OnClickListener _listener) {
        inflater = _inflater;
        fm = _fm;
        lv = _lv;
        list = new ArrayList<>();
        listener = _listener;
    }

    public ArrayList<DostItem> getList() {
        return list;
    }

    public int getCurrent_edit_index() {
        return current_edit_index;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void removeItem(int i) {
        list.remove(i);
        lv.invalidateViews();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
            view = inflater.inflate(R.layout.dost_listitem, viewGroup, false);

        DostItem item = (DostItem) getItem(i);

        if (current_edit_index == i) {
            view.findViewById(R.id.edit).setVisibility(View.GONE);
            view.findViewById(R.id.accept).setVisibility(View.VISIBLE);
            view.findViewById(R.id.image).setOnClickListener(listener);
            ((EditText) view.findViewById(R.id.descr)).setEnabled(true);
        }
        else {
            view.findViewById(R.id.edit).setVisibility(View.VISIBLE);
            view.findViewById(R.id.accept).setVisibility(View.GONE);
            view.findViewById(R.id.image).setOnClickListener(null);
            ((EditText) view.findViewById(R.id.descr)).setEnabled(false);
            ((EditText) view.findViewById(R.id.descr)).setInputType(InputType.TYPE_NULL);
        }

        view.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_edit_index = i;
                lv.invalidateViews();
            }
        });

        EditText descr = (EditText) view.findViewById(R.id.descr);

        view.findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_edit_index = -1;
                item.setDescription(descr.getText().toString());
                lv.invalidateViews();
            }
        });

        view.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialog dialog = new ListDialog(lv.getContext(), 2, DostListAdapter.this, i);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        ((ImageView) view.findViewById(R.id.image)).setImageDrawable(item.getImage());

        if(current_edit_index != i) ((EditText) view.findViewById(R.id.descr)).setText(item.getDescription());

        return view;
    }

    public void addItem() {
        current_edit_index = list.size();
        list.add(new DostItem(inflater.getContext().getDrawable(R.drawable.noimage), ""));
        lv.invalidateViews();
    }

    public void add(DostItem item) {
        list.add(item);
    }
}
