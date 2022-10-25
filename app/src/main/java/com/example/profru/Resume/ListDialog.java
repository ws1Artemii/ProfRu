package com.example.profru.Resume;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ListDialog extends DialogFragment {

    private ListAdapter adapter;
    private int type;
    private int index;
    private ListView listView;

    public ListDialog(int _type, ListAdapter _adapter, ListView _listView, int _index) {
        type = _type;
        index = _index;
        listView = _listView;
        adapter = _adapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Вы действительно хотите удалить данный элемент?")
                .setPositiveButton("Окей", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(type == 0){
                            ObrListAdapter a = (ObrListAdapter) adapter;
                            a.removeItem(index);
                            dialogInterface.cancel();
                        }
                        else if(type == 1){
                            WorkListAdapter a = (WorkListAdapter) adapter;
                            a.removeItem(index);
                            dialogInterface.cancel();
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        return builder.create();
    }
}
