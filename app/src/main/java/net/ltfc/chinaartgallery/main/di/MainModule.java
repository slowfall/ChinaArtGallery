package net.ltfc.chinaartgallery.main.di;

import net.ltfc.chinaartgallery.base.view.OnRecyclerViewItemClickListener;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.base.rx.BaseSubscriber;
import net.ltfc.chinaartgallery.base.model.entities.MainTab;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.di.PerFragment;
import net.ltfc.chinaartgallery.main.view.GalleryFragment;
import net.ltfc.chinaartgallery.main.view.GalleryView;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zack on 2016/3/24.
 */
@Module
public class MainModule {
    private GalleryFragment galleryFragment;

    public MainModule() {

    }

    public MainModule(GalleryFragment galleryFragment) {
        this.galleryFragment = galleryFragment;
    }

    @Provides
    @PerFragment
    OnRecyclerViewItemClickListener provideOnRecyclerViewItemClickListener() {
        return this.galleryFragment;
    }

    @Provides
    BaseSubscriber<List<Painting>> providePaintingsSubscriber(ToastUtils toastUtils) {
        return new BaseSubscriber<>(toastUtils);
    }

    @Provides
    BaseSubscriber<List<MainTab>> provideMainTabsSubscriber(ToastUtils toastUtils) {
        return new BaseSubscriber<>(toastUtils);
    }
//    private Fragment fragment;
//
//    public SearchModule(Fragment fragment) {
//        this.fragment = fragment;
//    }
//
//    @Provides
//    @PerActivity
//    Fragment provideFragment() {
//        return this.fragment;
//    }
//
//    @Provides
//    @PerActivity
//    FragmentManager provideChildFragmentManager() {
//        return this.fragment.getChildFragmentManager();
//    }
}
