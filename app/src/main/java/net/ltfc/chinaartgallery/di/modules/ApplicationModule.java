package net.ltfc.chinaartgallery.di.modules;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.common.ToastUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
