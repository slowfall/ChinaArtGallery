package net.ltfc.chinaartgallery.di.modules;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import net.ltfc.chinaartgallery.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 2016/3/24.
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PerActivity
    Fragment provideFragment() {
        return this.fragment;
    }

    @Provides
    @PerActivity
    FragmentManager provideChildFragmentManager() {
        return this.fragment.getChildFragmentManager();
    }
}
