package net.ltfc.chinaartgallery.detail.di;

import net.ltfc.chinaartgallery.detail.view.DetailFragment;
import net.ltfc.chinaartgallery.di.PerFragment;
import net.ltfc.chinaartgallery.di.components.ApplicationComponent;
import net.ltfc.chinaartgallery.di.components.FragmentComponent;
import net.ltfc.chinaartgallery.di.modules.ActivityModule;
import net.ltfc.chinaartgallery.di.modules.FragmentModule;
import net.ltfc.chinaartgallery.main.di.MainModule;

import dagger.Component;

/**
 * Created by zack on 2016/6/18.
 */
@PerFragment
@Component(dependencies = {ApplicationComponent.class},
        modules = {ActivityModule.class, FragmentModule.class, DetailModule.class})
public interface DetailComponent extends FragmentComponent {
    void inject(DetailFragment detailFragment);
}
