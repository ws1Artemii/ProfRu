package com.example.profru.Resume;

public class ObrItem {

    private String[] types = {"дошкольное образование",
            "начальное общее образование",
            "основное общее образование",
            "среднее общее образование",
            "профессионального образования",
            "среднее профессиональное образование",
            "бакалавриат",
            "специалитет, магистратура",
            "подготовка кадров высшей квалификации"};
    private int type;
    private String origin;

    public String getType() {
        return types[type];
    }
    public int getTypeId() {
        return type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public ObrItem(String _origin, int _type) {
        origin = _origin;
        type = _type;
    }

    public ObrItem(String compressed) {
        this(compressed.split("|")[1], Integer.getInteger(compressed.split("|")[0]));
    }

    public ObrItem() {
        this("", 0);
    }

    public String[] getTypes() {
        return types;
    }
}
