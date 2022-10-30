package com.example.profru.Resume;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.profru.R;

import java.util.ArrayList;

public class WorkListAdapter extends BaseAdapter {

    private ArrayList<WorkItem> list;
    private LayoutInflater inflater;
    private FragmentManager fm;
    private ListView lv;
    private int height = -1;

    public WorkListAdapter(LayoutInflater _inflater, FragmentManager _fm, ListView _lv) {
        inflater = _inflater;
        fm = _fm;
        lv = _lv;
        list = new ArrayList<>();
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

        if (view == null)
            view = inflater.inflate(R.layout.resume_listitem, viewGroup, false);

        WorkItem item = (WorkItem) getItem(i);

        String t = (0.1 * Math.round(item.getTime() * 10) + " ").replace(".0", "");
        if (item.getTime() >= 11 && item.getTime() < 20)
            t += "лет";
        else {
            switch ((int) (Math.floor(item.getTime()) % 10)) {
                case 1:
                    t += "год";
                    break;
                case 2:
                    t += "года";
                    break;
                case 3:
                    t += "года";
                    break;
                case 4:
                    t += "года";
                    break;
                case 5:
                    t += "лет";
                    break;
                case 6:
                    t += "лет";
                    break;
                case 7:
                    t += "лет";
                    break;
                case 8:
                    t += "лет";
                    break;
                case 9:
                    t += "лет";
                    break;
                case 0:
                    t += "лет";
                    break;
            }
        }

        ((TextView) view.findViewById(R.id.item_text)).setText(item.getProfession() + ", " + t);

        view.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialog dialog = new ListDialog(lv.getContext(), 1, WorkListAdapter.this, i);
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
        lp.height = height * list.size();
        lv.setLayoutParams(lp);

        return view;
    }

    public void addItem(WorkItem item) {
        list.add(item);
        lv.invalidateViews();
    }
}
