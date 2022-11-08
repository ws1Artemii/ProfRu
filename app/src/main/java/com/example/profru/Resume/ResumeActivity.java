package com.example.profru.Resume;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.profru.MainScreen.Vacations.VacationListActivity;
import com.example.profru.R;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

public class ResumeActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_DOST = 2;
    private String avatar_path;
    private String image_path;

    private DostListAdapter dost_adapter;
    private ObrListAdapter obr_adapter;
    private WorkListAdapter work_adapter;
    private ListView dost_list;

    private TextView birthdate;
    private EditText fio;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            avatar_path = cursor.getString(columnIndex);
            cursor.close();

            ((ImageView) findViewById(R.id.avatar)).setImageBitmap(BitmapFactory.decodeFile(avatar_path));
        }
        else if(requestCode == RESULT_LOAD_DOST && resultCode==RESULT_OK && data != null) {
            Log.e("DOST", "LOADED");
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            image_path = cursor.getString(columnIndex);
            cursor.close();

            ((DostItem) dost_adapter.getItem(dost_adapter.getCurrent_edit_index())).setImage_path(image_path);
            ((DostItem) dost_adapter.getItem(dost_adapter.getCurrent_edit_index())).setImage(Drawable.createFromPath(image_path));
            dost_list.invalidateViews();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ResumeActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        ActivityCompat.requestPermissions(ResumeActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }
        });

        dost_list = findViewById(R.id.dost_list);
        dost_adapter = new DostListAdapter(getLayoutInflater(), getSupportFragmentManager(), dost_list, this::dost_listener);
        dost_list.setAdapter(dost_adapter);

        findViewById(R.id.dost_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dost_adapter.addItem();
            }
        });

        ListView obr_list = findViewById(R.id.obr_list);
        obr_adapter = new ObrListAdapter(getLayoutInflater(), getSupportFragmentManager(), obr_list);
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
        work_adapter = new WorkListAdapter(getLayoutInflater(), getSupportFragmentManager(), work_list);
        work_list.setAdapter(work_adapter);

        findViewById(R.id.work_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkAddDialog wd = new WorkAddDialog(ResumeActivity.this, work_adapter);
                wd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                wd.show();
            }
        });

        birthdate = findViewById(R.id.birthdate);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
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

        fio = findViewById(R.id.fio);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog cd = new CustomDialog(ResumeActivity.this, "Сохранить все и перейти на главную страницу?");
                cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cd.show();
            }
        });
    }

    public void SaveAll() throws IOException {
        //XML
        FileOutputStream fileos;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "resume.xml");
        file.createNewFile();
        fileos = new FileOutputStream(file);

        StringWriter writer = new StringWriter();
        XmlSerializer xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(writer);

        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.startTag(null, "data");

        //USER DATA
        xmlSerializer.startTag(null, "user");

        xmlSerializer.startTag(null, "FIO");
        xmlSerializer.text(fio.getText().toString());
        xmlSerializer.endTag(null, "FIO");

        xmlSerializer.startTag(null, "birthdate");
        xmlSerializer.text(birthdate.getText().toString());
        xmlSerializer.endTag(null, "birthdate");

        xmlSerializer.startTag(null, "avatar");
        xmlSerializer.text(avatar_path);
        xmlSerializer.endTag(null, "avatar");

        xmlSerializer.endTag(null, "user");

        //OBR
        xmlSerializer.startTag(null, "obr");
        for(ObrItem item : obr_adapter.getList()) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.text(item.getType() + "|" + item.getOrigin());
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "obr");

        //WORK
        xmlSerializer.startTag(null, "work");
        for(WorkItem item : work_adapter.getList()) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.text(item.getProfession() + "|" + item.getPlace() + "|" + item.getTime());
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "work");

        //DOST
        xmlSerializer.startTag(null, "dost");
        for(DostItem item : dost_adapter.getList()) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.text(item.getImage_path() + "|" + item.getDescription());
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "dost");

        xmlSerializer.endTag(null, "data");
        xmlSerializer.endDocument();
        xmlSerializer.flush();

        String dataWrite = writer.toString();
        fileos.write(dataWrite.getBytes());
        fileos.close();

        startActivity(new Intent(ResumeActivity.this, VacationListActivity.class));
        finish();
    }

    private void dost_listener(View view) {
        int i = dost_adapter.getCurrent_edit_index();
        switch (view.getId()) {
            case R.id.image:
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(in, RESULT_LOAD_DOST);
                }
                break;
        }
    }
}