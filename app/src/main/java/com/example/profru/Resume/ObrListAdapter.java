package com.example.profru.Resume;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.profru.R;

import java.util.ArrayList;

public class ObrListAdapter extends BaseAdapter {

    private ArrayList<ObrItem> list;
    private LayoutInflater inflater;
    private FragmentManager fm;
    private ListView lv;
    private int height = -1;

    public ObrListAdapter(LayoutInflater _inflater, FragmentManager _fm, ListView _lv) {
        inflater = _inflater;
        fm = _fm;
        lv = _lv;
        list = new ArrayList<>();
    }

    public ArrayList<ObrItem> getList() {
        return list;
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
            view = inflater.inflate(R.layout.resume_listitem, viewGroup, false);

        ObrItem item = (ObrItem) getItem(i);

        ((TextView) view.findViewById(R.id.item_text)).setText(item.getType());

        view.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialog dialog = new ListDialog(lv.getContext(), 0, ObrListAdapter.this, i);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        if (height == -1) {
            int UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(UNBOUNDED, UNBOUNDED);
            height = view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams lp = lv.getLayoutParams();
        lp.height = 1+height * list.size();
        lv.setLayoutParams(lp);

        return view;
    }

    public void addItem(ObrItem item) {
        list.add(item);
        lv.invalidateViews();
    }
}
