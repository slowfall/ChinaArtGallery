package net.ltfc.chinaartgallery.di.modules;

import android.app.Activity;

import net.ltfc.chinaartgallery.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 2016/3/24.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return this.activity;
    }
}
