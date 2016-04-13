package net.ltfc.chinaartgallery.di.modules;

import android.app.Application;
import android.content.Context;

import net.ltfc.chinaartgallery.base.model.rest.CAGSource;

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
}
