package net.ltfc.chinaartgallery.base;

import android.app.Application;

import net.ltfc.chinaartgallery.di.components.ApplicationComponent;
import net.ltfc.chinaartgallery.di.modules.ApplicationModule;
import net.ltfc.chinaartgallery.di.components.DaggerApplicationComponent;

/**
 * Created by zack on 2016/3/23.
 */
public class CAGApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
         this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}