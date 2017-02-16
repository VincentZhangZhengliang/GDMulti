package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.szxkbl.gd");
        Entity son = schema.addEntity("Son");
        son.addIdProperty();
        son.addIntProperty("age");
        son.addStringProperty("name");
        Property fatherId = son.addLongProperty("fatherId").getProperty();

        Entity father = schema.addEntity("Father");
        father.addIdProperty();
        father.addIntProperty("age");
        father.addStringProperty("name");

        son.addToOne(father, fatherId);

        try {
            new DaoGenerator().generateAll(schema, "app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
