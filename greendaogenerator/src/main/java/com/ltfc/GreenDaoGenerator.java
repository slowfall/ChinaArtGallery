package com.ltfc;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {
    private static final int DB_VERSION = 1;
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(DB_VERSION,"net.ltfc.chinaartgallery.model.db");

        addSearchKey(schema);
        new DaoGenerator().generateAll(schema,"app/src/main/java");
    }

    private static void addSearchKey(Schema schema) {
        Entity searchKey = schema.addEntity("SearchKey");
        searchKey.addIdProperty();
        searchKey.addStringProperty("key");
        searchKey.addDateProperty("date");
    }
}
