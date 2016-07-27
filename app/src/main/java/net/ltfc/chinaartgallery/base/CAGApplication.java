package net.ltfc.chinaartgallery.base;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.umeng.socialize.PlatformConfig;

import net.ltfc.chinaartgallery.di.components.ApplicationComponent;
import net.ltfc.chinaartgallery.di.modules.ApplicationModule;
import net.ltfc.chinaartgallery.di.components.DaggerApplicationComponent;
import net.ltfc.chinaartgallery.base.model.db.SearchKeyDao;

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
        Log.i("CAGApplication", "onCreate sqLiteOpenHelper:" + sqLiteOpenHelper);
        SearchKeyDao.createTable(sqLiteOpenHelper.getWritableDatabase(), true);

        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");//微信 appid appsecret
        PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");//新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba"); // QQ和Qzone appid appkey
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