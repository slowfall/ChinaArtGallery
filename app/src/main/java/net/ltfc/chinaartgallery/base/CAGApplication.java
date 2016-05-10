package net.ltfc.chinaartgallery.base;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.ltfc.chinaartgallery.di.components.ApplicationComponent;
import net.ltfc.chinaartgallery.di.modules.ApplicationModule;
import net.ltfc.chinaartgallery.di.components.DaggerApplicationComponent;
import net.ltfc.chinaartgallery.model.db.SearchKeyDao;

import javax.inject.Inject;

/**
 * Created by zack on 2016/3/23.
 */
public class CAGApplication extends Application {
    private ApplicationComponent applicationComponent;
    @Inject
    public SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        Log.d("CAGApplication", "onCreate sqLiteOpenHelper:" + sqLiteOpenHelper);
        SearchKeyDao.createTable(sqLiteOpenHelper.getWritableDatabase(), true);
    }

    private void initializeInjector() {
         this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        this.applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}