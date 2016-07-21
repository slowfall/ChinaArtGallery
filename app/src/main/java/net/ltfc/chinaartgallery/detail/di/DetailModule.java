package net.ltfc.chinaartgallery.detail.di;

import net.ltfc.chinaartgallery.detail.view.DetailFragment;

import dagger.Module;

/**
 * Created by zack on 2016/6/18.
 */
@Module
public class DetailModule {
    private DetailFragment detailFragment;
    public DetailModule(DetailFragment detailFragment) {
        this.detailFragment = detailFragment;
    }
}
