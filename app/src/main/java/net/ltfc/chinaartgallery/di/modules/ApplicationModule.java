package net.ltfc.chinaartgallery.di.modules;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.gson.Gson;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.model.db.DaoMaster;
import net.ltfc.chinaartgallery.model.rest.CAGServerURL;
import net.ltfc.chinaartgallery.model.rest.CAGService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by zack on 2016/3/23.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    Toast provideToast(Context context) {
        return Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT);
    }

    @Provides
    @Singleton
    ToastUtils provideToastUtils(Context context, Toast toast) {
        return new ToastUtils(context, toast);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(CAGServerURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    CAGService provideCAGService(Retrofit retrofit) {
        return retrofit.create(CAGService.class);
    }

    @Provides
    @Singleton
    SQLiteOpenHelper provideSQLiteOpenHelper(Context context) {
        return new DaoMaster.DevOpenHelper(context, "cag-db", null);
    }

    @Provides
    @Singleton
    DaoMaster provideDaoMaster(SQLiteOpenHelper openHelper) {
        return new DaoMaster(openHelper.getWritableDatabase());
    }

}
