package net.ltfc.chinaartgallery.di.components;

import android.app.Activity;

import net.ltfc.chinaartgallery.base.view.BaseActivity;
import net.ltfc.chinaartgallery.detail.view.DetailActivity;
import net.ltfc.chinaartgallery.di.modules.ActivityModule;
import net.ltfc.chinaartgallery.di.PerActivity;
import net.ltfc.chinaartgallery.main.view.MainActivity;

import dagger.Component;

/**
 * Created by zack on 2016/3/24.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity baseActivity);
    void inject(DetailActivity baseActivity);
    Activity activity();
}
