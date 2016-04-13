package net.ltfc.chinaartgallery.search.di;

import net.ltfc.chinaartgallery.di.PerActivity;
import net.ltfc.chinaartgallery.di.components.ApplicationComponent;
import net.ltfc.chinaartgallery.di.components.FragmentComponent;
import net.ltfc.chinaartgallery.di.modules.ActivityModule;
import net.ltfc.chinaartgallery.di.modules.FragmentModule;
import net.ltfc.chinaartgallery.main.view.GalleryFragment;
import net.ltfc.chinaartgallery.main.view.MainFragment;
import net.ltfc.chinaartgallery.search.view.SearchFragment;

import dagger.Component;

/**
 * Created by zack on 2016/3/25.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class, FragmentModule.class, SearchModule.class})
public interface SearchComponent extends FragmentComponent {
    void inject(SearchFragment mainFragment);
}
