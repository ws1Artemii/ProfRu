package com.example.profru;

import android.media.metrics.Event;
import android.os.Environment;
import android.util.Xml;

import com.example.profru.Resume.DostItem;
import com.example.profru.Resume.ObrItem;
import com.example.profru.Resume.WorkItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

public class User {

    public String fullname;
    public String birthdate;
    public String avatar_path;
    public ArrayList<ObrItem> obrlist;
    public ArrayList<DostItem> dostlist;
    public ArrayList<WorkItem> worklist;

    public User() {
        obrlist = new ArrayList<>();
        dostlist = new ArrayList<>();
        worklist = new ArrayList<>();
    }

    public void Save() throws IOException {
        //XML
        FileOutputStream fileos;
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "ProfRu Files");
        if (!folder.exists())
            folder.mkdir();
        File file = new File(folder, "resume.xml");
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
        xmlSerializer.text(fullname);
        xmlSerializer.endTag(null, "FIO");

        xmlSerializer.startTag(null, "birthdate");
        xmlSerializer.text(birthdate);
        xmlSerializer.endTag(null, "birthdate");

        xmlSerializer.startTag(null, "avatar");
        xmlSerializer.text(avatar_path);
        xmlSerializer.endTag(null, "avatar");

        xmlSerializer.endTag(null, "user");

        //OBR
        xmlSerializer.startTag(null, "obr");
        for (ObrItem item : obrlist) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.text(item.getTypeId() + "|" + item.getOrigin());
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "obr");

        //WORK
        xmlSerializer.startTag(null, "work");
        for (WorkItem item : worklist) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.text(item.getProfession() + "|" + item.getPlace() + "|" + item.getTime());
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "work");

        //DOST
        xmlSerializer.startTag(null, "dost");
        for (DostItem item : dostlist) {
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
    }

    public String toString() {
        return birthdate + "; " + fullname + "; " + avatar_path;
    }

    public void ParseXml() throws IOException, XmlPullParserException {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "ProfRu Files");
        if (!folder.exists())
            folder.mkdir();
        File file = new File(folder, "resume.xml");
        FileInputStream istream = new FileInputStream(file);

        XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
        xpp.setInput(istream, null);

        int event = xpp.getEventType();
        String tag = "";
        String text = "";
        String childOf = "";

        while (event != XmlPullParser.END_DOCUMENT) {

            switch (event) {
                case XmlPullParser.START_TAG:
                    tag = xpp.getName();
                    if(!(tag.equals("FIO") || tag.equals("birthdate") || tag.equals("avatar") || tag.equals("item")))
                        childOf = tag;
                    break;
                case XmlPullParser.TEXT:
                    text = xpp.getText();
                    break;
                case XmlPullParser.END_TAG:
                    switch (tag) {
                        case "FIO":
                            fullname = text;
                            break;
                        case "birthdate":
                            birthdate = text;
                            break;
                        case "avatar":
                            avatar_path = text;
                            break;
                        case "item":
                            switch (childOf) {
                                case "obr":
                                    obrlist.add(new ObrItem(text));
                                    break;
                                case "work":
                                    worklist.add(new WorkItem(text));
                                    break;
                                case "dost":
                                    dostlist.add(new DostItem(text));
                                    break;
                            }
                            break;
                    }
            }

            event = xpp.next();
        }
    }
}
