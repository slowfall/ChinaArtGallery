package net.ltfc.chinaartgallery.di.components;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import net.ltfc.chinaartgallery.di.PerActivity;
import net.ltfc.chinaartgallery.di.modules.FragmentModule;

import dagger.Component;

/**
 * Created by zack on 2016/3/25.
 */
@PerActivity
@Component(modules = {FragmentModule.class})
public interface FragmentComponent {
    Fragment fragment();

    FragmentManager childFragmentManager();
}
