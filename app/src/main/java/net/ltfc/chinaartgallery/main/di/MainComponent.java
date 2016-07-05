package net.ltfc.chinaartgallery.main.di;

import net.ltfc.chinaartgallery.di.PerFragment;
import net.ltfc.chinaartgallery.di.components.ApplicationComponent;
import net.ltfc.chinaartgallery.di.components.FragmentComponent;
import net.ltfc.chinaartgallery.di.modules.ActivityModule;
import net.ltfc.chinaartgallery.di.modules.FragmentModule;
import net.ltfc.chinaartgallery.main.view.GalleryFragment;
import net.ltfc.chinaartgallery.main.view.MainFragment;

import dagger.Component;

/**
 * Created by zack on 2016/3/25.
 */
@PerFragment
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, FragmentModule.class, MainModule.class})
public interface MainComponent extends FragmentComponent {
    void inject(MainFragment mainFragment);

    void inject(GalleryFragment galleryFragment);
}
