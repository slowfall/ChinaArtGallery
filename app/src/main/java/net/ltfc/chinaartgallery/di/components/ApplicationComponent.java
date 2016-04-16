package net.ltfc.chinaartgallery.di.components;

import android.content.Context;

import com.google.gson.Gson;

import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.di.modules.ApplicationModule;
import net.ltfc.chinaartgallery.base.view.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zack on 2016/3/23.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context applicationContext();
    Gson gson();
    ToastUtils toastUtils();
}
