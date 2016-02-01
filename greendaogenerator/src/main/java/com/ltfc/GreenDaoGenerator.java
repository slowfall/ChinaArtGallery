package com.ltfc;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {
    private static final int DB_VERSION = 1;
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(DB_VERSION,"net.ltfc.chinaartgallery.model.db");

        addSearchSuggestion(schema);
        new DaoGenerator().generateAll(schema,"app/src/main/java");
    }

    private static void addSearchSuggestion(Schema schema) {
        Entity searchSuggestion = schema.addEntity("SearchSuggestion");
        searchSuggestion.addIdProperty();
        searchSuggestion.addStringProperty("key");
        searchSuggestion.addDateProperty("date");
    }
}
